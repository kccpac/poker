package com.porker;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;

public class GameView extends View {

	private static final String TAG = GameView.class.getName();
	private List<playerInfo> plst;
	private coord mDimCard = new coord(73, 98);
	private Bitmap mCardMap;
	private Bitmap mCardbackMap;
	private BlackJackActivity ctxt;
	private Paint mPaint;
	private card mCard;
	private coord mCarddim;
	private RuleType rType;
	private float mStepX, mStepY;
	private int xPadding;
	private int yPadding;
	private int nMaxCardPerRow;
	private int nMaxCardPerCol;
	private boolean mRefresh;
	private int mCountStep;
	private Handler mHandler;

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GameView(Context context, Handler handler, List<playerInfo> plst,
			RuleType rType) {
		super(context);
		this.plst = plst;
		ctxt = (BlackJackActivity) context;

		mCard = ctxt.getCard();
		mCarddim = mCard.getCardDim();
		this.rType = rType;
		mHandler = handler;

		mCardMap = BitmapFactory.decodeResource(getResources(),
				R.drawable.cards);
		mCardbackMap = BitmapFactory.decodeResource(getResources(),
				R.drawable.card_back);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);
		mStepX = mStepY = 0;

	}

	private void init() {
		int width = this.getWidth();
		int height = this.getHeight();
		nMaxCardPerRow = (width) / (2 * mCarddim.getX());
		nMaxCardPerCol = (height) / (2 * mCarddim.getY());

		nMaxCardPerCol = (nMaxCardPerCol == 0) ? 1 : nMaxCardPerCol;
		nMaxCardPerRow = (nMaxCardPerRow == 0) ? 1 : nMaxCardPerRow;
		// Log.i(TAG, "Width:" + width + " # card per row:" + nMaxCardPerRow);
		// Log.i(TAG, "Height:" + height + " # card per col:" + nMaxCardPerCol);
		xPadding = (width - mCarddim.getX() * nMaxCardPerRow)
				/ (2 * nMaxCardPerRow);

		yPadding = (height - mCarddim.getY() * nMaxCardPerCol)
				/ (2 * nMaxCardPerCol);
	}

	private void draw_deck(Canvas canvas, playerInfo pinfo) {
		// Log.i(TAG, "draw_deck stepx=" + mStepX + ", stepy=" + mStepY);
		// Log.i(TAG, "Width:" + getWidth() + ", height:" + getHeight());
		Rect dst = new Rect(mDimCard.getX(), 0, 2 * mDimCard.getX(), 0);
		// canvas.rotate(270, mDimCard.getX()/2, mDimCard.getY()/2);
		// Bitmap bitmap = Bitmap.createBitmap(mCardbackMap, 0, 0,
		// mCarddim.getX(), mCarddim.getY());
		canvas.drawBitmap(mCardbackMap, pinfo.getSPOS().getX(), pinfo.getSPOS()
				.getY(), mPaint);
		/*
		 * mStepX+=5; //mStepY+=1; if (mStepX < getWidth())// && mStepY <
		 * getHeight()) { Log.i(TAG, "postInvalidateDelayed"); postInvalidate();
		 * }
		 */
	}

	private void draw_dealer(Canvas canvas, playerInfo pinfo, int playerId) {

		/*
		 * if (pinfo.isActive() == false) { Rect dst = new Rect(0, 0, 0, 0);
		 * canvas.drawBitmap(mCardbackMap, dst.left, dst.top, mPaint); } else
		 */
		{
			draw_player(canvas, pinfo, playerId);
		}

	}

	private void show_card(Canvas canvas, cardinfo cinfo, coord pos) { // playerInfo
																		// pinfo)
																		// {
		/*
		 * int width = getWidth(); int height = getHeight(); int nMaxCardPerRow
		 * = (width) / (2 * mCarddim.getX()); int nMaxCardPerCol = (height) / (2
		 * * mCarddim.getY());
		 * 
		 * nMaxCardPerCol = (nMaxCardPerCol == 0) ? 1 : nMaxCardPerCol;
		 * nMaxCardPerRow = (nMaxCardPerRow == 0) ? 1 : nMaxCardPerRow;
		 * Log.i(TAG, "Width:" + width + " # card per row:" + nMaxCardPerRow);
		 * Log.i(TAG, "Height:" + height + " # card per col:" + nMaxCardPerCol);
		 * int xPadding = (width - mCarddim.getX() * nMaxCardPerRow) / (2 *
		 * nMaxCardPerRow);
		 * 
		 * int yPadding = (height - mCarddim.getY() * nMaxCardPerCol) / (2 *
		 * nMaxCardPerCol);
		 */
		// Log.i(TAG, "padding:" + xPadding + ", " + yPadding);
		// coord pos = new coord(0, pinfo.getSPOS().getY());
		// cardinfo cinfo = null;
		card_value cval = null;
		Rect src = null;
		Rect dst = null;
		Bitmap bitmap = null;
		int value_offset = ctxt.getCard().getValueOffset();
		// for (int i = 0; i < pinfo.getNumCard(); i++)
		{
			// cinfo = pinfo.getCardAt(i);
			cval = cinfo.getCardValue();
			src = mCard.getCardRect(cval.getValue() - value_offset,
					cval.getType());
			dst = new Rect(pos.getX(), pos.getY(),
					pos.getX() + mCarddim.getX(), pos.getY() + mCarddim.getY());
			bitmap = Bitmap.createBitmap(mCardMap, src.left, src.top,
					mCarddim.getX(), mCarddim.getY());
			// Log.i(TAG, "src Bitmap pos: " + src.left + ", " + src.top);
			// Log.i(TAG, "dst Bitmap pos: " + dst.left + ", " + dst.top);
			canvas.drawBitmap(bitmap, dst.left, dst.top, mPaint);

			// if (i % nMaxCardPerRow < nMaxCardPerRow) {
			// pos.incr(xPadding/* + mCarddim.getX() / 4 */, 0);
			// }
		}
	}

	/*
	 * private boolean move_card(Canvas canvas, cardinfo cinfo, coord pos) {//
	 * playerInfo pinfo) {
	 * 
	 * playerInfo deckInfo = null; for (int i = 0; i < plst.size(); i++) {
	 * playerInfo p = plst.get(i); if (p.getRuleType() == RuleType.NONE) {
	 * deckInfo = p; break; } }
	 * 
	 * //int width = getWidth(); //int height = getHeight(); //int
	 * nMaxCardPerRow = (width) / (2 * mCarddim.getX()); //int nMaxCardPerCol =
	 * (height) / (2 * mCarddim.getY());
	 * 
	 * //nMaxCardPerCol = (nMaxCardPerCol == 0) ? 1 : nMaxCardPerCol;
	 * //nMaxCardPerRow = (nMaxCardPerRow == 0) ? 1 : nMaxCardPerRow;
	 * //Log.i(TAG, "Width:" + width + " # card per row:" + nMaxCardPerRow);
	 * //Log.i(TAG, "Height:" + height + " # card per col:" + nMaxCardPerCol);
	 * //int xPadding = (width - mCarddim.getX() * nMaxCardPerRow) // / (2 *
	 * nMaxCardPerRow);
	 * 
	 * // int yPadding = (height - mCarddim.getY() * nMaxCardPerCol)/ (2 * //
	 * nMaxCardPerCol);
	 * 
	 * coord dpos = deckInfo.getSPOS(); //coord ppos = pinfo.getSPOS();
	 * Log.i(TAG, "Deck pos: " + dpos.getX() + ", " + dpos.getY()); Log.i(TAG,
	 * "Before Card pos: " + pos.getX() + ", " + pos.getY()); //
	 * ppos.incr(xPadding * pinfo.getNumCard(), 0); Log.i(TAG,
	 * "After Card pos: " + pos.getX() + ", " + pos.getY()); float slope =
	 * (pos.getY() - dpos.getY()) / (pos.getX() - dpos.getX()); int stepx =
	 * (pos.getX() - dpos.getX()) / 10; int y = (int) slope * dpos.getX() +
	 * stepx; canvas.drawBitmap(mCardbackMap, dpos.getX() + stepx, y, mPaint);
	 * //pinfo.setSPOS(new coord(dpos.getX() + stepx, y));
	 * 
	 * mStepX += 5; // mStepY+=1; return true; }
	 */

	private Bitmap getBitmap(cardinfo cinfo) {
		// Bitmap bitmap = null;

		if (cinfo.getFlipped() == false)
			return mCardbackMap;

		int value_offset = ctxt.getCard().getValueOffset();
		card_value cval = cinfo.getCardValue();

		Rect src = mCard.getCardRect(cval.getValue() - value_offset,
				cval.getType());
		return Bitmap.createBitmap(mCardMap, src.left, src.top,
				mCarddim.getX(), mCarddim.getY());
	}

	private Rect getBMRect(coord pos) {
		Rect dst = new Rect(pos.getX(), pos.getY(), pos.getX()
				+ mCarddim.getX(), pos.getY() + mCarddim.getY());
		return dst;
	}

	private coord calStep(cardinfo cinfo, coord pos) {// playerInfo pinfo) {

		playerInfo deckInfo = null;
		for (int i = 0; i < plst.size(); i++) {
			playerInfo p = plst.get(i);
			if (p.getRuleType() == RuleType.NONE) {
				deckInfo = p;
				break;
			}
		}

		coord dpos = deckInfo.getSPOS();
		// Log.i(TAG, "Deck pos: " + dpos.getX() + ", " + dpos.getY());
		// Log.i(TAG, "Before Card pos: " + pos.getX() + ", " + pos.getY());
		// Log.i(TAG, "After Card pos: " + pos.getX() + ", " + pos.getY());
		int stepx = 0, stepy = 0;
		if (pos.getX() - dpos.getX() > 0) {
			float slope = (pos.getY() - dpos.getY())
					/ (pos.getX() - dpos.getX());
			stepx = (pos.getX() - dpos.getX()) / 10;
			stepy = (int) slope * stepx;
		} else {
			stepy = (pos.getY() - dpos.getY()) / 10;
		}
		return new coord(stepx, stepy);
	}

	private playerInfo getDeck() {
		playerInfo deckInfo = null;
		for (int i = 0; i < plst.size(); i++) {
			playerInfo p = plst.get(i);
			if (p.getRuleType() == RuleType.NONE) {
				deckInfo = p;
				break;
			}
		}
		return deckInfo;
	}

	/*
	 * private int draw_stationary() { int cplayerid = 0; for (int i = 0; i <
	 * pinfo.getNumCard(); i++) //int i = pinfo.getNumCard() - 1; { cinfo =
	 * pinfo.getCardAt(i); //cinfo.flipped = true; bitmap = getBitmap(cinfo);
	 * 
	 * 
	 * { //nPos = show_card(canvas, cinfo, pos); cinfo.setFlipped(true);
	 * 
	 * } nRect = getBMRect(pos); canvas.drawBitmap(bitmap, nRect.left,
	 * nRect.top, mPaint);
	 * 
	 * if (i % nMaxCardPerRow < nMaxCardPerRow) { pos.incr(xPadding, 0); } }
	 * return cplayerid; }
	 * 
	 * private void draw_moving() {
	 * 
	 * // for (int i = 0; i < pinfo.getNumCard(); i++) //int i =
	 * pinfo.getNumCard() - 1; { cinfo = pinfo.getCardAt(i); //cinfo.flipped =
	 * true; bitmap = getBitmap(cinfo);
	 * 
	 * if (cinfo.getFlipped() == false && i > 0) { //Log.i(TAG, "print card i: "
	 * + i); playerInfo deckInfo = getDeck(); pos = new coord
	 * (deckInfo.getSPOS().getX(), deckInfo.getSPOS().getY()); // nRect =
	 * move_card(canvas, cinfo, pos);
	 * pos.incr(cinfo.getStep().getX()*mCountStep,
	 * cinfo.getStep().getY()*mCountStep);
	 * 
	 * ret = true; if (mCountStep >= 10) { cinfo.setFlipped(true); } }
	 * 
	 * nRect = getBMRect(pos); //Log.i(TAG, "Bitmap pos: " + pos.getX() + ", " +
	 * pos.getY()); //Log.i(TAG, "Padding x: " + xPadding + " y: " + yPadding);
	 * canvas.drawBitmap(bitmap, nRect.left, nRect.top, mPaint); }
	 * 
	 * }
	 */

	private boolean draw_player(Canvas canvas, playerInfo pinfo, int playerId) {
		// if (pinfo.isActive() == true)
		// {
		cardinfo cinfo = null;
		coord pos = new coord(0, pinfo.getSPOS().getY());
		Rect nRect = null;
		Bitmap bitmap = null;
		boolean ret = false;

		for (int i = 1; i < pinfo.getNumCard(); i++) {
			cinfo = pinfo.getCardAt(i);
			if (cinfo.getFlipped() == false && cinfo.getStep() == null) {
				cinfo.setStep(calStep(cinfo, pos));
			}
		}

		for (int i = 0; i < pinfo.getNumCard(); i++)
		// int i = pinfo.getNumCard() - 1;
		{
			cinfo = pinfo.getCardAt(i);
			// cinfo.flipped = true;
			bitmap = getBitmap(cinfo);

			if (cinfo.getFlipped() == false && i > 0) {
				// Log.i(TAG, "print card i: " + i);
				playerInfo deckInfo = getDeck();
				pos = new coord(deckInfo.getSPOS().getX(), deckInfo.getSPOS()
						.getY());
				// nRect = move_card(canvas, cinfo, pos);
				pos.incr(cinfo.getStep().getX() * mCountStep, cinfo.getStep()
						.getY() * mCountStep);

				ret = true;
				if (mCountStep >= 10) {
					cinfo.setFlipped(true);
				}
			} else {
				// nPos = show_card(canvas, cinfo, pos);
				cinfo.setFlipped(true);

			}
			nRect = getBMRect(pos);
			// Log.i(TAG, "Bitmap pos: " + pos.getX() + ", " + pos.getY());
			// Log.i(TAG, "Padding x: " + xPadding + " y: " + yPadding);
			canvas.drawBitmap(bitmap, nRect.left, nRect.top, mPaint);

			if (ret == true) {
				return ret;
			}
			if (i % nMaxCardPerRow < nMaxCardPerRow) {
				pos.incr(xPadding/* + mCarddim.getX() / 4 */, 0);
			}
		}
		Log.i(TAG, "draw_player");
		// send main thread a msg to get the total
		try {
			Messenger msgr = new Messenger(mHandler);
			Message msg = Message.obtain();
			Bundle data = new Bundle();
			data.putBoolean("getTotal", true);
			data.putInt("playerId", playerId);
			msg.setData(data);
			msgr.send(msg);
		} catch (Exception e) {

		}

		return false;
		// }
		// show_card(canvas, pinfo);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// Log.i(TAG, "onDraw position: " + getX() + " ," + getY() + " dim: "
		// + getWidth() + ", " + getHeight());
		// Log.i(TAG, "thread id: " + Thread.currentThread().getId());
		init();
		// canvas.save();
		/*
		 * if (rType == RuleType.NONE) { draw_deck(canvas); return; } if
		 * (ctxt.getPlayerId() == -1) return; playerInfo pinfo =
		 * plst.get(ctxt.getPlayerId());
		 * 
		 * Log.i(TAG, "Player id: " + ctxt.getPlayerId() + " RuleType: " +
		 * pinfo.getRuleType().toString());
		 */
		for (int i = 0; i < plst.size(); i++) {
			playerInfo pinfo = plst.get(i);

			if (pinfo.getRuleType() == RuleType.NONE) {
				draw_deck(canvas, pinfo);
			} else {
				mRefresh = draw_player(canvas, pinfo, i);

				if (mRefresh == true)
					break;
			}
		}

		if (mRefresh == true) {
			// Log.i(TAG, "postInvalidateDelayed");
			mCountStep++;
			postInvalidateDelayed(50);
		} else {
			mCountStep = 0;
		}
		// notify();

		// canvas.restore();
		// ctxt.resetPlayerId();

		// this.postInvalidate();
		// postInvalidateDelayed(20);
	}

	private void updateCanvas() {
		postInvalidateDelayed(50);
	}

}
