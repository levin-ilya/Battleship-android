package edu.harvard.ilevin.battleship2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class HighScores extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
		
		SharedPreferences sharedPref = getSharedPreferences("scores",Context.MODE_PRIVATE);
		
		int highScore1 = sharedPref.getInt(getString(R.string.highScore1),1000);
		int highScore2 = sharedPref.getInt(getString(R.string.highScore2),1000);
		int highScore3 = sharedPref.getInt(getString(R.string.highScore3),1000);
		int highScore4 = sharedPref.getInt(getString(R.string.highScore4),1000);
		int highScore5 = sharedPref.getInt(getString(R.string.highScore5),1000);
		
		TextView hScore1 = (TextView) findViewById(R.id.highScoreText1);
		TextView hScore2 = (TextView) findViewById(R.id.highScoreText2);
		TextView hScore3 = (TextView) findViewById(R.id.highScoreText3);
		TextView hScore4 = (TextView) findViewById(R.id.highScoreText4);
		TextView hScore5 = (TextView) findViewById(R.id.highScoreText5);
		
		hScore1.setText(String.valueOf(highScore1));
		hScore2.setText(String.valueOf(highScore2));
		hScore3.setText(String.valueOf(highScore3));
		hScore4.setText(String.valueOf(highScore4));
		hScore5.setText(String.valueOf(highScore5));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_scores, menu);
		return true;
	}
	
	public void backToHome(View v){
		Intent home = new Intent(this,MainActivity.class);
		startActivity(home);
	}

}
