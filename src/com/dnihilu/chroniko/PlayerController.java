package com.dnihilu.chroniko;

import java.awt.event.KeyEvent;

public abstract class PlayerController {
	
	protected Board board;
	
	public PlayerController(Board board) {
		this.board = board;
	}

	public abstract void event(KeyEvent arg0, boolean b);

}