package idc.nlp.entities;

import idc.nlp.parsers.Parser;

public class SongCollection {

	public final Song[] values;

	public SongCollection(String filename) {
		values = Parser.parseSongsFile(filename).toArray(new Song[0]);
	}

}
