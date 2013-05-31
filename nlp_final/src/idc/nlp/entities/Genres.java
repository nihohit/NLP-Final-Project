package idc.nlp.entities;

public enum Genres {

	METAL(1), ROCK(2), RAP(3), CHILL_OUT(4);

	private int numVal;

	Genres(int numVal) {
		this.numVal = numVal;
	}

	public int getNumVal() {
		return numVal;
	}
}
