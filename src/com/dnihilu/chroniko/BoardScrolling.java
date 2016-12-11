package com.dnihilu.chroniko;

public abstract class BoardScrolling extends Board {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2634290713028325474L;

	public BoardScrolling(Chroniko game) {
		super(game);
	}
	
	public abstract double getLength();
	public abstract double getRunoff();
}