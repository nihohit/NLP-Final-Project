package idc.nlp.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class handling file system I/O API
 */
public class FileUtil {

	/**
	 * write the given string to a file.
	 * @param filename - the file name of the file to be written.
	 * @param str - the string to be written into the file.
	 */
	public static void writeStringToFile(String filename, String str) {
		BufferedWriter writer = getWriter(filename);
		try {
			writer.write(str);
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gets a file writer to write to a file
	 * @param filename - the file name to write to.
	 * @return a writer
	 */
	public static BufferedWriter getWriter(String filename) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filename));
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return writer;
	}
	
	/**
	 * gets a file reader to read from a file
	 * @param filename - the file name to read from.
	 * @return a reader
	 */
	public static BufferedReader getReader(String filename) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return reader;
	}

}
