package idc.nlp.parsers;

import idc.nlp.entities.LyricsData;
import idc.nlp.entities.Song;
import idc.nlp.utils.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * this class represents a parser object to read songs from a file.
 */
public class Parser {

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
	 * @param filename - the file to parse
	 * @return a <code>List</code> of <code>Song</code> instances
	 */
	public static List<Song> parseSongsFile(String filename) {
		List<Song> songs = new LinkedList<Song>();
		Set<String> songLyrics = new HashSet<String>();
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
					songLyrics = new HashSet<String>();
				}
				else if (line.charAt(0) != '#') {
					StringTokenizer tokenizer = new StringTokenizer(line);
					while (tokenizer.hasMoreTokens()) {
						String word = tokenizer.nextToken();
						word = word.toLowerCase();
						if (songLyrics.add(word)) {
							LyricsData.add(word);
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

	/**
	 * handle the errors by any kind.
	 * @param e - the error
	 * @param lineNumber - the line number of which the error occurred.
	 */
	private static void handleError(Exception e, int lineNumber) {
		System.out.println("line number is: " + lineNumber);
		e.printStackTrace();
	}

}
