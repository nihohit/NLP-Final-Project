package idc.nlp.parsers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class represents a parser object to read POS tagged segments from a
 * file.
 * 
 */
public class Parser {


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
