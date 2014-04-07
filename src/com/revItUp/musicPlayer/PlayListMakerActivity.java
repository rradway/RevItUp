package com.revItUp.musicPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import com.revItUp.musicPlayer.SongsManager.GetSongList;

import android.app.Activity;
import android.os.Bundle;

public class PlayListMakerActivity extends Activity{
	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		
		SongsManager plm = new SongsManager();
		// get all songs from sdcard
		GetSongList getPL =  plm.new GetSongList();
		getPL.execute();
		try {
			this. songsList = getPL.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void SortSongsList(){
		
		
	
	}
}
