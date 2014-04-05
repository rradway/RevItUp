package com.revItUp.musicPlayer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

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
		Boolean rememberMe = pref.getBoolean(PREF_REMEMBERME, (Boolean) null);
		if (!(username == null || password == null || rememberMe == null || rememberMe == false)) {
			EditText usr = (EditText)findViewById(R.id.enteredUserName);
		}
	}

}
