package dnihilu.chroniko.r161112_1;

import java.awt.Color;
import java.awt.Graphics2D;


public class NoteGem extends Scrolling{
	private double len;
	private int lane;
	private int numLanes;
	
	private Color notecolor;
	private Color tailcolor;
	private double size;
//	private double width;
	private double tailwidth;
	
	public NoteGem(double start, double len, int lane, Board board, Color color){
		super(start, board);
		
		this.len = len;
		this.lane = lane;
		this.numLanes = board.getNumLanes();
		
		this.notecolor = color;
		if(lane == 0) size = .03;
		else size = .04;
//		width = 1;
		if(len > 0){
			this.tailcolor = color.darker();
			this.tailwidth = 1/4;
		}
	}
	
	public void paint(Graphics2D g2d, int x, int y, int w, int h){
		super.paint(g2d, x, y, w, h);
		
		g2d.setColor(notecolor);
		
		if(lane != 0){	//Gem
		g2d.fillRect(x+(w/numLanes)*(lane-1)+1,
				  (int)(y+pos(0)*h-(h*(size/2))), 
						(w/board.getNumLanes()-1),
						(int)(h*size));
		if(len != 0){	//Sustain line
			g2d.setColor(tailcolor);
			g2d.fillRect(x+(w/numLanes)*(lane-1)+1+(int)(w+0.5-tailwidth/2)/numLanes,
					  (int)(y+pos(len)*h), 
							(int)(w/board.getNumLanes()*tailwidth),
							(int)(len/blen*h));
			}
		}
		else			//Open/Bass Gem
		g2d.fillRect(x+1, 
				  (int)(y+pos(0)*h-(h*(size/2))), 
					 w-1, (int)(h*size));
	}
	
	protected void fail(){
		notecolor = new Color(255, 0, 0, 100);
		if(len > 0){
			
		}
	}
	
	@Override
	protected double cutoff(){
		return -(brun+len);
	}
	
	public int getLane(){
		return lane;
	}
}
