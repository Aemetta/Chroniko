package dnihilu.chroniko.r161111_1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class Board implements Visible {
	
	public GameWindow window;
	
	private double length;
	private double runoff;
	
	private BoardType type;
	private int numLanes;

	private boolean playerControlled;
	public PlayerController controls;
	boolean fret[];
	
	private static final Color boardcolor = new Color(50, 50, 50);
	private static final Color linecolor = new Color(100, 100, 100);
	private Color notecolor[];

	private Vector<NoteGem> notes = new Vector<NoteGem>(50, 25);
	private Vector<BeatMarker> beats = new Vector<BeatMarker>(50, 25);
	
	public Board(BoardType type, double speed, boolean playerControlled){
		this.type = type;
		this.numLanes = type.lanes();
		this.notecolor = type.noteColors();
		
		this.length = 1/speed;
		this.runoff = length/5;
		
		this.playerControlled = playerControlled;
		if(playerControlled)
			this.controls = new PlayerController(this);
		this.fret = new boolean[numLanes+1];
	}
	
	public Board(BoardType type, boolean playerControlled){
		this(type, 1, playerControlled);
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
		
		NoteGem[] gems = notes.toArray(new NoteGem[0]);
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
	
	public void removeNote(Scrolling thing){
		notes.remove(thing);
		beats.remove(thing);
	//	System.out.println("Note deleted");
	}

	public void addBeatMarker(boolean wide, double theTime){
		beats.add(new BeatMarker(theTime, wide, this, linecolor));
	}
	
	public void setFretState(int lane, boolean on){
		fret[lane] = on;
		
		NoteGem[] gems = notes.toArray(new NoteGem[0]);
		NoteGem playedNote = type.controlScheme().tryPlaying(fret, lane, gems);
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
