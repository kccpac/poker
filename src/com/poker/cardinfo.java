package com.poker;

public class cardinfo {
	card_value cval;
	boolean flipped;
	coord step;

	public cardinfo(int type, int val) {
		this.cval = new card_value(type, val);
		flipped = false;
		step = null;
	}

	public boolean getFlipped() {
		return flipped;
	}

	public card_value getCardValue() {
		return cval;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}

	public boolean isFlipped() {
		return flipped == true;
	}
	
	public coord getStep() {
		return step;
	}

	public void setStep(coord step) {
		this.step = new coord (step.getX(), step.getY());
	}

}
