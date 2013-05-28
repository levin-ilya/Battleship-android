package edu.harvard.ilevin.battleship2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class Splash extends Activity implements Callback{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Handler handle = new Handler(this);
		handle.sendEmptyMessageDelayed(00, 3000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean handleMessage(Message arg0) {
		// TODO Auto-generated method stub
		Intent main = new Intent(this,MainActivity.class);
		startActivity(main);
		return false;
	}

}
