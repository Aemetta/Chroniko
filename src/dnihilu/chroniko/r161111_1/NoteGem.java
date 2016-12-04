package dnihilu.chroniko.r161111_1;

import java.awt.Color;
import java.awt.Graphics2D;


public class NoteGem extends Scrolling{
	private double len;
	private int lane;
	private int numLanes;

	private Color notecolor;
	
	public NoteGem(double start, double len, int lane, Board board, Color color){
		super(start, board);
		
		this.len = len;
		this.lane = lane;
		this.numLanes = board.getNumLanes();
		
		this.notecolor = color;
	}
	
	public void paint(Graphics2D g2d, int x, int y, int w, int h){
		super.paint(g2d, x, y, w, h);
		
		double size;
		if(lane == 0) size = .03;
		else size = .04;
		
		g2d.setColor(notecolor);
		
		if(lane != 0){	//Gem
		g2d.fillRect(x+(w/numLanes)*(lane-1)+1,
				  (int)(y+pos(0)*h-(h*(size/2))), 
						(w/board.getNumLanes()-1),
						(int)(h*size));
		if(len != 0)	//Sustain line
		g2d.fillRect(x+(w/numLanes)*(lane-1)+1+(w/3)/board.getNumLanes(),
				  (int)(y+pos(len)*h), 
						(w/board.getNumLanes())/3,
						(int)(len/blen*h));
		}
		else			//Open/Bass Gem
		g2d.fillRect(x+1, 
				  (int)(y+pos(0)*h-(h*(size/2))), 
					 w-1, (int)(h*size));
	}
	
	@Override
	protected double cutoff(){
		return -(brun+len);
	}
	
	public int getLane(){
		return lane;
	}
}
