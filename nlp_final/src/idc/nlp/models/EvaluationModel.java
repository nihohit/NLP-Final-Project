package idc.nlp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import idc.nlp.entities.Genre;
import idc.nlp.entities.Song;
import idc.nlp.entities.SongCollection;

/**
 * 
 */
public class EvaluationModel {

	private double constraints;
	private Genre genre;
	private List<PredictionResult> results;
	private SongClassifierModel model;
	private String testSongsFilePath;
	private int[] confusionVector;
	
	public EvaluationModel(double constrains, SongClassifierModel model, Genre genre, String testSongsFilePath) {
		this.constraints = constrains;
		this.model = model;
		this.genre = genre;
		this.testSongsFilePath = testSongsFilePath;
		evaluateData();
	}
	
	private void evaluateData() {
		SongCollection testFile = new SongCollection(testSongsFilePath);
		confusionVector = new int[Genre.values().length + 1];
		results = new ArrayList<PredictionResult>();
		for (Song song : testFile.songs) {
			PredictionResult result = model.predict(song.convertToFeatureNodes(), song.toString(), this.genre);
			confusionVector[result.getPrediction()]++;
			results.add(result);
		}
	}

	/**
	 * @return the genre
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * @return the results
	 */
	public List<PredictionResult> getResults() {
		return results;
	}

	/**
	 * @return the constrains
	 */
	public double getConstraints() {
		return constraints;
	}

	/**
	 * @return the confusionVector
	 */
	public int[] getConfusionVector() {
		return Arrays.copyOfRange(confusionVector, 1, confusionVector.length);
	}
}
