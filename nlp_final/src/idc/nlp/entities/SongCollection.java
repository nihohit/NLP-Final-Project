package idc.nlp.entities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class SongCollection {

	public final Song[] values;

	public SongCollection(String filename) {
		values = generateApps(filename);
	}

	protected Song[] generateApps(String filename) {
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

	public static Song getSongWithKey(String filename, String key) {
		Properties properties = new Properties();
		Song ins = null;
		try {
			properties.load(new FileInputStream(filename));
			String [] permissions = convertAppObjectToPermissionArray(properties.getProperty(key), filename);
			if(!(permissions == null || permissions.length == 0)){
				ins = new Song(permissions);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return ins;
	}

	private static String[] convertAppObjectToPermissionArray(Object app, String filename) {
		if(app == null) {
			return null;
		}
		String[] lyrics = app.toString().split(" ");
		return lyrics;
	}
}
