package com.poker;

public class card_value {
	private int type;
	private int value;

	public card_value(int type, int value) {
		this.type = type;
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public int getValue() {
		return value;
	}
}
