package com.revItUp.musicPlayer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewIntervalActivity extends Activity {
	public ArrayList<Integer> tInterval = new ArrayList<Integer>();
	private EditText txt_intervallength;
	private EditText txt_intervalintensity;
	private EditText txt_restlength;
	private Button btndone;
	private TextView msgTxt;
	private String oldIntervalData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_interval);

		// set Menu UI
		txt_intervallength = (EditText) findViewById(R.id.txtfld_intervallength);
		txt_intervalintensity = (EditText) findViewById(R.id.txtfld_intensity);
		txt_restlength = (EditText) findViewById(R.id.txtfld_restlength);
		btndone = (Button) findViewById(R.id.btn_interval_done);
		msgTxt = (TextView) findViewById(R.id.IntervalMessage);
		
		Intent it = getIntent();
		Bundle extras = it.getExtras();
		if(extras!=null && extras.containsKey("IntervalData")){
			oldIntervalData = extras.get("IntervalData").toString();
			String[] vals = oldIntervalData.split(";");
			txt_intervallength.setText(vals[0].trim());
			txt_intervalintensity.setText(vals[1].trim());
			txt_restlength.setText(vals[2].trim());
	
		}
		// finish creating interval
		btndone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Boolean allInts = true;
				// Getting numbers from textfields
				try {
					tInterval.add(Integer.parseInt(txt_intervallength.getText()
							.toString()));
					tInterval.add(Integer.parseInt(txt_intervalintensity
							.getText().toString()));
					tInterval.add(Integer.parseInt(txt_restlength.getText()
							.toString()));
				} catch (Exception e) {
					allInts = false;
				}
				// send interval data to workoutmaker
				Intent in = new Intent(getApplicationContext(),
						MusicPlayerActivity.class);
				if (allInts) {
					in.putExtra("interval", tInterval);
					if (oldIntervalData != null) {
						in.putExtra("oldIntervalData", oldIntervalData);
						setResult(101, in);
					} else {
						setResult(100, in);
					}
					System.out.println("INTERVALDATA: "
							+ txt_intervallength.getText().toString() + ";"
							+ txt_intervalintensity.getText().toString() + ";"
							+ txt_restlength.getText().toString());
					finish();

				}
				else{
					msgTxt.setText("Interval Data Must be entered");
					tInterval = new ArrayList<Integer>();
				}
			}
		});
	}

}
