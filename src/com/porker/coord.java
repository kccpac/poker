package com.porker;

public class coord {

	private int x, y;

	public coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void incr(int offsetx, int offsety) {
		x += offsetx;
		y += offsety;
	}

}
