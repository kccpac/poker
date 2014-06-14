package com.porker;

import android.util.Log;

public class BJCard extends card {

	private static final String TAG = BJCard.class.getCanonicalName();

	private int VALUE_OFFSET = 1;
	
	public BJCard(int num_card) {
		super();
		init_value(num_card);
		
	}	
	
	private void init_value(int num_card)
	{
		int val;
		for (int i = 0; i < num_card; i++) {
			// Log.i(TAG, "idx: " + i + " key: " + i/13 + " value:" + i%13);
			val = i%13+VALUE_OFFSET;
			cardinfo c = new cardinfo(i / 13, (val < 10)?val: 10);
		//	Log.i(TAG, "idx: " + i + " key: " + i/13 + " value:" + c.getCardValue().getValue());
			stack.add(c);
			// Log.i(TAG, "inFlipped = " + c.isFlipped());
		}
	}
	
	public int getValueOffset()
	{
		return VALUE_OFFSET;
	}

}
