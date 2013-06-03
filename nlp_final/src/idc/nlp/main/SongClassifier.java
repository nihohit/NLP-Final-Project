package idc.nlp.main;

import idc.nlp.entities.SongCollection;
import idc.nlp.models.SongClassifierModel;

public class SongClassifier {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		SongCollection popTestFile = new SongCollection("resources/test/pop_songs.test");
		SongClassifierModel model = new SongClassifierModel(1.0);
		System.out.println(model.predict(popTestFile.values[1].convertToFeatureNodes()));
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Program duration: " + (elapsedTime / 1000) + " seconds");
	}
}
