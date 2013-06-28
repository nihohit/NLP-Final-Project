package idc.nlp.entities;

import idc.nlp.utils.CountMap;

import java.util.Arrays;
import java.util.List;

import de.bwaldvogel.liblinear.FeatureNode;

public class Song {

	protected CountMap<String> lyricsCount;
	protected FeatureNode[] featureNodes;
	private List<String> lyrics;
	public static int ADDITIONAL_FEATURES_AMOUNT = 0;

	public Song(CountMap<String> lyrics) {
		this.lyricsCount = lyrics;
		this.featureNodes = convertToFeatureNodes();
	}

	public CountMap<String> getLyrics() {
		return lyricsCount;
	}

	public FeatureNode[] getFeatureNodes() {
		return featureNodes;
	}

	public FeatureNode[] convertToFeatureNodes() {
		int[] tempArr = new int[lyricsCount.size()];
		int i = 0;
		
		for (String word : lyricsCount.keySet()) {
			tempArr[i] = LyricsData.getId(word);
			i++;
		}
		
		Arrays.sort(tempArr);
		FeatureNode[] featureNodes = new FeatureNode[tempArr.length + ADDITIONAL_FEATURES_AMOUNT];
		for (i = 0; i < tempArr.length; i++) {
			featureNodes[i] = new FeatureNode(tempArr[i], lyricsCount.get(LyricsData.getWord(tempArr[i])));
		}
		
		for(FeatureNode feature : getAdditionalFeatures())
		{
			featureNodes[i] = feature; 
			i++;
		}
		return featureNodes;
	}

	@Override
	public String toString() {
		return this.getLyrics().toString();
	}
	
	private FeatureNode[] getAdditionalFeatures()
	{
		FeatureNode[] result = new FeatureNode[ADDITIONAL_FEATURES_AMOUNT];
		
		return result;
	}
}
