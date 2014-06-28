package com.poker;

public class card_value {
	private cardType type;
	private int value;

	public card_value(cardType type, int value) {
		this.type = type;
		this.value = value;
	}

	public cardType getType() {
		return type;
	}

	public int getValue() {
		return value;
	}
}
