package com.dnihilu.chroniko;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class BoardPS2D extends BoardPS implements Visible {
	
	private double length;
	private double runoff;
	
	private static final Color boardcolor = new Color(50, 50, 50);
	private static final Color linecolor = new Color(100, 100, 100);
	private Color notecolor[];

	private Vector<NotePS> notes = new Vector<NotePS>(50, 25);
	private Vector<BeatMarker> beats = new Vector<BeatMarker>(20, 5);
	private Vector<Visible> effects = new Vector<Visible>(20, 5);
	
	public BoardPS2D(Chroniko game, BoardTypePS type, double speed, boolean playerControlled){
		super(game, type, playerControlled);
		this.notecolor = type.noteColors();
		
		this.length = 1/speed;
		this.runoff = length/5;
	}
	
	public void paint(Graphics2D g2d, int x, int y, int w, int h){
		
		g2d.setColor(boardcolor);
		g2d.fillRect(x, y, w, h);
		
		for(int i = 1; i <= numLanes; i++){
			if(fret[i]){
				g2d.setColor(new Color(255, 255, 255, 50));
				g2d.fillRect(	x+(w/getNumLanes())*(i-1)+1, y,
								w/getNumLanes()-1, h);
			}
		}
		
		g2d.setColor(linecolor);
		g2d.fillRect(x, (int)(y+h*length/getFullLength()), w, (int)(h*.01));

		BeatMarker[] lines = beats.toArray(new BeatMarker[0]);
		for(int i = 0; i < lines.length; i++)
			lines[i].paint(g2d, x, y, w, h);
		
		NotePS[] gems = notes.toArray(new NotePS[0]);
		for(int i = 0; i < gems.length; i++)
			gems[i].paint(g2d, x, y, w, h);
		
		Visible[] other = effects.toArray(new Visible[0]);
		for(int i = 0; i < other.length; i++)
			other[i].paint(g2d, x, y, w, h);
	}
	
	public void addNote(double startTime, double endTime, int lane, int noteType){
		double len;
		if(type == BoardTypePS.DRUMS || noteType == 0) len = 0;
		else len = endTime-startTime;
		
		if(lane >= 0 && lane < numLanes + 1){
			notes.add(new NotePS(startTime, len, lane, this, notecolor[lane]));
		}
	}
	
	public void removeNote(Scrolling thing){
		notes.remove(thing);
		beats.remove(thing);
	//	System.out.println("Note deleted");
	}

	public void addBeatMarker(boolean wide, double theTime){
		beats.add(new BeatMarker(theTime, wide, this, linecolor));
	}
	
	public void addParticle(){
		effects.add(new Particle());
	}
	
	public void setFretState(int lane, boolean on){
		fret[lane] = on;
		
		NotePS[] gems = notes.toArray(new NotePS[0]);
		NotePS playedNote = type.controlScheme().tryPlaying(fret, lane, hitWindow, gems);
		if(playedNote != null);
			removeNote(playedNote);
		
	//	System.out.println("Fret " + lane);
	}
	
	public double getLength(){
		return length;
	}
	
	public double getFullLength(){
		return length + runoff;
	}
	
	public double getRunoff(){
		return runoff;
	}
	
	public boolean hasController(){
		return isPlayerControlled();
	}
	
	public PlayerController getController(){
		return getControls();
	}
	
	public double getHitWindow(){
		return hitWindow;
	}
}
