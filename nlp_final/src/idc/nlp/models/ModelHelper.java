package idc.nlp.models;

import java.util.List;

import idc.nlp.entities.Genre;
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
	 * @param rapSongs
	 * @return the <code>Problem</code> object to be trained used as the
	 * prediction model.
	 */
	public Problem createProblem(SongCollection metalSongs, SongCollection popSongs, SongCollection rapSongs) {
		Problem p = new Problem();

		//the number of training set examples
		p.l = metalSongs.size() + popSongs.size() + rapSongs.size();

		//the number of total features in input
		p.n = LyricsData.size();

		//the feature nodes of the training set
		p.x = convertTrainingSetToFeatureNodes(metalSongs.songs, popSongs.songs, rapSongs.songs);

		//the target songs of the known feature nodes
		p.y = createTargetValuesArray(metalSongs.size(), popSongs.size(), rapSongs.size());
		return p;
	}

	/**
	 * Creates target songs array.<br/>
	 * If value=<code>Genre.METAL</code> then this means the value belongs to a
	 * metal song.<br/>
	 * If value=<code>Genre.POP</code> then this means the value belongs to a
	 * pop song.<br/>
	 * If value=<code>Genre.RAP</code> then this means the value belongs to a
	 * rap song.<br/>
	 * The order of the songs array is defined as follow: If the size of the
	 * array is <code>n</code>, then the first <code>k</code> songs are defined
	 * as metal songs, the other of the <code>n-k</code> songs are defined as
	 * the pop songs and so on...
	 * 
	 * @param quantityOfMetal the quantity of the metal songs.
	 * @param quantityOfPop the quantity of the rock songs.
	 * @param quantityOfRap the quantity of the rock songs.
	 * 
	 * @return The target songs array.
	 */
	public double[] createTargetValuesArray(int quantityOfMetal, int quantityOfPop, int quantityOfRap) {
		double[] arr = new double[quantityOfPop + quantityOfMetal + quantityOfRap];
		int i = 0;
		for (i = 0; i < quantityOfMetal; i++) {
			arr[i] = Genre.METAL.getInt();
		}
		for (int j = 0; j < quantityOfPop; j++, i++) {
			arr[i] = Genre.POP.getInt();
		}
		for (int k = 0; k < quantityOfRap; k++, i++) {
			arr[i] = Genre.RAP.getInt();
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
	 * defined as metal songs, the other of the <code>n-k</code> rows are
	 * defined as the pop songs and so on...
	 * @param metalSongs a list of metal songs in a form of <code>Song</code>.
	 * @param popSongs a list of pop songs in a form of <code>Song</code>.
	 * @param rapSongs a list of rap songs in a form of <code>Song</code>.
	 * @return FeatureNode matrix X filled with the songs features.
	 */
	public FeatureNode[][] convertTrainingSetToFeatureNodes(List<Song> metalSongs, List<Song> popSongs,
			List<Song> rapSongs) {
		FeatureNode[][] temp = new FeatureNode[metalSongs.size() + popSongs.size() + rapSongs.size()][];
		int row = 0;
		for (Song song : metalSongs) {
			temp[row] = song.getFeatureNodes();
			row++;
		}
		for (Song song : popSongs) {
			temp[row] = song.getFeatureNodes();
			row++;
		}
		for (Song song : rapSongs) {
			temp[row] = song.getFeatureNodes();
			row++;
		}

		return temp;
	}

}
