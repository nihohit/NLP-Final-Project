package idc.nlp.models;

import idc.nlp.entity.MetalSongCollection;
import idc.nlp.entity.RockSongCollection;

import java.io.File;
import java.io.IOException;

import de.bwaldvogel.liblinear.FeatureNode;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;

public class SecurityModel {

	final SolverType solver = SolverType.L2R_LR; // -s 0
	final double C; // cost of constraints violation
	final double eps = 0.001; // stopping criteria
	Model model;
	private Problem problem;

	public SecurityModel(double constraints) {
		C = constraints;
		prepareData();
		this.model = Linear.train(problem, new Parameter(solver, C, eps));
	}

	private void prepareData() {
		this.problem = new SecurityModelHelper().createProblem(new MetalSongCollection(), new RockSongCollection());
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

		double[] probabilityResults = new double[2];
		double prediction = Linear.predictProbability(model, instanceToTest, probabilityResults);
		return new PredictionResultModel(prediction, probabilityResults);
	}

}
