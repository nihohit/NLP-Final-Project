package idc.nlp.entities;

import java.util.HashMap;
import java.util.Map;

public class LyricsData {

	private static int counter = 1;
	
	public static Map<String, Integer> MAP = new HashMap<String, Integer>();
	
	/**
	 * adds the given word into the map, if not exists, and assigns an ID to it.
	 * @param word - the word to add
	 */
	public static void add(String word) {
		if(!MAP.containsKey(word)){
			MAP.put(word, counter);
			counter++;
		}
	}
	
	/**
	 * returns the current size of the map.
	 * @return the map size
	 */
	public static int size() {
		return counter;
	}
	
}
