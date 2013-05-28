package edu.harvard.ilevin.battleship2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Winner extends Activity {
	private int score;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_winner);
		score = this.getIntent().getExtras().getInt("Score");
		TextView scoreText = (TextView)findViewById(R.id.winnerScore);
		scoreText.setText(String.valueOf(score));
		saveHighScore(score);
	}

	private void saveHighScore(int score2) {
		SharedPreferences sharedPref = getSharedPreferences("scores",Context.MODE_PRIVATE);
		int highScore1 = sharedPref.getInt(getString(R.string.highScore1),1000);
		int highScore2 = sharedPref.getInt(getString(R.string.highScore2),1000);
		int highScore3 = sharedPref.getInt(getString(R.string.highScore3),1000);
		int highScore4 = sharedPref.getInt(getString(R.string.highScore4),1000);
		int highScore5 = sharedPref.getInt(getString(R.string.highScore5),1000);
	
		SharedPreferences.Editor editor = sharedPref.edit();
		
		if(score2<highScore1){
			editor.putInt(getString(R.string.highScore1), score2);
			editor.commit();
		}else if(score2<highScore2){
			editor.putInt(getString(R.string.highScore2), score2);
			editor.commit();
		}else if(score2<highScore3){
			editor.putInt(getString(R.string.highScore3), score2);
			editor.commit();
		}else if(score2<highScore4){
			editor.putInt(getString(R.string.highScore4), score2);
			editor.commit();
		}else if(score2<highScore5){
			editor.putInt(getString(R.string.highScore5), score2);
			editor.commit();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.winner, menu);
		return true;
	}
	
	public void playAgain(View v){
		Intent play = new Intent(this,GameActivity.class);
		startActivity(play);
	}
	
	public void backToHome(View v){
		Intent home = new Intent(this,MainActivity.class);
		startActivity(home);
	}

}
