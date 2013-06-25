package idc.nlp.main;

import java.io.BufferedWriter;
import java.io.IOException;

import idc.nlp.entities.Genre;
import idc.nlp.entities.SongCollection;
import idc.nlp.models.EvaluationModel;
import idc.nlp.models.PredictionResult;
import idc.nlp.models.SongClassifierModel;
import idc.nlp.utils.FileUtil;

/**
 *
 */
public class Evaluator {

	static String modelFilename = SongClassifierModel.EXPS_PATH + "3_genre_model_c_1.0_25_06_13_23_30.llm";

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		SongClassifierModel model = new SongClassifierModel(Trainer.constrains);
		model.train();
		//SongClassifierModel.loadModelFromFile(modelFilename);
		EvaluationModel evaluationModel = new EvaluationModel(Trainer.constrains, model, Genre.RAP,
				SongCollection.RAP_TEST_FILENAME);
		evaluationModel.evaluateData();
		writeResultsToFile(evaluationModel);
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Program duration: " + (elapsedTime / 1000) + " seconds");
	}

	private static void writeResultsToFile(EvaluationModel evaluationModel) {
		int count = 0, i = 1;
		BufferedWriter writer = FileUtil.getWriter("resources/results/3_genre_model_c_"
				+ evaluationModel.getConstrains() + ".eval");
		try {
			writer.write("Song Classifier - Evaluation File\n");
			writer.write("=================================\n");
			writer.write("Genre:\t" + evaluationModel.getGenre() + "\n");
			writer.write("Cost of constraints violation:\t" + evaluationModel.getConstrains() + "\n");
			writer.write("Number of songs to classify:\t" + evaluationModel.getResults().size() + "\n\n");
			for (PredictionResult result : evaluationModel.getResults()) {
				if (result.getGenreClassification().equals(evaluationModel.getGenre())) {
					count++;
				}
				writer.write(i++ + ". " + result.toString());
				writer.write("\n\n");
			}
			writer.write("\n");
			writer.write("predicted " + count + " of " + evaluationModel.getResults().size() + " songs");
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
