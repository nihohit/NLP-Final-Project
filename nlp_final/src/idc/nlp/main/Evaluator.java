package idc.nlp.main;

import java.util.ArrayList;
import java.util.List;

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

	final static String modelFilename = SongClassifierModel.EXPS_PATH + "3_genre_model_c_1.0_25_06_13_23_30.llm";

	final static double costOfViolation = Trainer.constrains;
	
	static long startTime = System.currentTimeMillis();

	public static void main(String[] args) {
		
		SongClassifierModel model = new SongClassifierModel(costOfViolation);
		model.train();
		//SongClassifierModel.loadModelFromFile(modelFilename);
		List<EvaluationModel> evaluations = new ArrayList<EvaluationModel>();
		evaluations.add(new EvaluationModel(costOfViolation, model, Genre.METAL, SongCollection.METAL_TEST_FILENAME));
		evaluations.add(new EvaluationModel(costOfViolation, model, Genre.POP, SongCollection.POP_TEST_FILENAME));
		evaluations.add(new EvaluationModel(costOfViolation, model, Genre.RAP, SongCollection.RAP_TEST_FILENAME));

		writeResultsToFile(evaluations);
		
		System.out.println("Program duration: " + print2DecimalsAfterPoint(getElapsedTimeInSeconds(startTime)) + " seconds");
	}

	private static void writeResultsToFile(List<EvaluationModel> evaluations) {
		StringBuilder str = new StringBuilder("Song Classifier - Evaluation File\n");
		str.append("=================================\n");
		str.append("Evaluated Genres:");
		int totalSongs = 0;
		StringBuilder genresStringBuilder = new StringBuilder();
		for (EvaluationModel evaluationModel : evaluations) {
			genresStringBuilder.append("\t" + evaluationModel.getGenre());
			totalSongs += evaluationModel.getResults().size();
		}
		str.append(genresStringBuilder.toString());
		str.append("\nCost of constraints violation:\t" + evaluations.get(0).getConstrains() + "\n");
		str.append("Number of songs to classify:\t" + totalSongs + "\n\n");
		str.append(genresStringBuilder.toString());
		str.append("\tExpected\tPrediction");
		str.append('\n');
		int songNum = 1;
		int[] successfulPrediction = new int[evaluations.size() + 1];
		for (EvaluationModel evaluationModel : evaluations) {
			for (PredictionResult result : evaluationModel.getResults()) {
				if (result.getGenreClassification().equals(evaluationModel.getGenre())) {
					successfulPrediction[evaluationModel.getGenre().getInt()]++;
				}
				str.append(songNum++ + ".\t" + result.printConfidenceOnly());
				str.append('\t').append(evaluationModel.getGenre());
				str.append('\t').append(result.getGenreClassification());
				str.append("\n\n");
			}
		}
		str.append("\nTotals:\n");
		str.append("================================================\n");
		for (EvaluationModel evaluationModel : evaluations) {
			int p = successfulPrediction[evaluationModel.getGenre().getInt()];
			int t = evaluationModel.getResults().size();
			str.append("Prediction results for " + evaluationModel.getGenre() + ": " + p + " of " + t + " songs\t");
			str.append("-\t" + print2DecimalsAfterPoint((double) (1.0 * p / t) * 100) + "%\n");
		}
		int totalSuccessfulPredictions = arraySum(successfulPrediction);
		str.append("Total predicted " + totalSuccessfulPredictions + " of " + totalSongs + " songs\t");
		str.append("-\t" + print2DecimalsAfterPoint((double) (1.0 * totalSuccessfulPredictions / totalSongs) * 100) + "%\n");
		str.append("Evaluation time: "+print2DecimalsAfterPoint(getElapsedTimeInSeconds(startTime)) + " seconds");
		FileUtil.writeStringToFile("resources/results/3_genre_model_c_" + evaluations.get(0).getConstrains() + ".eval",
				str.toString());

	}

	private static int arraySum(int[] arr) {
		int sum = 0;
		for (int i : arr) {
			sum += i;
		}
		return sum;
	}
	
	private static double getElapsedTimeInSeconds(long startTime) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		return elapsedTime / 1000.0;
	}
	
	private static String print2DecimalsAfterPoint(double val){
		return String.format("%.2f", val);
	}
}
