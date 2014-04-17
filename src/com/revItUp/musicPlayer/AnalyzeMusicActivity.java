package com.revItUp.musicPlayer;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;

public class AnalyzeMusicActivity extends Activity {
	public static ArrayList<HashMap<String, String>> songsList;
	public static String PREF_SONGSLIST = "songslist";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analyzing_music_layout);
		GetMusic gm = new GetMusic();
		gm.execute();
	}

	class GetMusic extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			SongsManager sm = new SongsManager();
			songsList = sm.getSongInfo();

			Gson gs = new Gson();
			String sl = gs.toJson(songsList);
			SharedPreferences prefs = getSharedPreferences(
					LogInActivity.PREFS_NAME, MODE_PRIVATE);
			Editor e = prefs.edit();
			e.putString(PREF_SONGSLIST, sl);
			e.commit();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			Intent i = new Intent(getApplicationContext(),
					WorkoutMenuActivity.class);
			System.out.println("Does this happen?");
			startActivity(i);
			finish();
		}
	}

}
