package dnihilu.chroniko.r161106_1;

import java.awt.Color;
import java.awt.Graphics2D;


public class BeatMarker extends Scrolling{
	private Color color;
	private double size;
	
	public BeatMarker(double start, boolean isBig, Board board, Color color){
		super(start, board);
		
		if(isBig) this.size = 0.01;
		else this.size = 0.005;
		
		this.color = color;
	}
	
	public void paint(Graphics2D g2d, int x, int y, int w, int h){
		super.paint(g2d, x, y, w, h);
		g2d.setColor(color);
		
		g2d.fillRect(x+1,
				  (int)(y+(1-(start)/board.getLength())*h-(h*(size/2)))
				+ (int)((board.window.music.getTime())*h), 
						w-1,
						(int)(h*size));
	}
	
	@Override
	protected void update(){
		this.timeLeft = start - board.window.music.getTime();
		if(timeLeft < 0.2) board.removeNote(this);
	}
}
