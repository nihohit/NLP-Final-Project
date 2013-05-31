package idc.nlp.entities;

import idc.nlp.utils.CountMap;

import java.util.Arrays;

import de.bwaldvogel.liblinear.FeatureNode;

public class Song {

	protected CountMap<String> lyrics;
	protected FeatureNode[] featureNodes;

	public Song(CountMap<String> lyrics) {
		this.lyrics = lyrics;
		this.featureNodes = convertToFeatureNodes();
	}

	public CountMap<String> getLyrics() {
		return lyrics;
	}

	public FeatureNode[] getFeatureNodes() {
		return featureNodes;
	}

	public FeatureNode[] convertToFeatureNodes() {
		int[] tempArr = new int[lyrics.size()];
		int i = 0;

		for (String word : lyrics.keySet()) {
			tempArr[i] = LyricsData.getId(word);
			i++;
		}

		Arrays.sort(tempArr);
		FeatureNode[] featureNodes = new FeatureNode[tempArr.length];
		for (i = 0; i < featureNodes.length; i++) {
			featureNodes[i] = new FeatureNode(tempArr[i], lyrics.get(LyricsData.getWord(tempArr[i])));
		}
		return featureNodes;
	}

	@Override
	public String toString() {
		return this.getLyrics().toString();
	}

}
