package com.porker;

import java.util.List;

public class MGCard extends card {

	public MGCard(int num_card) {
		super();
		init_value(num_card);
	}

	public MGCard(List<cardinfo> mlist) {
		super();
		for (int i = 0; i < mlist.size(); i++) {
			stack.add(mlist.get(i));
		}
	}
	private void init_value(int num_card)
	{
		for (int i = 0; i < num_card; i++) {
			// Log.i(TAG, "idx: " + i + " key: " + i/13 + " value:" + i%13);
			cardinfo c = new cardinfo(i / 13, i%13);
			stack.add(c);
			// Log.i(TAG, "inFlipped = " + c.isFlipped());
		}
	}
}
