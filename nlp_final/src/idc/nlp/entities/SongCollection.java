package idc.nlp.entities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class SongCollection {

	public final Song[] values;

	public SongCollection(String filename) {
		values = readSongsFromFile(filename);
	}

	protected Song[] readSongsFromFile(String filename) {
		Properties properties = new Properties();
		Song[] values = null;
		try {
			FileInputStream in = new FileInputStream(filename);
			properties.load(in);
			Collection<Object> apps = properties.values();
			values = new Song[apps.size()];
			int i = 0;
			for (Object app : apps) {
				values[i] = new Song(convertAppObjectToPermissionArray(app, filename));
				i++;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return values;
	}

	private static String[] convertAppObjectToPermissionArray(Object app, String filename) {
		if(app == null) {
			return null;
		}
		String[] lyrics = app.toString().split(" ");
		return lyrics;
	}
}
