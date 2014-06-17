package com.porker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;

public class MainActivity extends Activity {

	protected static final String TAG = MainActivity.class.getCanonicalName();

	private List<String> mlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), BlackJackActivity.class);
		intent.putExtra("player", 1);
		// startActivityForResult(intent, 0);
		startActivity(intent);
		
		/*
		
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), CardFlyingActivity.class);
		//intent.putExtra("player", 1);
		// startActivityForResult(intent, 0);
		startActivity(intent);
		*/
		/*
		 Intent intent = new Intent();
		 intent.setClass(getApplicationContext(), MemoryGameActivity.class);
		 intent.putExtra("size", 4); startActivityForResult(intent, 0);
		 startActivity(intent);
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "onActivityResult");
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				// A contact was picked. Here we will just display it
				// to the user.

			}
		}
	}
}
