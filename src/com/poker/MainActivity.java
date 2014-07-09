package com.poker;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

	protected static final String TAG = MainActivity.class.getCanonicalName();

 private Dictionary<String, Object> mDictionary;

private List<String> mKeylist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDictionary = new Hashtable <String, Object>();
		mDictionary.put("Memory Game", MemoryGameActivity.class);
		mDictionary.put("Black Jack", BlackJackActivity.class); 

		;
		ListView lView = (ListView) findViewById(R.id.listView1);

		Enumeration<String> keys = mDictionary.keys();
		mKeylist = Collections.list(keys);
		ListAdapter adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, mKeylist);
		lView.setAdapter(adapter);
		
		lView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i(TAG, "arg3: " + arg3 + " arg2: " + arg2);
				Intent intent = new Intent();
				String key = mKeylist.get(arg2);
				
				intent.setClass(getApplicationContext(), (Class<?>) mDictionary.get(key));
				intent.putExtra("player", 1);
				startActivity(intent);
			}
			
		}
		);

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
