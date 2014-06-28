package com.poker;

import java.util.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class MemoryGameActivity extends Activity implements OnItemClickListener {

	protected static final String TAG = MainActivity.class.getCanonicalName();
	private MGCard mMemoryCard;
	private int mCardsize;
	private coord mDimCard = new coord(73, 98);
	private int mlastCardPos;
	private Bitmap mCardmap;
	private long mDelay = 2000;
	private int mScore;
	public Handler mhandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memory_game);

		int idx = getIntent().getIntExtra("size", 4);

		int col, row;

		DisplayMetrics dm = getResources().getDisplayMetrics();

		Log.i(TAG, "Display Metrics: " + dm.widthPixels + ", "
				+ dm.heightPixels);

		card c = new MGCard(52);

		int margin_horz = (int) getResources().getDimension(
				R.dimen.activity_horizontal_margin);
		int margin_vert = (int) getResources().getDimension(
				R.dimen.activity_vertical_margin);

		margin_horz = Math.round(margin_horz
				* (dm.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		margin_vert = Math.round(margin_vert
				* (dm.ydpi / DisplayMetrics.DENSITY_DEFAULT));

		int max_col;
		int max_row;

		if (mDimCard.getX() > mDimCard.getY()) {
			max_col = (dm.widthPixels - 2 * margin_horz) / mDimCard.getX();
			max_row = (dm.heightPixels - 2 * margin_vert) / mDimCard.getX();
		} else {
			max_col = (dm.widthPixels - 2 * margin_horz) / mDimCard.getY();
			max_row = (dm.heightPixels - 2 * margin_vert) / mDimCard.getY();
		}
		max_col = (max_col / 2) * 2;
		max_row = (max_row / 2) * 2;
		int max_num_card = max_col * max_row / 2;
		Log.i(TAG, "Margin: (" + margin_horz + ", " + margin_vert + ")");
		Log.i(TAG, "max dim: (" + max_row + ", " + max_col + ")");

		mCardsize = idx * idx / 2;
		mCardsize = (mCardsize > max_num_card) ? max_num_card : mCardsize;

		Log.i(TAG, "Before size: " + mCardsize + " dim: " + idx);

		mlastCardPos = -1;
		if (max_row > max_col) {
			row = (idx > max_row) ? max_row : idx;
			col = 2 * (mCardsize / row);
			if (col > max_col) {
				col = max_col;
			}
		} else {
			col = (idx > max_col) ? max_col : idx;
			row = 2 * (mCardsize / col);
			if (row > max_row) {
				row = max_row;
			}
		}

		mCardsize = col * row;

		Log.i(TAG, "After size: " + mCardsize + " dim: (" + row + ", " + col
				+ ")");

		c.shuffle();
		Log.i(TAG, "Shuffling ");
		List<cardinfo> lst = new ArrayList<cardinfo>();
		for (int i = 0; i < mCardsize / 2; i++) {
			cardinfo cval = c.getcard(i);
			// Log.i(TAG, "cards (" + cval.getCardValue().getType() + "," +
			// cval.getCardValue().getValue());
			lst.add(cval);
			lst.add(new cardinfo(cval.getCardValue().getType(), cval
					.getCardValue().getValue()));
		}
		mMemoryCard = new MGCard(lst);

		mMemoryCard.shuffle();

		mCardmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.cards);

		GridView gridview = (GridView) findViewById(R.id.gridView1);

		TextView totalscore = (TextView) findViewById(R.id.textView3);
		totalscore.setText(Integer.toString(mCardsize / 2));
		mScore = 0;
		gridview.setAdapter((ListAdapter) new ImageAdapter(this,
				R.drawable.cards, col, row));

		gridview.setNumColumns(col);

		int horz_padding = (dm.widthPixels - (col * mDimCard.getX())) / 2;
		int vert_padding = (dm.heightPixels - (row * mDimCard.getY())) / 2;

		gridview.setPaddingRelative(horz_padding, vert_padding, horz_padding,
				vert_padding);

		Log.i(TAG, "hz Padding: " + horz_padding + " vz Padding: "
				+ vert_padding);

		gridview.setOnItemClickListener(this);

		mhandler = new Handler() {
			public void handleMessage(Message msg) {
				Log.i(TAG, "handleMessage");
				Log.i(TAG, "msg: " + msg.obj);
				Intent intent = new Intent();
				intent.putExtra("data", msg.obj.toString());
				finish();
				setResult(RESULT_OK, intent);
			}

		};

	}

	@Override
	synchronized public void onItemClick(AdapterView<?> parent, View v,
			int position, long id) {
		Toast.makeText(MemoryGameActivity.this, "" + position,
				Toast.LENGTH_LONG).show();

		Log.i(TAG, "onItemClick: " + position);

		cardinfo cc = mMemoryCard.getcard(position);
		int deck = cc.getCardValue().getType().ordinal();
		int cval = cc.getCardValue().getValue();
		Bitmap bitmap;
		if (cc.isFlipped() == false) {
			Rect src = new Rect(cval * mDimCard.getX(), deck * mDimCard.getY(),
					(cval + 1) * mDimCard.getX(), (deck + 1) * mDimCard.getY());
			bitmap = Bitmap.createBitmap(mCardmap, src.left, src.top,
					mDimCard.getX(), mDimCard.getY());
			((ImageView) v).setImageBitmap(bitmap);
			cc.setFlipped(true);
		}

		if (mlastCardPos == -1) {
			mlastCardPos = position;
		} else {
			cardinfo lc = mMemoryCard.getcard(mlastCardPos);
			int dtype = lc.getCardValue().getType().ordinal();
			int val = lc.getCardValue().getValue();

			if (dtype != deck || val != cval) {

				Log.i(TAG, "Mismatched cards (" + dtype + "," + val + "), "
						+ "(" + deck + "," + cval + ")");
				ImageView lcView = (ImageView) parent.getChildAt(mlastCardPos);

				bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.card_back);
				loadIcon(lcView, bitmap);
				lc.setFlipped(false);
				loadIcon(v, bitmap);
				cc.setFlipped(false);
			} else {
				mScore++;
				TextView score = (TextView) findViewById(R.id.textView1);
				score.setText(Integer.toString(mScore));

				// TextView tView = (TextView) findViewById(R.id.textView3);
				// int totalscore = Integer.parseInt((String)tView.getText());
				// if (mScore == totalscore)
				{
					int status = 0;
					Message msg = Message.obtain(mhandler, 0, status);
					mhandler.sendMessage(msg);
				}

			}
			mlastCardPos = -1;
		}

	}

	private void loadIcon(View v, Bitmap map) {
		final Bitmap bmap = map;
		final ImageView imv = (ImageView) v;

		new Thread(new Runnable() {
			@Override
			public void run() {
				imv.postInvalidate();
				try {
					Thread.sleep(mDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Log.i(TAG, " loadIcon new Thread id "
						+ (int) Thread.currentThread().getId());
				imv.post(new Runnable() {
					@Override
					public void run() {
						// Log.i(TAG, " loadIcon post Thread id "
						// + (int) Thread.currentThread().getId());
						imv.setImageBitmap(bmap);
					}
				});
			}
		}).start();
	}

	public cardinfo getcard(int pos) {
		if (mMemoryCard != null)
			return mMemoryCard.getcard(pos);
		return null;
	}

	public coord getcardDim() {
		return mDimCard;
	}

	public Rect getCardRect(int cval, int deck) {
		return new Rect(cval * mDimCard.getX(), deck * mDimCard.getY(),
				(cval + 1) * mDimCard.getX(), (deck + 1) * mDimCard.getY());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.memory_game, menu);
		return true;
	}

	@Override
	protected void onRestoreInstanceState(Bundle saveInstanceState) {
		Log.i(TAG, "onRestoreInstanceState");

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.i(TAG, "onConfigurationChanged");
		// Checks the orientation of the screen

		GridView gridview = (GridView) findViewById(R.id.gridView1);
		int margin_horz = (int) getResources().getDimension(
				R.dimen.activity_horizontal_margin);
		int margin_vert = (int) getResources().getDimension(
				R.dimen.activity_vertical_margin);
		DisplayMetrics dm = getResources().getDisplayMetrics();

		int row = gridview.getNumColumns();

		int col = mCardsize / row;

		// Log.i(TAG, "newConfig Metrics: " + newConfig.screenWidthDp + ", "
		// + newConfig.screenHeightDp);
		// Log.i(TAG, "Metrics: " + dm.widthPixels + ", " + dm.heightPixels);
		// Log.i(TAG, "dim: " + row + ", " + col);
		// Log.i(TAG, "After Margin: " + margin_horz + ", " + margin_vert);

		int horz_padding = (dm.widthPixels - col * mDimCard.getX()) / 2;
		int vert_padding = (dm.heightPixels - row * mDimCard.getY()) / 2;
		Log.i(TAG, "padding: " + horz_padding + ", " + vert_padding);

		margin_horz = Math.round(margin_horz
				* (dm.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		margin_vert = Math.round(margin_vert
				* (dm.ydpi / DisplayMetrics.DENSITY_DEFAULT));

		gridview.setAdapter((ListAdapter) new ImageAdapter(this,
				R.drawable.cards, col, row));
		gridview.setPaddingRelative(horz_padding, vert_padding, horz_padding,
				vert_padding);
		gridview.setNumColumns(col);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
		}

	}
}
