package idc.nlp.models;

import idc.nlp.entities.Genre;

/**
 * @author zivl
 * 
 */
public class PredictionResultModel {

	private int prediction;
	private double[] probabilityConfidense;

	public PredictionResultModel(double prediction, double[] probabilityConfidense) {
		this.prediction = (int) prediction;
		this.probabilityConfidense = probabilityConfidense;
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

	public void setProbabilityConfidense(double[] probabilityConfidense) {
		this.probabilityConfidense = probabilityConfidense;
	}

	public Genre getGenreClassification() {
		return Genre.fromInt(prediction);
	}

	@Override
	public String toString() {
		return "Prediction: " + getGenreClassification() + "\nPrediction Confidence: " + Genre.fromInt(1) + ": "
				+ (int) (probabilityConfidense[0] * 100) + "%, " + Genre.fromInt(2) + ": "
				+ (int) (probabilityConfidense[1] * 100) + "%, " + Genre.fromInt(3) + ": "
				+ (int) (probabilityConfidense[2] * 100) + "%";
	}

}
