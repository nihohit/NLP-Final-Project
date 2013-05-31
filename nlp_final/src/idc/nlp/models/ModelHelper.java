package idc.nlp.models;


import idc.nlp.entities.LyricsData;
import idc.nlp.entities.Song;
import idc.nlp.entities.SongCollection;
import de.bwaldvogel.liblinear.FeatureNode;
import de.bwaldvogel.liblinear.Problem;

public class ModelHelper {

	/**
	 * This function creates a <code>Problem</code> object with the given
	 * parameters
	 * @param metalSongs
	 * @param rockSongs
	 * @return the <code>Problem</code> object to be trained used as the
	 *         prediction model.
	 */
	public Problem createProblem(SongCollection metalSongs, SongCollection rockSongs) {
		Problem p = new Problem();
		
		//the number of training set examples
		p.l = metalSongs.values.length + rockSongs.values.length;
		
		//the number of total features in input
		p.n = LyricsData.size();
		
		//the feature nodes of the training set
		p.x = convertTrainingSetToFeatureNodes(metalSongs.values, rockSongs.values);
		
		//the target values of the known feature nodes
		p.y = createTargetValuesArray(metalSongs.values.length,	rockSongs.values.length);
		return p;
	}

	/**
	 * Creates target values array.<br/>
	 * If value=1 then this means the value belongs to a metal song.<br/>
	 * If value=2 then this means the value belongs to a rock song.<br/>
	 * The order of the values array is defined as follow: If the size of the
	 * array is <code>n</code>, then the first <code>k</code> values are defined
	 * as metal songs, the rest of the <code>n-k</code> values are defined as the
	 * rock songs
	 * 
	 * @param quantityOfMetal
	 *            the quantity of the metal songs.
	 * @param quantityOfRock
	 *            the quantity of the rock songs.
	 * 
	 * @return The target values array.
	 */
	public double[] createTargetValuesArray(int quantityOfMetal, int quantityOfRock) {
		double[] arr = new double[quantityOfRock + quantityOfMetal];
		int i = 0;
		for (i = 0; i < quantityOfMetal; i++) {
			arr[i] = 1.0;
		}
		for (int j = 0; j < quantityOfRock; j++, i++) {
			arr[i] = 2.0;
		}

		// validate array cells
		for (i = 0; i < arr.length; i++) {
			if (arr[i] == 0.0) {
				System.out.println("ERROR: Target values array is not initialized properly");
				System.exit(-1);
			}
		}

		return arr;
	}

	/**
	 * Converts the training set into <code>FeatureNode</code> matrix.<br/>
	 * The order of the values array is defined as follow: If the number of rows
	 * in the matrix is <code>n</code>, then the first <code>k</code> rows are
	 * defined as metal songs, the rest of the <code>n-k</code> rows are defined
	 * as the rock songs.
	 * @param metalSongs an array of metal songs in a form of <code>Song</code>.
	 * @param rockSongs an array of rock songs in a form of <code>Song</code>.
	 * @return FeatureNode matrix X filled with the songs features.
	 */
	public FeatureNode[][] convertTrainingSetToFeatureNodes(Song[] metalSongs,
			Song[] rockSongs) {
		FeatureNode[][] temp = new FeatureNode[metalSongs.length + rockSongs.length][];
		int row = 0;
		for (int i = 0; i < metalSongs.length; i++, row++) {
			Song song = metalSongs[i];
			for (int col = 0; col < song.getLyrics().size(); col++) {
				temp[row] = song.getFeatureNodes();
			}
		}
		for (int i = 0; i < rockSongs.length; i++, row++) {
			Song app = rockSongs[i];
			for (int col = 0; col < app.getLyrics().size(); col++) {
				temp[row] = app.getFeatureNodes();
			}
		}
		return temp;
	}

}
