package dnihilu.chroniko.r161106_1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class Board implements Visible {
	
	boolean playerControlled;
	PlayerController controls;
	GameWindow window;
	Vector<Visible> notes = new Vector<Visible>(50, 25);
	private double length = 1;
	private BoardType type;
	int numLanes;
	
	boolean fret[];
	
	Color boardcolor = new Color(50, 50, 50);
	Color linecolor = new Color(100, 100, 100);
	Color notecolor[];
	
	public Board(BoardType type, boolean playerControlled){
		this.type = type;
		this.numLanes = type.lanes();
		this.notecolor = type.noteColors();
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
		
		Visible[] gems = notes.toArray(new Visible[0]);
		
		for(int i = 0; i < gems.length; i++)
			gems[i].paint(g2d, x, y, w, h);
	}
	
	public void setWindow(GameWindow window){
		this.window = window;
	}
	
	public void addNote(double startTime, double endTime, int lane, int noteType){
		double len;
		if(type == BoardType.DRUMS || noteType == 0) len = 0;
		else len = endTime-startTime;
		
		if(lane >= 0 && lane < numLanes + 1){
			notes.add(new NoteGem(startTime, len, lane, this, notecolor[lane]));
		}
	}
	
	public void removeNote(Visible note){
		notes.remove(note);
	//	System.out.println("Note deleted");
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
	
	public BoardType getType(){
		return type;
	}
	
	public int getNumLanes(){
		return numLanes;
	}
	
	public boolean hasController(){
		return playerControlled;
	}
}
