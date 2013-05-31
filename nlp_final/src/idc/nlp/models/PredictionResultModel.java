package idc.nlp.models;

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

	@Override
	public String toString() {
		return "Prediction: " + prediction + "\nPrediction Confidence: "
				+ (int)(probabilityConfidense[0] * 100) + "% < -- > "
				+ (int)(probabilityConfidense[1] * 100) + "%";
	}

}
