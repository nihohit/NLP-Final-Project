package idc.nlp.main;

import idc.nlp.entities.Song;
import idc.nlp.entities.SongCollection;
import idc.nlp.models.SongClassifierModel;

/**
 * A main class to expose CLI for user to test their own songs
 */
public class Predict {

	public static void main(String[] args) {
		if(args.length != 2 || !Character.isDigit(args[0].charAt(0))){
			System.out.println("Usage:");
			System.out.println("predict -h | <cost> <song_lyrics>");
			System.out.println("cost - the cost of constraint violation. A number > 0 (larger than zero).");
			System.out.println("song_lyrics - a text file name with the song lyrics");
			System.out.println("-h - this help screen");
			System.exit(0);
		}
		SongClassifierModel model = new SongClassifierModel(Double.valueOf(args[0]));
		model.train();
		System.out.println();
		SongCollection songs = new SongCollection(args[1]);
		for(Song song: songs.songs){
			System.out.println(model.predict(song.convertToFeatureNodes(), song.toString(), null));
		}
		
	}

}
