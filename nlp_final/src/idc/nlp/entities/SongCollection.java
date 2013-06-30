package idc.nlp.entities;

import java.util.List;

import idc.nlp.parsers.ParseMode;
import idc.nlp.parsers.Parser;

public class SongCollection {

	public final List<Song> songs;

	private Genre genre;
	
	public static String generateTestFileName(Genre genre)
	{
		return "resources/data/" + genre.toString().toLowerCase() + "_songs.test"; 
	}
	
	public static String generateTrainFileName(Genre genre)
	{
		return "resources/data/" + genre.toString().toLowerCase() + "_songs.train"; 
	}

	public SongCollection(Genre genre) {
		this.genre = genre;
		String filename = generateTrainFileName(genre);
		songs = Parser.PARSER.parseSongsFile(filename, ParseMode.TRAIN);
	}

	public SongCollection(String filename) {
		songs = Parser.PARSER.parseSongsFile(filename, ParseMode.TEST);
	}

	public Genre getGenre() {
		return genre;
	}

	public int size() {
		return songs.size();
	}
}
