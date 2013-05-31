package idc.nlp.entities;

import java.util.Arrays;
import java.util.Set;

import de.bwaldvogel.liblinear.FeatureNode;

public class Song {

	protected Set<String> lyrics;
	protected FeatureNode[] featureNodes;

	public Song(Set<String> lyrics) {
		this.lyrics = lyrics;
		this.featureNodes = convertToFeatureNodes();
	}

	public Set<String> getLyrics() {
		return lyrics;
	}

	public FeatureNode[] getFeatureNodes() {
		return featureNodes;
	}

	public FeatureNode[] convertToFeatureNodes() {
		int[] tempArr = new int[lyrics.size()];
		int i = 0;

		for (String word : lyrics) {
			tempArr[i] = LyricsData.get(word);
			i++;
		}

		Arrays.sort(tempArr);
		FeatureNode[] featureNodes = new FeatureNode[tempArr.length];
		for (i = 0; i < featureNodes.length; i++) {
			featureNodes[i] = new FeatureNode(tempArr[i], 1);
		}
		return featureNodes;
	}

	@Override
	public String toString() {
		return this.getLyrics().toString();
	}

}
