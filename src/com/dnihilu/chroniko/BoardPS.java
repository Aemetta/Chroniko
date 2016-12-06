package com.dnihilu.chroniko;

public abstract class BoardPS extends BoardScrolling {
	
	
	
	protected BoardTypePS type;
	protected int numLanes;

	protected boolean fret[];
	protected double hitWindow = 0.1;

	public BoardPS(Chroniko game, BoardTypePS type, boolean playerControlled) {
		super(game);
		this.type = type;
		this.numLanes = type.lanes();
		
		this.fret = new boolean[numLanes+1];
		

		if(playerControlled)
			this.setControls(new PlayerControllerPS(this));
	}
	
	public BoardTypePS getType(){
		return type;
	}
	
	public int getNumLanes(){
		return numLanes;
	}
	
	public boolean[] getFretState(){
		return fret;
	}

	protected abstract void setFretState(int i, boolean on);

	public abstract void addNote(double time, double time2, int lane, int noteLength);

	public abstract void addBeatMarker(boolean b, double time);
}
