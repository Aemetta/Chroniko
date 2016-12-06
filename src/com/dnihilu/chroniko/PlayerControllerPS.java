package com.dnihilu.chroniko;

import java.awt.event.KeyEvent;

public class PlayerControllerPS extends PlayerController {
	
	BoardPS boardps;
	
	public PlayerControllerPS(Board board){
		super(board);
		this.boardps = (BoardPS) board;
	}

	public void event(KeyEvent e, boolean on) {
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_SLASH)	boardps.setFretState(0, on);
		if(k == KeyEvent.VK_SHIFT)	boardps.setFretState(0, on);
		if(k == KeyEvent.VK_Q)		boardps.setFretState(1, on);
		if(k == KeyEvent.VK_W)		boardps.setFretState(2, on);
		if(k == KeyEvent.VK_F)		boardps.setFretState(3, on);
		if(k == KeyEvent.VK_P)		boardps.setFretState(4, on);
		if(k == KeyEvent.VK_SPACE)	boardps.setFretState(5, on);
	}
}
