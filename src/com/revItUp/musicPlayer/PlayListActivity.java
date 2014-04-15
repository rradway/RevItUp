package com.revItUp.musicPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import com.revItUp.musicPlayer.R;
import com.revItUp.musicPlayer.SongsManager.GetSongList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PlayListActivity extends ListActivity {
	// Songs list
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private ArrayList<Integer> workoutPlayList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);

		ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();

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
		Bundle extras = getIntent().getExtras();
		workoutPlayList = extras.getIntegerArrayList("wopl");
		if(workoutPlayList==null)
		{
			// looping through playlist
			for (int i = 0; i < songsList.size(); i++) {
				// creating new HashMap
				HashMap<String, String> song = songsList.get(i);
	
				// adding HashList to ArrayList
				songsListData.add(song);
			}
		}
		else
		{
			//list workoutplaylist
			
			for(int i=0;i<workoutPlayList.size();i++)
			{
				HashMap<String, String> song = songsList.get(workoutPlayList.get(i));
				songsListData.add(song);
			}
			
		}

		
		
		
		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, songsListData,
				R.layout.playlist_item, new String[] { "songTitle" }, new int[] {
						R.id.songTitle });

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();
		// listening to single listitem click
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting listitem index
				int songIndex = position;
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						MusicPlayerActivity.class);
				// Sending songIndex to PlayerActivity
				in.putExtra("songIndex", songIndex);
				setResult(100, in);
				// Closing PlayListView
				finish();
			}
		});

	}
}
