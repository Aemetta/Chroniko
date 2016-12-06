package com.dnihilu.chroniko;

public abstract class BoardScrolling extends Board {

	public BoardScrolling(Chroniko game) {
		super(game);
	}
	
	public abstract double getLength();
}