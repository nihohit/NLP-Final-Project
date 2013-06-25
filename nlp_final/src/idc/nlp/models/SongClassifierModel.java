package idc.nlp.models;

import idc.nlp.entities.Genre;
import idc.nlp.entities.SongCollection;

import java.io.File;
import java.io.IOException;

import de.bwaldvogel.liblinear.FeatureNode;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;

public class SongClassifierModel {

	private static final String EXPS_PATH = "resources/exps/";
	private final SolverType solver = SolverType.L2R_LR; // -s 0
	private final double C; // cost of constraints violation
	private final double eps = 0.01; // stopping criteria
	private Model model;

	public SongClassifierModel(double constraints) {
		C = constraints;
	}
	
	private SongClassifierModel(Model model) {
		C = 1.0;
		this.model = model;
	}

	/**
	 * train the song classifier model
	 */
	public void train() {
		train(null);
	}

	/**
	 * train the song classifier model and saves the model to file to save
	 * future training.
	 * @param modelFilename - the model file name
	 */
	public void train(String modelFilename) {
		Problem problem = new ModelHelper().createProblem(new SongCollection(Genre.METAL),
				new SongCollection(Genre.POP), new SongCollection(Genre.RAP));
		this.model = Linear.train(problem, new Parameter(solver, C, eps));
		if (modelFilename != null) {
			try {
				model.save(new File(EXPS_PATH + modelFilename));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * loads the model from an existing model file
	 * @param modelFilename - the file name of the existing model
	 */
	public static SongClassifierModel loadModelFromFile(String modelFilename) {
		Model model = null;
		try {
			model = Model.load(new File(modelFilename));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return new SongClassifierModel(model);
	}

	/**
	 * predicts and classify the given instance
	 * @param instanceToTest - the song to be classified as a feature nodes
	 * @return a result of the prediction with probabilities.
	 */
	public PredictionResult predict(FeatureNode[] instanceToTest) {
		double[] probabilityResults = new double[Genre.values().length];
		double prediction = Linear.predictProbability(model, instanceToTest, probabilityResults);
		return new PredictionResult(prediction, probabilityResults);
	}

}
