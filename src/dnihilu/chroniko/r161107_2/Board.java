package dnihilu.chroniko.r161107_2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class Board implements Visible {
	
	private boolean playerControlled;
	public PlayerController controls;
	public GameWindow window;
	private Vector<Scrolling> notes = new Vector<Scrolling>(50, 25);
	private double length = 1;
	private double runoff = 0.3;
	private BoardType type;
	private int numLanes;
	
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
		this.fret = new boolean[numLanes+1];
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
		
		Scrolling[] stuff = notes.toArray(new Scrolling[0]);
		
		for(int i = 0; i < stuff.length; i++)
			stuff[i].paint(g2d, x, y, w, h);
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
	
	public void removeNote(Scrolling note){
		notes.remove(note);
	//	System.out.println("Note deleted");
	}

	public void addBeatMarker(boolean wide, double theTime){
		notes.add(new BeatMarker(theTime, wide, this, linecolor));
	}
	
	public void setFretState(int lane, boolean on){
		fret[lane] = on;
		
		NoteGem[] gems = notes.toArray(new NoteGem[0]);
		NoteGem playedNote = type.controlScheme().tryPlaying(fret, lane, gems);
		if(playedNote != null); //TODO finish this fucking shit goddamn
		
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
