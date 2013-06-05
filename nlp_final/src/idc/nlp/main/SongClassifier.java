package idc.nlp.main;

import idc.nlp.entities.SongCollection;
import idc.nlp.models.SongClassifierModel;
import idc.nlp.parsers.ParseMode;

public class SongClassifier {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		SongClassifierModel model = new SongClassifierModel(1.0);
		model.train();
		SongCollection popTestFile = new SongCollection("resources/test/pop_songs.test", ParseMode.TEST);
		System.out.println(model.predict(popTestFile.songs.get(0).convertToFeatureNodes()));
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Program duration: " + (elapsedTime / 1000) + " seconds");
	}
}
