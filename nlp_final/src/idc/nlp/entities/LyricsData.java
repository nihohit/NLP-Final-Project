package idc.nlp.entities;

import java.util.HashMap;
import java.util.Map;

public class LyricsData {

	private static int counter = 1;
	
	private static Map<String, Integer> WORD_TO_INT = new HashMap<String, Integer>();
	private static Map<Integer, String> INT_TO_WORD = new HashMap<Integer, String>();
	
	/**
	 * adds the given word into the map, if not exists, and assigns an ID to it.
	 * @param word - the word to add
	 */
	public static void add(String word) {
		if(!WORD_TO_INT.containsKey(word)){
			WORD_TO_INT.put(word, counter);
			INT_TO_WORD.put(counter, word);
			counter++;
		}
	}
	
	public static Integer getId(String word) {
		return WORD_TO_INT.get(word);
	}
	
	public static String getWord(int i) {
		return INT_TO_WORD.get(i);
	}
	
	public static boolean contains(String word) {
		return WORD_TO_INT.containsKey(word);
	}
	
	public static boolean contains(int i) {
		return INT_TO_WORD.containsKey(i);
	}
	
	/**
	 * returns the current size of the map.
	 * @return the map size
	 */
	public static int size() {
		return counter;
	}

}
