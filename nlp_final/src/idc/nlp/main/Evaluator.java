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

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		SongClassifierModel model = new SongClassifierModel(Trainer.constraints);
		model.train();
		//SongClassifierModel.loadModelFromFile(modelFilename);
		List<EvaluationModel> evaluationModel = new ArrayList<EvaluationModel>();
		for(Genre genre : Genre.values())
		{
			evaluationModel.add(new EvaluationModel(Trainer.constraints, model, genre, SongCollection.generateTestFileName(genre)));
		}

		writeResultsToFile(evaluationModel);
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Program duration: " + (elapsedTime / 1000) + " seconds");
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
		str.append("\nCost of constraints violation:\t" + evaluations.get(0).getConstraints() + "\n");
		str.append("Number of songs to classify:\t" + totalSongs + "\n\n");
		str.append(genresStringBuilder.toString());
		str.append("\tPrediction");
		str.append('\n');
		int songNum = 1;
		int[] successfulPrediction = new int[evaluations.size() + 1];
		for (EvaluationModel evaluationModel : evaluations) {
			for (PredictionResult result : evaluationModel.getResults()) {
				if (result.getGenreClassification().equals(evaluationModel.getGenre())) {
					successfulPrediction[evaluationModel.getGenre().getInt()]++;
				}
				str.append(songNum++ + ".\t" + result.printConfidenceOnly());
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
			str.append("-\t" + (double) (1.0 * p / t)*100 + "%\n");
		}
		str.append("Total predicted " + arraySum(successfulPrediction) + " of " + totalSongs + " songs");
		FileUtil.writeStringToFile("resources/results/3_genre_model_c_" + evaluations.get(0).getConstraints() + ".eval",
				str.toString());

	}

	private static int arraySum(int[] arr) {
		int sum = 0;
		for (int i : arr) {
			sum += i;
		}
		return sum;
	}
}
