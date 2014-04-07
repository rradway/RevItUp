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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WorkoutMenuActivity extends ListActivity {
	// WorkoutList
	public ArrayList<ArrayList<Integer>> workoutList = new ArrayList<ArrayList<Integer>>();
	public ArrayList<String> workoutListNames = new ArrayList<String>();
	
	//list view stuff
	ArrayAdapter<String> arrayAdapter;
	
	//menu items
	private Button btnNewWorkout;
	private Button btnClose;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_menu);
		
		//GET OLD WORKOUTS - NOT CODED
		// Adding menuItems to ListView
		/*ListAdapter adapter = new SimpleAdapter(this, songsListData,
						R.layout.playlist_item, new String[] { "songTitle" }, new int[] {
								R.id.songTitle });

				setListAdapter(adapter);*/
		
		
		ListView lv = getListView();
		
		arrayAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.playlist_item, R.id.songTitle);
		lv.setAdapter(arrayAdapter);
		
		
		// listening to single listitem click
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting listitem index
				int workoutIndex = position;
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						MusicPlayerActivity.class);
				// Sending workoutIndex to PlayerActivity
				in.putExtra("workout", workoutList.get(workoutIndex));
				in.putExtra("workoutName", workoutListNames.get(workoutIndex));
				setResult(110, in);
				// Closing WorkoutMenu
				finish();
			}
		});
		
		
		
		//Menu Buttons
		btnNewWorkout = (Button) findViewById(R.id.btn_newworkout);
		btnClose = (Button) findViewById(R.id.btn_close);
		
		//tapped add new workout
		btnNewWorkout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), WorkoutMakerActivity.class);
				startActivityForResult(i,100);		
			}
		});
		
		//cancelled choosing a workout
		btnClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent in = new Intent(getApplicationContext(),
						MusicPlayerActivity.class);
				// Sending workoutIndex to PlayerActivity
				in.putExtra("WorkOutFlag", 0);
				setResult(110, in);
				// Closing WorkoutMenu
				finish();	
			}
		});
		
		
	}
	
	 protected void onActivityResult(int requestCode,
             int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 100){
			
			ArrayList<Integer> tWorkout = data.getExtras().getIntegerArrayList("intervallist");
			if(tWorkout.size()>0)
			{
				workoutList.add(tWorkout);
				workoutListNames.add(data.getExtras().getString("workoutname"));
				arrayAdapter.add(data.getExtras().getString("workoutname"));
			}
		}	
	 }
	 
}