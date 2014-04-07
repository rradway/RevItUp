package com.revItUp.musicPlayer;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewIntervalActivity extends Activity{
	public ArrayList<Integer> tInterval = new ArrayList<Integer>();
	private EditText txt_intervallength;
	private EditText txt_intervalintensity;
	private EditText txt_restlength;
	private Button btndone;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_interval);
		
		//set Menu UI
		txt_intervallength = (EditText) findViewById(R.id.txtfld_intervallength);
		txt_intervalintensity = (EditText) findViewById(R.id.txtfld_intensity);
		txt_restlength = (EditText) findViewById(R.id.txtfld_restlength);
		btndone = (Button) findViewById(R.id.btn_interval_done);
		
		
		//finish creating interval
		btndone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent in = new Intent(getApplicationContext(),
						MusicPlayerActivity.class);
				// Getting numbers from textfields
				tInterval.add(Integer.parseInt(txt_intervallength.getText().toString()));
				tInterval.add(Integer.parseInt(txt_intervalintensity.getText().toString()));
				tInterval.add(Integer.parseInt(txt_restlength.getText().toString()));
				in.putExtra("interval", tInterval);
				
				//send interval data to workoutmaker
				setResult(100, in);
				System.out.println("INTERVALDATA: " + txt_intervallength.getText().toString() + ";" + txt_intervalintensity.getText().toString() + ";" + txt_restlength.getText().toString());
				finish();
			}
		});
	}

	
		
}
