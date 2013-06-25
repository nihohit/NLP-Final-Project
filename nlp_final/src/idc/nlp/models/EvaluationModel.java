package idc.nlp.models;

import java.util.ArrayList;
import java.util.List;

import idc.nlp.entities.Genre;
import idc.nlp.entities.Song;
import idc.nlp.entities.SongCollection;

/**
 *
 */
public class EvaluationModel {

	private double constrains;
	private Genre genre;
	private List<PredictionResult> results;
	private SongClassifierModel model;
	private String testSongsFilePath;
	
	public EvaluationModel(double constrains, SongClassifierModel model, Genre genre, String testSongsFilePath) {
		this.constrains = constrains;
		this.model = model;
		this.genre = genre;
		this.testSongsFilePath = testSongsFilePath;
	}
	
	public List<PredictionResult> evaluateData() {
		SongCollection testFile = new SongCollection(testSongsFilePath);
		results = new ArrayList<PredictionResult>();
		for (Song song : testFile.songs) {
			results.add(model.predict(song.convertToFeatureNodes()));
		}
		return results;
		
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
	public double getConstrains() {
		return constrains;
	}
	
	
}
