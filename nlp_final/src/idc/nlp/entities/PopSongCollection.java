package idc.nlp.entities;


public class PopSongCollection extends SongCollection{

	private static final String FILENAME = "resources/train/pop_songs.train";
	
	public PopSongCollection() {
		super(FILENAME);
	}
}
