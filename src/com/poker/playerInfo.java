package com.poker;

import java.util.*;

import android.graphics.Rect;
import android.util.Log;

public class playerInfo {

	private static final String TAG = playerInfo.class.getName();
	private List<cardinfo> holding;
	private RuleType mtype;
	private int vid;
	private boolean bActive;
	private coord spos;
	private Rect dim;
	private Rule mRule;
	
	public playerInfo(RuleType type) {
		holding = new ArrayList<cardinfo>();
		mtype = type;
		bActive = (type == RuleType.PLAYER) ? true : false;
		mRule = new Rule();
		Log.i(TAG, "playerInfo name: " + type.toString());
	}

	public void addcard(cardinfo card) {
		holding.add(card);
	}

	public cardinfo getCardAt(int pos) {
		return holding.get(pos);
	}

	public void cardCleanup()
	{
		holding.clear();
	}
	
	public coord getSPOS() {
		return spos;
	}

	public void setSPOS(coord spos) {
		this.spos = spos;
	}

	public Rect getDim() {
		return dim;
	}

	public void setDim(Rect dim) {
		this.dim = dim;
	}

	public void setVid(int id) {
		vid = id;
	}

	public int getVid() {
		return vid;
	}

	public RuleType getRuleType() {
		return mtype;
	}

	public boolean test(int result)
	{
		int wildcard_val = Integer.valueOf(mRule.getCondition(Rule.TEST_WILDCARD).toString());
		int ceiling_val = Integer.valueOf(mRule.getCondition(Rule.TEST_DEALER_CEILING).toString());

		if (result == wildcard_val ||
			mtype == RuleType.DEALER && result >= ceiling_val)
		{
			return true; // no more cards allowed
		}
		return false;

	}
	public int getCardTotal() {
		int total = 0;

		int i;
		int vlist[];
		

		vlist = new int [holding.size()];
		for (i = 0; i < holding.size(); i++) {
			cardinfo c = holding.get(i);
			vlist[i]= c.getCardValue().getValue();
		}
		Arrays.sort(vlist);

		int countAces=0;
		for (i = 0; i < holding.size(); i++) {
			if (vlist[i] != 1) // search for Aces
				break;
			countAces++;
		}
		
		int wildcard_val = Integer.valueOf(mRule.getCondition(Rule.TEST_WILDCARD).toString());

		for (i=holding.size()-1; i>=countAces; i--)
		{
			total += vlist[i];
		}

		if (countAces == 1)
		{
			// Apply Aces 11 rules
			int aces_val = Integer.valueOf(mRule.getCondition(Rule.WILDCARD_ACES_VALUE).toString());
			total = (total + aces_val <= wildcard_val) ? total + aces_val: total + 1;
		}
		else if (countAces > 1)
		{
			total += countAces;
		}

		return total;
	}

	public int getNumCard() {
		return holding.size();
	}

	public boolean isActive() {
		return bActive;
	}

	public void setActive(boolean bActive) {
		this.bActive = bActive;
	}
}
