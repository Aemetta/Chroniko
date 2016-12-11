package com.dnihilu.chroniko;

import javax.swing.JPanel;

public abstract class Board extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3423879462574466330L;

	Chroniko game;

	private boolean playerControlled;
	private PlayerController controls;
	
	public Board(Chroniko game){
		this.game = game;
		this.setPlayerControlled(playerControlled);
	}
	
	public Chroniko getGame(){
		return game;
	}

	public boolean isPlayerControlled() {
		return playerControlled;
	}

	public void setPlayerControlled(boolean playerControlled) {
		this.playerControlled = playerControlled;
	}

	public PlayerController getControls() {
		return controls;
	}

	public void setControls(PlayerController controls) {
		this.controls = controls;
	}
	
	public boolean hasController(){
		return isPlayerControlled();
	}
	
	public PlayerController getController(){
		return getControls();
	}
}
