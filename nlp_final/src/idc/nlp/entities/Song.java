package idc.nlp.entities;

import java.util.Arrays;

import de.bwaldvogel.liblinear.FeatureNode;

public class Song {

	protected String[] lyrics;
	protected FeatureNode[] featureNodes;

	public Song(String[] lyrics) {
		this.lyrics = lyrics;
		this.featureNodes = convertToFeatureNodes();
	}

	public String[] getLyrics() {
		return lyrics;
	}

	public FeatureNode[] getFeatureNodes() {
		return featureNodes;
	}

	public FeatureNode[] convertToFeatureNodes() {
		int[] tempArr = new int[lyrics.length];
		for (int i = 0; i < tempArr.length; i++) {
			tempArr[i] = LyricsData.MAP.get(lyrics[i]);
		}

		Arrays.sort(tempArr);
		FeatureNode[] featureNodes = new FeatureNode[tempArr.length];
		for (int i = 0; i < featureNodes.length; i++) {
			featureNodes[i] = new FeatureNode(tempArr[i], 1);
		}
		return featureNodes;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.getLyrics());
	}

}
