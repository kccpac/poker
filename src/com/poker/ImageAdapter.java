package com.poker;


import android.content.Context;
import android.graphics.*;
//import android.util.Log;
import android.view.*;
import android.widget.*;

public class ImageAdapter extends BaseAdapter {
	// private static final String TAG = ImageAdapter.class.getCanonicalName();
	private Context mContext;
	private int mCol;
	private int mRow;
	private Bitmap mCardmap;
	private Bitmap mCardbackmap;

	public ImageAdapter(Context c, int thumbRid, int col, int row) {
		mContext = c;
		mCol = col;
		mRow = row;
		mCardmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.cards);
		mCardbackmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.card_back);
	}

	public int getCount() {
		return mRow * mCol;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		// if (position >= 0 && position < mRow * mCol)
		// return mThumbIds[position];
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			// Log.i(TAG, "getView view #" + position);
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(73, 98));
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			// imageView.setPadding(0, 1, 1, 1);
		} else {
			// Log.i(TAG, "getView from convertView view #: " + position);
			imageView = (ImageView) convertView;
		}

		cardinfo cc = ((MemoryGameActivity) mContext).getcard(position);
		coord cdim = ((MemoryGameActivity) mContext).getcardDim();

		// Log.i(TAG, "Card Flipped : " + cc.isFlipped());
		if (cc.isFlipped() == true) {

			int deck = cc.getCardValue().getType();
			int cval = cc.getCardValue().getValue();

			Rect src = ((MemoryGameActivity) mContext).getCardRect(cval, deck);

			Bitmap bitmap = Bitmap.createBitmap(mCardmap, src.left, src.top,
					cdim.getX(), cdim.getY());
			imageView.setImageBitmap(bitmap);
			cc.setFlipped(true);
		} else {
			imageView.setImageBitmap(mCardbackmap);
		}

		return imageView;
	}
}
