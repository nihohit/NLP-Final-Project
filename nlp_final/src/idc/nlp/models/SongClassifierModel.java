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

	final SolverType solver = SolverType.L2R_LR; // -s 0
	final double C; // cost of constraints violation
	final double eps = 0.01; // stopping criteria
	Model model;

	public SongClassifierModel(double constraints) {
		C = constraints;
	}

	public void train() {
		Problem problem = new ModelHelper().createProblem(new SongCollection(Genre.METAL), new SongCollection(Genre.POP), new SongCollection(Genre.RAP));
		this.model = Linear.train(problem, new Parameter(solver, C, eps));
	}
	
	public PredictionResultModel predict(FeatureNode[] instanceToTest) {
		return predict(instanceToTest, false);
	}

	public PredictionResultModel predict(FeatureNode[] instanceToTest, boolean saveModelToFile) {
		if (saveModelToFile) {
			File modelFile = new File("model.dat");
			try {
				model.save(modelFile);
				// load model or use it directly
				model = Model.load(modelFile);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		double[] probabilityResults = new double[Genre.values().length];
		double prediction = Linear.predictProbability(model, instanceToTest, probabilityResults);
		return new PredictionResultModel(prediction, probabilityResults);
	}
	
}
