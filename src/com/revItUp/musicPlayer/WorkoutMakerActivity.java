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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WorkoutMakerActivity extends ListActivity {
	// Interval list
	public ArrayList<Integer> intervalList = new ArrayList<Integer>();
	
	//listview items
	ArrayAdapter<String> arrayAdapter;
	
	public String WorkoutName;
	private EditText txtWorkoutName;
	private Button btnNewInterval;
	private Button btnDone;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_workout);
		
		
		ListView lv = getListView();
		//create list items
		arrayAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.playlist_item, R.id.songTitle);
		lv.setAdapter(arrayAdapter);
		
		// listening to interval click
		lv.setOnItemClickListener(new OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// interval click
				// look at interval/edit !
				
			}
		});
		
		
		
		//Menu Buttons
		btnNewInterval = (Button) findViewById(R.id.btn_newinterval);
		btnDone = (Button) findViewById(R.id.btn_workout_done);
		txtWorkoutName = (EditText) findViewById(R.id.txtfld_workoutname);
		
		//tapped add new workout
		btnNewInterval.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), NewIntervalActivity.class);
				startActivityForResult(i,100);		
			}
		});
		
		//finished creating workout
		btnDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent in = new Intent(getApplicationContext(),
						MusicPlayerActivity.class);
				// Sending name to workoutmenu
				in.putExtra("workoutname",txtWorkoutName.getText().toString());
				System.out.println("workoutname:" + txtWorkoutName.getText().toString());
				
				//sending workout intervals
				in.putExtra("intervallist",intervalList);
				
				setResult(100, in);
				// Closing WorkoutMenu
				finish();	
			}
		});
		
		
	}
	
	 protected void onActivityResult(int requestCode,
             int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 100){
			//get interval
			ArrayList<Integer> tInterval = data.getExtras().getIntegerArrayList("interval");
			intervalList.add(tInterval.get(0));
			intervalList.add(tInterval.get(1));
			intervalList.add(tInterval.get(2));
			String intervalInfo = tInterval.get(0).toString() +"; "+ tInterval.get(1).toString() +"; "+ tInterval.get(2).toString();
			arrayAdapter.add(intervalInfo);
		}	
	 }
}