package idc.nlp.entities;

import idc.nlp.parsers.Parser;

public class SongCollection {
	
	public final Song[] values;
	
	private Genre genre;
	
	private final String POP_TRAIN_FILENAME = "resources/train/pop_songs.train";
	private final String METAL_TRAIN_FILENAME = "resources/train/metal_songs.train";
	private final String RAP_TRAIN_FILENAME = "resources/train/rap_songs.train";
	

	public SongCollection(Genre genre) {
		this.genre = genre;
		String filename = null;
		switch (genre) {
			case RAP:
				filename = RAP_TRAIN_FILENAME;
				break;
			case METAL:
				filename = METAL_TRAIN_FILENAME;
				break;
			case POP:
				filename = POP_TRAIN_FILENAME;
				break;
			default:
				break;
		}
		values = Parser.parseSongsFile(filename).toArray(new Song[0]);
	}
	
	public SongCollection(String filename) {
		values = Parser.parseSongsFile(filename).toArray(new Song[0]);
	}

	public Genre getGenre() {
		return genre;
	}
}
