package idc.nlp.parsers;

/**
 * Represent a mode for parsing methodology.
 * for example, in parse of training we would like to learn all words from training set,<br>
 * but as for parse of testing data we don't need to consider unseen words.  
 *
 */
public enum ParseMode {
	TEST, TRAIN
}
