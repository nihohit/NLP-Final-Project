package idc.nlp.entities;

import java.util.List;

import idc.nlp.parsers.ParseMode;
import idc.nlp.parsers.Parser;

public class SongCollection {
	
	public final List<Song> songs;
	
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
		songs = Parser.PARSER.parseSongsFile(filename, ParseMode.TRAIN);
	}
	
	public SongCollection(String filename, ParseMode mode) {
		songs = Parser.PARSER.parseSongsFile(filename, mode);
	}

	public Genre getGenre() {
		return genre;
	}
	
	public int size() {
		return songs.size();
	}
}
