package com.dnihilu.chroniko;

public abstract class Board {
	
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
}
