package idc.nlp.entities;

public enum Genres {

	METAL(1), ROCK(2);

	private int numVal;

	Genres(int numVal) {
		this.numVal = numVal;
	}

	public int getNumVal() {
		return numVal;
	}
}
