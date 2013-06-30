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
		long startTime = System.currentTimeMillis();
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
		StringBuilder genresStringBuilder = new StringBuilder();
		for (EvaluationModel evaluationModel : evaluations) {
			genresStringBuilder.append(String.format("%-6s", evaluationModel.getGenre().toString().toLowerCase()));
			totalSongs += evaluationModel.getResults().size();
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
		int songNum = 1;
		int[] successfulPrediction = new int[evaluations.size() + 1];
		for (EvaluationModel evaluationModel : evaluations) {
			for (PredictionResult result : evaluationModel.getResults()) {
				if (result.getGenreClassification().equals(evaluationModel.getGenre())) {
					successfulPrediction[evaluationModel.getGenre().getInt()]++;
				}
				str.append(String.format("%-5s", songNum++ + ".") + result.printConfidenceOnly());
				str.append(String.format("%-12s", result.getGenreClassification()));
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
		str.append("-\t" + print2DecimalsAfterPoint((double) (1.0 * totalSuccessfulPredictions / totalSongs) * 100)
				+ "%\n\n");
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

	private static double getElapsedTimeInSeconds(long startTime) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		return elapsedTime / 1000.0;
	}

	private static String print2DecimalsAfterPoint(double val) {
		return String.format("%.2f", val);
	}
}
