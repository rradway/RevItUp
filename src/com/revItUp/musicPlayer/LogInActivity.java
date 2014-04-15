package com.revItUp.musicPlayer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActivity extends Activity {
	public static final String PREFS_NAME = "RevItUpPrefsFile";
	private static final String PREF_USERNAME = "username";
	private static final String PREF_PASSWORD = "password";
	private static final String PREF_REMEMBERME = "rememberMe";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		String username = pref.getString(PREF_USERNAME, null);
		String password = pref.getString(PREF_PASSWORD, null);
		Boolean rememberMe = pref.getBoolean(PREF_REMEMBERME, false);

		if (!(username == null || password == null || rememberMe == null || rememberMe == false)) {
			EditText usr = (EditText) findViewById(R.id.enteredUserName);
			usr.setText(username);
			EditText pwd = (EditText) findViewById(R.id.enteredPassWord);
			pwd.setText(password);
			CheckBox cb = (CheckBox) findViewById(R.id.rememberMeCheck);
			cb.setChecked(true);
		}
	}

	public void logIn(View view) {
		EditText usr = (EditText) findViewById(R.id.enteredUserName);
		EditText pwd = (EditText) findViewById(R.id.enteredPassWord);
		CheckBox cb = (CheckBox) findViewById(R.id.rememberMeCheck);
		CheckBox RA = (CheckBox) findViewById(R.id.reAnalyze);
		SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		String username = pref.getString(PREF_USERNAME, null);
		String password = pref.getString(PREF_PASSWORD, null);
		Boolean rememberMe = pref.getBoolean(PREF_REMEMBERME, false);
		if (usr.getText().toString().equals(username)
				&& pwd.getText().toString().equals(password)) {
			Editor e = pref.edit();
			e.putBoolean(PREF_REMEMBERME, (cb.isChecked() || rememberMe));
			e.commit();
			String songslist = pref.getString(
					AnalyzeMusicActivity.PREF_SONGSLIST, null);
			if (songslist != null && !RA.isChecked() ) {
				Gson gs = new Gson();
				Type type = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
				ArrayList<HashMap<String, String>> songls = gs.fromJson(songslist, type);
				System.out.println("songls " + songls.toString());
				Intent in = new Intent(getApplicationContext(),
						WorkoutMenuActivity.class);
				startActivity(in);
				finish();
			} else {
				Intent in = new Intent(getApplicationContext(),
						AnalyzeMusicActivity.class);
				startActivity(in);
				finish();
			}
		} else {
			TextView error = (TextView) findViewById(R.id.loginMessage);
			error.setText("Invalid Username or Password!");
		}

	}

	public void createNewUser(View view) {
		Intent in = new Intent(getApplicationContext(),
				CreateNewUserActivity.class);
		startActivity(in);
		finish();
	}

}
