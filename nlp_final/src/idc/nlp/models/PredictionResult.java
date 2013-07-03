package idc.nlp.models;

import idc.nlp.entities.Genre;

/**
 * @author zivl
 * 
 */
public class PredictionResult {

	private int prediction;
	private double[] probabilityConfidense;
	private String songName;
	private Genre songGenre;

	public PredictionResult(double prediction, double[] probabilityConfidense, String name, Genre genre) {
		this.prediction = (int) prediction;
		this.probabilityConfidense = probabilityConfidense;
		this.songGenre = genre;
		this.songName = name;
	}

	public int[] normalizeDoubleResults(double[] results) {
		int[] temp = new int[results.length];
		for (int i = 0; i < results.length; i++) {
			temp[i] = (int) (results[i] * 100);
		}
		return temp;
	}

	public int getPrediction() {
		return prediction;
	}

	public void setPrediction(int prediction) {
		this.prediction = prediction;
	}

	public double[] getProbabilityConfidense() {
		return probabilityConfidense;
	}

	public String getSongName() {
		return songName;
	}

	public Genre getSongGenre() {
		return songGenre;
	}

	public void setProbabilityConfidense(double[] probabilityConfidense) {
		this.probabilityConfidense = probabilityConfidense;
	}

	public Genre getGenreClassification() {
		return Genre.fromInt(prediction);
	}

	public String printConfidenceOnly() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < probabilityConfidense.length; i++) {
			stringBuilder.append(String.format("%-6s", (int) (probabilityConfidense[i] * 100) + "%"));
		}
		return stringBuilder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("Prediction: " + getGenreClassification()
				+ "\nConfidence: ");
		for (int i = 0; i < probabilityConfidense.length; i++) {
			stringBuilder
			.append(Genre.fromInt(i + 1))
			.append(": ")
			.append((int) (probabilityConfidense[i] * 100))
			.append("% ");
		}
		return stringBuilder.toString();
	}

}
