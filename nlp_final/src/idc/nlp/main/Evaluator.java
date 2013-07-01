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

	final static double costOfViolation = Trainer.constraints;
	final static long startTime = System.currentTimeMillis();

	public static void main(String[] args) {
		SongClassifierModel model = new SongClassifierModel(costOfViolation);
		model.train();
		//SongClassifierModel.loadModelFromFile(modelFilename);
		List<EvaluationModel> evaluations = new ArrayList<EvaluationModel>();
		for (Genre genre : Genre.values()) {
			evaluations.add(new EvaluationModel(costOfViolation, model, genre, SongCollection
					.generateTestFileName(genre)));
		}

		writeResultsToFile(evaluations);
		System.out.println("Program duration: " + print2DecimalsAfterPoint(getElapsedTimeInSeconds(startTime))
				+ " seconds");
	}

	private static void writeResultsToFile(List<EvaluationModel> evaluations) {
		StringBuilder str = new StringBuilder("Song Classifier - Evaluation File\n");
		str.append("=================================\n");
		str.append("Evaluated Genres: ");
		int totalSongs = 0;
		int songNum = 1;
		int[] successfulPrediction = new int[evaluations.size() + 1];
		StringBuilder genresStringBuilder = new StringBuilder();
		StringBuilder songsDataStringBuilder = new StringBuilder();
		for (EvaluationModel evaluation : evaluations) {
			genresStringBuilder.append(String.format("%-6s", evaluation.getGenre().toString().toLowerCase()));
			totalSongs += evaluation.getResults().size();
			for (PredictionResult result : evaluation.getResults()) {
				if (result.getGenreClassification().equals(evaluation.getGenre())) {
					successfulPrediction[evaluation.getGenre().getInt()]++;
				}
				songsDataStringBuilder.append(String.format("%-5s", songNum++ + ".") + result.printConfidenceOnly());
				songsDataStringBuilder.append(String.format("%-12s", result.getGenreClassification()));
				songsDataStringBuilder.append("\n\n");
			}
		}
		str.append(genresStringBuilder.toString());
		str.append("\nCost of constraints violation:\t" + evaluations.get(0).getConstraints() + "\n");
		str.append("Number of songs to classify:\t" + totalSongs + "\n\n");
		str.append(String.format("%-5s", "song"));
		str.append(genresStringBuilder.toString());
		str.append("\tPrediction");
		str.append("\tExpected Prediction\t");
		str.append("Song Name");
		str.append('\n');
		str.append(songsDataStringBuilder.toString());
		str.append("\nTotals:\n");
		str.append("================================================\n");
		StringBuilder confusionMatrixStringBuilder = new StringBuilder("\t\t" + genresStringBuilder.toString());
		for (EvaluationModel evaluation : evaluations) {
			int p = successfulPrediction[evaluation.getGenre().getInt()];
			int t = evaluation.getResults().size();
			str.append("Prediction results for " + evaluation.getGenre() + ": " + p + " of " + t + " songs\t");
			str.append("-\t" + print2DecimalsAfterPoint((double) (1.0 * p / t) * 100) + "%\n");
			confusionMatrixStringBuilder.append('\n').append(evaluation.getGenre().toString().toLowerCase())
					.append(':').append('\t');
			confusionMatrixStringBuilder.append(printArrayWithTabs(evaluation.getConfusionVector()));
			confusionMatrixStringBuilder.append('\n');
		}
		int totalSuccessfulPredictions = arraySum(successfulPrediction);
		str.append("Total predicted " + totalSuccessfulPredictions + " of " + totalSongs + " songs\t");
		str.append("-\t" + print2DecimalsAfterPoint((double) (1.0 * totalSuccessfulPredictions / totalSongs) * 100)
				+ "%\n\n");
		str.append(confusionMatrixStringBuilder.toString() + "\n\n");
		str.append("Evaluation time: " + print2DecimalsAfterPoint(getElapsedTimeInSeconds(startTime)) + " seconds");
		FileUtil.writeStringToFile(
				"resources/results/3_genre_model_c_" + evaluations.get(0).getConstraints() + ".eval", str.toString());

	}

	private static int arraySum(int[] arr) {
		int sum = 0;
		for (int i : arr) {
			sum += i;
		}
		return sum;
	}

	private static String printArrayWithTabs(int[] arr) {
		String arrString = "";
		for (int i : arr) {
			arrString += String.format("%02d    ", i);
		}
		return arrString;
	}

	private static double getElapsedTimeInSeconds(long startTime) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		return elapsedTime / 1000.0;
	}

	private static String print2DecimalsAfterPoint(double val) {
		return String.format("%.2f", val);
	}
}
