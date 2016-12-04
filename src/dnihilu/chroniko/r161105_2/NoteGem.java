package dnihilu.chroniko.r161105_2;

import java.awt.Color;
import java.awt.Graphics2D;


public class NoteGem implements Note {
	private double start;
	private double len;
	private int lane;
	private Board board;

	private Color notecolor;
	
	private double timeLeft;
	
	public NoteGem(double start, double len, int lane, Board board, Color color){
		this.start = start;
		this.len = len;
		this.lane = lane;
		this.board = board;
		
		this.notecolor = color;
	}
	
	private void update(){
		this.timeLeft = start - board.window.music.getTime();
		if(timeLeft + len < -0.5) board.removeNote(this);
	}
	
	public void paint(Graphics2D g2d, int x, int y, int w, int h){
		update();
		
		double size;
		if(lane == 0) size = .03;
		else size = .04;
		
		g2d.setColor(notecolor);
		
		if(lane != 0){	//Gem
		g2d.fillRect(x+(w/board.getNumLanes())*(lane-1)+1,
				  (int)(y+(1-(start)/board.getLength())*h-(h*(size/2)))
				+ (int)((board.window.music.getTime())*h), 
						(w/board.getNumLanes()-1),
						(int)(h*size));
		if(len != 0)	//Sustain line
		g2d.fillRect(x+(w/board.getNumLanes())*(lane-1)+1+(w/3)/board.getNumLanes(),
				  (int)(y+(1-(start+len)/board.getLength())*h)
				+ (int)((board.window.music.getTime())*h), 
						(w/board.getNumLanes())/3,
						(int)((len/board.getLength())*h));
		}
		else			//Open/Bass Gem
		g2d.fillRect(x+1, 
				  (int)(y+(1-(start)/board.getLength())*h-(h*(size/2)))
				+ (int)((board.window.music.getTime())*h), 
					 w-1, (int)(h*size));
	}
}
