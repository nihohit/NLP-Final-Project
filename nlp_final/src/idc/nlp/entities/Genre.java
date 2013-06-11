package idc.nlp.entities;

public enum Genre {

	METAL(1), POP(2), RAP(3);

	private int numVal;

	Genre(int numVal) {
		this.numVal = numVal;
	}

	public int getInt() {
		return numVal;
	}

	public static Genre fromInt(int i) {
		for (Genre genre : Genre.values()) {
			if (genre.getInt() == i) { return genre; }
		}
		return null;
	}
}
