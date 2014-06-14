package com.porker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Rect;
import android.util.Log;

public class card {

	private static final String TAG = card.class.getCanonicalName();
	private coord mDimCard = new coord(73, 98);
	protected List<cardinfo> stack;
	private Random rgen;

	private void init() {
		rgen = new Random();
		stack = new ArrayList<cardinfo>();
	}

	public card() {
		init();
		/*for (int i = 0; i < num_card; i++) {
			// Log.i(TAG, "idx: " + i + " key: " + i/13 + " value:" + i%13);
			cardinfo c = new cardinfo(i / 13, i % 13);
			stack.add(c);
			// Log.i(TAG, "inFlipped = " + c.isFlipped());
		} */
		Log.d(TAG, "init");
	}



	public void shuffle() {
		int pos = 0;
		cardinfo tmp = null;
		int num_card = stack.size();
		for (int i = 0; i < num_card; i++) {
			pos = rgen.nextInt(num_card);
			tmp = stack.get(i);
			stack.set(i, stack.get(pos));
			stack.set(pos, tmp);
		}
	}

	public int count() {
		return stack.size();
	}

	public cardinfo getcard(int pos) {
		return stack.get(pos);
	}

	public void removecardAt(int pos) {
		stack.remove(pos);
	}

	public void setflipped(int pos, boolean flipped) {
		cardinfo info = stack.get(pos);
		info.setFlipped(flipped);
	}

	public Rect getCardRect(int cval, int deck) {
		return new Rect(cval * mDimCard.getX(), deck * mDimCard.getY(),
				(cval + 1) * mDimCard.getX(), (deck + 1) * mDimCard.getY());
	}

	public coord getCardDim() {
		return mDimCard;
	}

}
