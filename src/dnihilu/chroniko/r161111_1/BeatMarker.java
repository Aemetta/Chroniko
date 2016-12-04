package dnihilu.chroniko.r161111_1;

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
				  (int)(y+pos(0)*h-(h*(size/2))), 
						w-1,
						(int)(h*size));
	}
	
	@Override
	protected double cutoff(){
		return 0;
	}
}
