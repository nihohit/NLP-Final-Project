package idc.nlp.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import idc.nlp.models.SongClassifierModel;

public class Trainer {

	final static double constrains = 1.0;
	final static String modelFilename = "3_genre_model_c_" + constrains + "_"
			+ new SimpleDateFormat("dd_MM_yy_HH_mm", Locale.ENGLISH).format(new Date()) + ".llm";

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		SongClassifierModel model = new SongClassifierModel(constrains);
		model.train(modelFilename);
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Training time: " + (elapsedTime / 1000) + " seconds");
	}
}
