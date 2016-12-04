package dnihilu.chroniko.r161105_2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class Board {
	
	boolean playerControlled;
	PlayerController controls;
	GameWindow window;
	Vector<Note> notes = new Vector<Note>(50, 25);
	private double length = 1;
	int type;
	int numLanes;
	
	boolean fret[];
	
	Color boardcolor = new Color(50, 50, 50);
	Color linecolor = new Color(100, 100, 100);
	Color notecolor[];
	
	public Board(String type, boolean playerControlled){
		if(type == "PART DRUMS") {
			notecolor = new Color[] {
					new Color(200, 125, 25),
					new Color(230, 75, 75),
					new Color(200, 200, 50),
					new Color(50, 100, 230),
					new Color(50, 200, 100)};
			this.type = 1;
			this.numLanes = 4;}
		else if(type == "PART GUITAR") {
			notecolor = new Color[] {
					new Color(125, 25, 175),
					new Color(50, 200, 100),
					new Color(230, 75, 75),
					new Color(200, 200, 50),
					new Color(50, 100, 230),
					new Color(230, 150, 50)};
			this.type = 3;
			this.numLanes = 5;}
		else if(type == "PART KEYS") {
			notecolor = new Color[] {
					new Color(0, 0, 0),
					new Color(100, 220, 160),
					new Color(255, 150, 150),
					new Color(220, 220, 130),
					new Color(120, 150, 255),
					new Color(255, 190, 120)};
			this.type = 7;
			this.numLanes = 5;}
		else System.err.println("Invalid instrument type");
		
		this.playerControlled = playerControlled;
		if(playerControlled)
			this.controls = new PlayerController(this);
		this.fret = new boolean[numLanes+2];
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
		g2d.fillRect(x, y+h*4/5, w, (int)(h*.01));
		
		Note[] gems = notes.toArray(new Note[0]);
		
		for(int i = 0; i < gems.length; i++)
			gems[i].paint(g2d, x, y*5/4, w, h);
	}
	
	public void setWindow(GameWindow window){
		this.window = window;
	}
	
	public void addNote(double startTime, double endTime, int lane, int noteType){
		double len;
		if(type == 1 || noteType == 0) len = 0;
		else len = endTime-startTime;
		
		if(lane >= 0 && lane < numLanes + 1){
			notes.add(new NoteGem(startTime, len, lane, this, notecolor[lane]));
		}
	}
	
	public void removeNote(Note note){
		notes.remove(note);
		System.out.println("Note deleted");
	}

	public void addBeatMarker(boolean wide, double theTime){
		notes.add(new BeatMarker(theTime, wide, this, linecolor));
	}
	
	public void setFretState(int lane, boolean on){
		fret[lane] = on;
	//	System.out.println("Fret " + lane);
	}
	
	public double getLength(){
		return length;
	}
	
	public int getType(){
		return type;
	}
	
	public int getNumLanes(){
		return numLanes;
	}
	
	public boolean hasController(){
		return playerControlled;
	}
}
