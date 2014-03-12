package com.revItUp.musicPlayer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Environment;
import android.provider.DocumentsContract.Root;

public class SongsManager {
	// SDCard Path
	final String MEDIA_PATH = new String("/sdcard/");
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	
	// Constructor
	public SongsManager(){
		
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList(){
		File externalStorage = Environment.getExternalStorageDirectory();
		File[] a = externalStorage.listFiles(new FileExtensionFilter());
		if (a != null && externalStorage.listFiles(new FileExtensionFilter()).length > 0) {
			for (File file : externalStorage.listFiles(new FileExtensionFilter())) {
				HashMap<String, String> song = new HashMap<String, String>();
				song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
				song.put("songPath", file.getPath());
				
				// Adding each song to SongList
				songsList.add(song);
			}
		}
		// return songs list array
		return songsList;
	}
	
	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			System.out.println("File Found: ");
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}
}
