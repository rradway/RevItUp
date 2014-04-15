package com.revItUp.musicPlayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateNewUserActivity extends Activity {
	public static final String PREFS_NAME = "RevItUpPrefsFile";
	private static final String PREF_USERNAME = "username";
	private static final String PREF_PASSWORD = "password";
	private static final String PREF_EMAIL = "email";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_user_layout);
	}
	
	@SuppressLint("NewApi")
	public void createAccount(View view){
		EditText username = (EditText)findViewById(R.id.EnteredUserName);
		EditText email = (EditText)findViewById(R.id.EnteredEmail);
		EditText pw1 = (EditText)findViewById(R.id.EnteredPassword1);
		EditText pw2 = (EditText)findViewById(R.id.EnteredPassword2);
		
		if(pw1.getText().toString().equals(pw2.getText().toString())){
			SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
			
			Editor e = pref.edit();
			e.putString(PREF_USERNAME, username.getText().toString());
			e.putString(PREF_PASSWORD, pw1.getText().toString());
			e.putString(PREF_EMAIL, email.getText().toString());
			e.commit();
			
			Intent in = new Intent(getApplicationContext(),
					WorkoutMenuActivity.class);
			startActivity(in);
			finish();
		}
		else{
			TextView tv = (TextView)findViewById(R.id.NewUserMessage);
			tv.setText("Passwords do not match");
		}
		
	}
}
