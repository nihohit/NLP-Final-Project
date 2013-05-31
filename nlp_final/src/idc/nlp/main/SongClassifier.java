package idc.nlp.main;

import idc.nlp.entities.MetalSongCollection;

public class SongClassifier {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		MetalSongCollection metalSongCollection = new MetalSongCollection();
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Program duration: " + (elapsedTime / 1000) + " seconds");
	}
}
