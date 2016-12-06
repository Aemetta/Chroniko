package dnihilu.chroniko.current;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class Board implements Visible {
	
	private Chroniko game;
	
	private double length;
	private double runoff;
	
	private BoardType type;
	private int numLanes;

	private boolean playerControlled;
	private PlayerController controls;
	private boolean fret[];
	private double hitWindow = 0.1;
	
	private static final Color boardcolor = new Color(50, 50, 50);
	private static final Color linecolor = new Color(100, 100, 100);
	private Color notecolor[];

	private Vector<NoteGem> notes = new Vector<NoteGem>(50, 25);
	private Vector<BeatMarker> beats = new Vector<BeatMarker>(20, 5);
	private Vector<Visible> effects = new Vector<Visible>(20, 5);
	
	public Board(Chroniko game, BoardType type, double speed, boolean playerControlled){
		this.game = game;
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
		
		Visible[] other = effects.toArray(new Visible[0]);
		for(int i = 0; i < other.length; i++)
			other[i].paint(g2d, x, y, w, h);
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
	
	public void addParticle(){
		effects.add(new Particle());
	}
	
	public void setFretState(int lane, boolean on){
		fret[lane] = on;
		
		NoteGem[] gems = notes.toArray(new NoteGem[0]);
		NoteGem playedNote = type.controlScheme().tryPlaying(fret, lane, hitWindow, gems);
		if(playedNote != null);
			removeNote(playedNote);
		
	//	System.out.println("Fret " + lane);
	}
	
	public boolean[] getFretState(){
		return fret;
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
	
	public PlayerController getController(){
		return controls;
	}
	
	public Chroniko getGame(){
		return game;
	}
	
	public double getHitWindow(){
		return hitWindow;
	}
}
