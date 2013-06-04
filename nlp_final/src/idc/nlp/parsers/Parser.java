package idc.nlp.parsers;

import idc.nlp.entities.LyricsData;
import idc.nlp.entities.Song;
import idc.nlp.utils.CountMap;
import idc.nlp.utils.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * A singleton object which represents a parser object to read songs from a file.
 */
public enum Parser {

	PARSER;
	
	private static Set<String> ignoredSet;

	static {
		ignoredSet = new HashSet<String>();
		BufferedReader reader = FileUtil.getReader("resources/misc/ignore.dat");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				while (tokenizer.hasMoreTokens()) {
					ignoredSet.add(tokenizer.nextToken());
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * parses a file in the following form:<br>
	 * <code># AUTHOR - SONG NAME<br>
	 * W1 W2 W3...<br>
	 * W...<br>
	 * <br>
	 * {empty line}<br>
	 * <br>
	 * # AUTHOR - SONG NAME<br>
	 * W1 W2 W3...<br>
	 * W...<br>
	 * </code> In addition, it will map each seen word to the
	 * <code>LyricsData</code> object to assign a unique ID to each word.
	 * @param filename - the file to parse.
	 * @param mode - parsing mode.
	 * @return a <code>List</code> of <code>Song</code> instances
	 */
	public List<Song> parseSongsFile(String filename, ParseMode mode) {
		List<Song> songs = new ArrayList<Song>();
		CountMap<String> songLyrics = new CountMap<String>();
		BufferedReader reader = FileUtil.getReader(filename);
		String line;
		int lineCounter = 0;
		try {
			while ((line = reader.readLine()) != null) {
				lineCounter++;
				if (line.isEmpty()) {

					// this means end of the current song
					songs.add(new Song(songLyrics));
					songLyrics = null;
					songLyrics = new CountMap<String>();
				}
				else if (line.charAt(0) != '#') {
					StringTokenizer tokenizer = new StringTokenizer(line);
					while (tokenizer.hasMoreTokens()) {
						String word = tokenizer.nextToken();
						word = word.toLowerCase();
						if (mode.equals(ParseMode.TRAIN) && wordShouldNotBeIgnored(word)) {
							songLyrics.increment(word);
							LyricsData.add(word);
						}
						else if (mode.equals(ParseMode.TEST) && LyricsData.contains(word)) {
							songLyrics.increment(word);
						}
					}
				}
			}
			reader.close();

			//one last song
			if (songLyrics.size() > 0) {
				songs.add(new Song(songLyrics));
			}
		}
		catch (IOException e) {
			handleError(e, lineCounter);
		}
		return songs;
	}

	private boolean wordShouldNotBeIgnored(String word) {
		return !ignoredSet.contains(word);
	}

	/**
	 * handle the errors by any kind.
	 * @param e - the error
	 * @param lineNumber - the line number of which the error occurred.
	 */
	private void handleError(Exception e, int lineNumber) {
		System.out.println("line number is: " + lineNumber);
		e.printStackTrace();
	}

}
