package idc.nlp.models;

import java.util.List;

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
	 * @param popSongs
	 * @return the <code>Problem</code> object to be trained used as the
	 * prediction model.
	 */
	public Problem createProblem(SongCollection metalSongs, SongCollection popSongs) {
		Problem p = new Problem();

		//the number of training set examples
		p.l = metalSongs.songs.size() + popSongs.songs.size();

		//the number of total features in input
		p.n = LyricsData.size();

		//the feature nodes of the training set
		p.x = convertTrainingSetToFeatureNodes(metalSongs.songs, popSongs.songs);

		//the target songs of the known feature nodes
		p.y = createTargetValuesArray(metalSongs.songs.size(), popSongs.songs.size());
		return p;
	}

	/**
	 * Creates target songs array.<br/>
	 * If value=1 then this means the value belongs to a metal song.<br/>
	 * If value=2 then this means the value belongs to a pop song.<br/>
	 * The order of the songs array is defined as follow: If the size of the
	 * array is <code>n</code>, then the first <code>k</code> songs are defined
	 * as metal songs, the rest of the <code>n-k</code> songs are defined as the
	 * pop songs
	 * 
	 * @param quantityOfMetal the quantity of the metal songs.
	 * @param quantityOfPop the quantity of the rock songs.
	 * 
	 * @return The target songs array.
	 */
	public double[] createTargetValuesArray(int quantityOfMetal, int quantityOfPop) {
		double[] arr = new double[quantityOfPop + quantityOfMetal];
		int i = 0;
		for (i = 0; i < quantityOfMetal; i++) {
			arr[i] = 1.0;
		}
		for (int j = 0; j < quantityOfPop; j++, i++) {
			arr[i] = 2.0;
		}

		// validate array cells
		for (i = 0; i < arr.length; i++) {
			if (arr[i] == 0.0) {
				System.out.println("ERROR: Target songs array is not initialized properly");
				System.exit(-1);
			}
		}

		return arr;
	}

	/**
	 * Converts the training set into <code>FeatureNode</code> matrix.<br/>
	 * The order of the songs list is defined as follow: If the number of rows
	 * in the matrix is <code>n</code>, then the first <code>k</code> rows are
	 * defined as metal songs, the rest of the <code>n-k</code> rows are defined
	 * as the pop songs.
	 * @param metalSongs a list of metal songs in a form of <code>Song</code>.
	 * @param popSongs a list of pop songs in a form of <code>Song</code>.
	 * @return FeatureNode matrix X filled with the songs features.
	 */
	public FeatureNode[][] convertTrainingSetToFeatureNodes(List<Song> metalSongs, List<Song> popSongs) {
		FeatureNode[][] temp = new FeatureNode[metalSongs.size() + popSongs.size()][];
		int row = 0;
		for (Song song : metalSongs) {
			for (int col = 0; col < song.getLyrics().size(); col++) {
				temp[row] = song.getFeatureNodes();
			}
			row++;
		}
		for (Song song : popSongs) {
			for (int col = 0; col < song.getLyrics().size(); col++) {
				temp[row] = song.getFeatureNodes();
			}
			row++;
		}
		return temp;
	}

}
