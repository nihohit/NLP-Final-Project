package idc.nlp.entities;

import idc.nlp.parsers.ParseMode;
import idc.nlp.utils.CountMap;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import de.bwaldvogel.liblinear.FeatureNode;

public class Song {

	protected CountMap<String> lyricsCount;
	private CountMap<String> generalLyricsCount;
	protected FeatureNode[] featureNodes;
	private List<List<String>> lyrics;
	public static int ADDITIONAL_FEATURES_AMOUNT = 1;

	public Song(CountMap<String> lyricsCountMap, List<List<String>> lines) {
		this.lyricsCount = lyricsCountMap;
		this.lyrics = lines;
		this.generalLyricsCount = generateLyricsCountList(this.lyrics);
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
		int totalLength = computeLength();
		//result[0] = new FeatureNode(LyricsData.size() + 1, averageLyricsOriginality());
		//result[1] =  new FeatureNode(LyricsData.size() + 2, computeAverageWordAppearance(totalLength));
		//result[2] =  new FeatureNode(LyricsData.size() + 3, computeAverageLineLength(totalLength));
		//result[3] =  new FeatureNode(LyricsData.size() + 4, wordDiversity(totalLength));
		return result;
	}
	
	private int computeLength()
	{
		int length = 0;
		for(List<String> line : lyrics)
		{
			length += line.size();
		}
		
		return length;
	}
	
	private double computeAverageLineLength(double totalLength)
	{
		return totalLength / lyrics.size();
	}
	
	private double computeAverageWordAppearance()
	{
		double variety = 0;
		for(String word : generalLyricsCount.keySet())
		{
			variety += generalLyricsCount.get(word);
		}
		return variety / generalLyricsCount.size();
	}
	
	private double wordDiversity(double totalWords)
	{
		return totalWords / generalLyricsCount.size();
	}
	
	private static CountMap<String> generateLyricsCountList(List<List<String>> lyrics)
	{
		CountMap<String> lyricsCount = new CountMap<String>();
		for(List<String> line : lyrics)
		{
			for(String word : line) 
			{
				lyricsCount.increment(word);
			}
		}
		return lyricsCount;
	}
	
	private double averageLyricsOriginality()
	{
		double result = 0.0;
		for(String word : lyricsCount.keySet())
		{
			result += LyricsData.wordAmount(word);
		}
		
		return result / lyricsCount.size();
	}
}
