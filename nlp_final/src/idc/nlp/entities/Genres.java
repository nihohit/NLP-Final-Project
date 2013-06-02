package idc.nlp.entities;

public enum Genres {

	METAL(1), POP(2), RAP(3);

	private int numVal;

	Genres(int numVal) {
		this.numVal = numVal;
	}

	public int getInt() {
		return numVal;
	}

	public Genres fromInt(int i) {
		for (Genres genre : Genres.values()) {
			if (genre.getInt() == i) { return genre; }
		}
		return null;
	}
}
