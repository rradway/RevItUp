package com.revItUp.musicPlayer;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revItUp.musicPlayer.R;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class WorkoutMenuActivity extends ListActivity {
	// WorkoutList
	public ArrayList<ArrayList<Integer>> workoutList = new ArrayList<ArrayList<Integer>>();
	public ArrayList<String> workoutListNames = new ArrayList<String>();
	private static String PREFS_WORKOUT_LIST_NAMES = "workoutListNames";
	private static String PREFS_WORKOUT_LIST_WORKOUTS = "workoutListWorkouts";

	// list view stuff
	ArrayAdapter<String> arrayAdapter;

	// menu items
	private Button btnNewWorkout;
	private Button btnClose;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_menu);
		SharedPreferences pref = getSharedPreferences(LogInActivity.PREFS_NAME,
				MODE_PRIVATE);
		Gson gs = new Gson();

		String workoutls = pref.getString(PREFS_WORKOUT_LIST_WORKOUTS, null);
		String workoutlsnames = pref.getString(PREFS_WORKOUT_LIST_NAMES,
				null);
		if (workoutls != null && workoutlsnames != null) {
			Type type = new TypeToken<ArrayList<ArrayList<Integer>>>() {}.getType();
			workoutList = gs.fromJson(workoutls, type);

			Type type2 = new TypeToken<ArrayList<String>>() {}.getType();
			workoutListNames = gs.fromJson(workoutlsnames, type2);

		}


		// Adding menuItems to ListView
		ListView lv = getListView();

		arrayAdapter = new ArrayAdapter<String>(getBaseContext(),
				R.layout.playlist_item, R.id.songTitle);
		lv.setAdapter(arrayAdapter);
		for (String name : workoutListNames) {
			arrayAdapter.add(name);
		}
		// user chose a workout
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
				in.putExtra("WorkOutFlag", 1);
				in.putExtra("workout", workoutList.get(workoutIndex));
				in.putExtra("workoutName", workoutListNames.get(workoutIndex));
				startActivity(in);
				// Closing WorkoutMenu
				finish();
			}
		});

		// Menu Buttons
		btnNewWorkout = (Button) findViewById(R.id.btn_newworkout);
		btnClose = (Button) findViewById(R.id.btn_close);

		// tapped add new workout
		btnNewWorkout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						WorkoutMakerActivity.class);
				startActivityForResult(i, 100);
			}
		});

		// cancelled choosing a workout
		btnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent in = new Intent(getApplicationContext(),
						MusicPlayerActivity.class);
				// Sending workoutIndex to PlayerActivity
				Log.e("Blah", "What is goign on");
				in.putExtra("WorkOutFlag", -1);
				setResult(110, in);
				// Closing WorkoutMenu
				finish();
			}
		});

	}

	// receive new workout
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 100) {
			ArrayList<Integer> tWorkout = data.getExtras().getIntegerArrayList(
					"intervallist");
			if (tWorkout.size() > 0) {
				workoutList.add(tWorkout);
				workoutListNames.add(data.getExtras().getString("workoutname"));
				arrayAdapter.add(data.getExtras().getString("workoutname"));
				SharedPreferences pref = getSharedPreferences(LogInActivity.PREFS_NAME, MODE_PRIVATE);
				Gson gs = new Gson();
				Type type = new TypeToken<ArrayList<ArrayList<Integer>>>() {}.getType();
				Type type2 = new TypeToken<ArrayList<String>>() {}.getType();
				String workol = gs.toJson(workoutList, type);
				String workoutlsn = gs.toJson(workoutListNames, type2);
				Editor e = pref.edit();
				e.putString(PREFS_WORKOUT_LIST_WORKOUTS, workol);
				e.putString(PREFS_WORKOUT_LIST_NAMES, workoutlsn);
				e.commit();
				System.out.println("This definetly Happend");
				String workoutls = pref.getString(PREFS_WORKOUT_LIST_WORKOUTS,null);
				String workoutlsnames = pref.getString(PREFS_WORKOUT_LIST_NAMES, null);
				System.out.println("List " + workoutls + "Names"+ workoutlsnames);
			}
		}
	}

}