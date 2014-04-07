package com.revItUp.musicPlayer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.SongParams;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;

public class SongsManager {
	// SDCard Path
	final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath(); // new String("/sdcard/");
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private EchoNestAPI en;
	// Constructor
	public SongsManager(){
		en = new EchoNestAPI("LTPQSDTKSYR8PYIL4");
		en.setTraceSends(false);
		en.setTraceRecvs(false);
	}
	
	
	public class GetSongList extends AsyncTask<String, Object, ArrayList<HashMap<String, String>>>{

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... params) {
			return getSongInfo();
		}
		
	}
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getSongInfo(){
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		
		File externalStorage = Environment.getExternalStorageDirectory();
		File[] a = externalStorage.listFiles(new FileExtensionFilter());
		if (a != null && externalStorage.listFiles(new FileExtensionFilter()).length > 0) {
			for (File file : externalStorage.listFiles(new FileExtensionFilter())) {
				HashMap<String, String> song = new HashMap<String, String>();
				try {
					mmr.setDataSource(file.getPath());
					String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
					String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
					if(SongFound(artist,title) == false)
					{
						continue;
					}
					else
					{
						song.put("tempo", getTempo(artist,title).toString());
						song.put("energy", getEnergy(artist, title).toString());
						song.put("loudness", getLoudness(artist, title).toString());
						song.put("echoNestInfo", getInfoStr(artist, title));
						song.put("duration",mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toString());
					}
				} catch (EchoNestException e) {
					e.printStackTrace();
				}
				song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
				song.put("songPath", file.getPath());
				
				// Adding each song to SongList
				songsList.add(song);
			}
		}
		// return songs list array
		return songsList;
	}
	public boolean SongFound(String artistName, String title) throws EchoNestException
	{
		SongParams p = new SongParams();
	    p.setArtist(artistName);
	    p.setTitle(title);
	    p.setResults(1);
	    p.includeAudioSummary();
	    List<Song> songs = en.searchSongs(p);
        if (songs.size() > 0) {
        	return true;
        }
        return false;
	}
	public Double getTempo(String artistName, String title)
            throws EchoNestException {
        SongParams p = new SongParams();
        p.setArtist(artistName);
        p.setTitle(title);
        p.setResults(1);
        p.includeAudioSummary();
        List<Song> songs = en.searchSongs(p);
        if (songs.size() > 0) {
            double tempo = songs.get(0).getTempo();
            return Double.valueOf(tempo);
        } else {
            return null;
        }
    }
	
	public Double getEnergy(String artistName, String title)
			throws EchoNestException {
		SongParams p = new SongParams();
		p.setArtist(artistName);
		p.setTitle(title);
		p.setResults(10);
		p.includeAudioSummary();
		List<Song> songs = en.searchSongs(p);
		System.out.println("Song 0: " + songs.get(0).toString());
		if (songs.size() > 1) {
			System.out.println("Song 2: " + songs.get(1).toString());
		}
		if (songs.size() > 0) {
			double tempo = songs.get(0).getEnergy();
			return Double.valueOf(tempo);
		} else {
			return null;
		}
    }
	
	public Double getLoudness(String artistName, String title)
            throws EchoNestException {
        SongParams p = new SongParams();
        p.setArtist(artistName);
        p.setTitle(title);
        p.setResults(1);
        p.includeAudioSummary();
        List<Song> songs = en.searchSongs(p);
        if (songs.size() > 0) {
            double tempo = songs.get(0).getLoudness();
            return Double.valueOf(tempo);
        } else {
            return null;
        }
    }
	
	public String getInfoStr(String artistName, String title)
			throws EchoNestException {
		SongParams p = new SongParams();
		p.setArtist(artistName);
		p.setTitle(title);
		p.setResults(1);
		p.includeAudioSummary();
		List<Song> songs = en.searchSongs(p);
		if (songs.size() > 0) {
			String info = songs.get(0).toString();
			return info;
		} else {
			return null;
		}
    }
	

	
	public void dumpSong(Song song) throws EchoNestException {
        System.out.printf("%s\n", song.getTitle());
        System.out.printf("   artist: %s\n", song.getArtistName());
        System.out.printf("   dur   : %.3f\n", song.getDuration());
        System.out.printf("   BPM   : %.3f\n", song.getTempo());
        System.out.printf("   Mode  : %d\n", song.getMode());
        System.out.printf("   S hot : %.3f\n", song.getSongHotttnesss());
        System.out.printf("   A hot : %.3f\n", song.getArtistHotttnesss());
        System.out.printf("   A fam : %.3f\n", song.getArtistFamiliarity());
        System.out.printf("   A loc : %s\n", song.getArtistLocation());
    }
	
	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			System.out.println("File Found: ");
			return (name.endsWith(".mp3") || name.endsWith(".MP3")
					|| name.endsWith(".mp4") || name.endsWith(".MP4")
					|| name.endsWith(".m4a") || name.endsWith(".M4A"));
		}
	}
}
