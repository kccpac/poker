package com.poker;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class CardFlyingActivity extends Activity {

	protected static final String TAG = CardFlyingActivity.class.getName();
	private Bitmap mCardMap;
	private Paint mPaint;
	private Bitmap mCardbackMap;
	private Bitmap mIMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_card_flying);
		
		card c = new MGCard(52);
		
		c.shuffle();
		final coord d = c.getCardDim();
		final coord center = new coord (d.getX()/2, d.getY()/2);
		cardinfo cc = c.getcard(0);
		card_value val = cc.getCardValue();
		mCardMap = BitmapFactory.decodeResource(getResources(),
				R.drawable.cards);
		mCardbackMap = BitmapFactory.decodeResource(getResources(),
				R.drawable.card_back);
		//int value_offset = c..getValueOffset();
		
		Rect src = c.getCardRect(val.getValue()-1, val.getType().ordinal());
		mIMap = Bitmap.createBitmap(
				mCardMap,
				src.left,
				src.top,
				d.getX(),
				d.getY());
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);
		
	//	FragmentManager manager = getFragmentManager();
	//	android.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
		
		View v = new View (this) {
			
			coord p0, p1;
			coord offset;
			private boolean mInit = false;
			private boolean mEnd = false;

			void move(Canvas canvas, int width, int height) {
				if ( mInit == false )
				{
					DisplayMetrics dm = getResources().getDisplayMetrics();
					p0 = new coord(0,0);
					mInit = true;
					p1 = new coord(width/2-d.getX(), height/2-d.getY());
					Log.i(TAG, "p0: (" + p0.getX() + ", " + p0.getY() + "), "
							+ "p1: (" + p1.getX() + ", " + p1.getY() + ")");
					offset = new coord (
							(int) ((p1.getX()-p0.getX())/20),
							(int) ((p1.getY()-p0.getY())/20)
							);
				}
				else
				{
					p0.incr(offset.getX(), offset.getY());
				}
			}
			
			private boolean IsEnd()
			{
			//	Log.i(TAG, "p0: (" + p0.getY() + ", " + p0.getX() + ")"
			//			+ "p1: (" + p1.getY() + ", " + p1.getX() + ")");
				//if (p0.getX() > p1.getX() && p0.getY() > p1.getY())
				if ( Math.abs(p0.getX()) > p1.getX() || Math.abs(p0.getY()) > p1.getY())
				{
					//Log.i(TAG, "p0: (" + p0.getX() + ", " + p0.getY() + ")");
					return true;
				}
				return false;
			}
			
			
			@Override
			public void onDraw(Canvas canvas) {

				
				// clockwise rotation - 90 deg, anti-clockwise - 270 deg 
			//	canvas.rotate(90, center.getX(), center.getY());

				
				//float scaleX = d.getX() / ((float) getWidth());
				//float scaleY = d.getY() / ((float) getHeight());
				move(canvas, getWidth(), getHeight());
				
				Bitmap map = (IsEnd() == false) ? mCardbackMap: mIMap;

				//coord p = new coord((int) (p0.getX() * scaleX), (int)(p0.getY() * scaleY));
				
				canvas.drawBitmap(map, p0.getX(), p0.getY(), mPaint);
				
				if (IsEnd() == false)
				{
				//	Log.i(TAG, "postInvalidateDelayed");
					postInvalidateDelayed(100);
				}
				else
				{
				//	canvas.drawBitmap(map, p0.getX(), p0.getY(), mPaint);
				}
				
			}
		};
		setContentView(v);
		//cc.getCardValue().
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.card_flying, menu);
		return true;
	}

}
