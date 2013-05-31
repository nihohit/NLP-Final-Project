package idc.nlp.entities;


public class RockSongCollection extends SongCollection{

	private static final String FILENAME = "resources/train/rock_songs.train";
	
	public RockSongCollection() {
		super(FILENAME);
	}
}
