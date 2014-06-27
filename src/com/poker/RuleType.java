package com.poker;

public enum RuleType {
	NONE(0), DEALER(1), PLAYER(2);
	
	private int idx;
	RuleType(int idx)
	{
		this.idx = idx;
	}
	public int getIdx()
	{
		return idx;
	}
}
