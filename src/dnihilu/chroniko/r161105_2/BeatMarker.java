package dnihilu.chroniko.r161105_2;

import java.awt.Color;
import java.awt.Graphics2D;


public class BeatMarker implements Note {
	private double start;
	private Board board;

	private Color color;
	private double size;
	
	private double timeLeft;
	
	public BeatMarker(double start, boolean isBig, Board board, Color color){
		this.start = start;
		this.board = board;
		if(isBig) this.size = 0.01;
		else this.size = 0.005;
		
		this.color = color;
	}
	
	private void update(){
		this.timeLeft = start - board.window.music.getTime();
		if(timeLeft < 0) board.removeNote(this);
	}
	
	public void paint(Graphics2D g2d, int x, int y, int w, int h){
		update();
		
		g2d.setColor(color);
		
		g2d.fillRect(x+1,
				  (int)(y+(1-(start)/board.getLength())*h-(h*(size/2)))
				+ (int)((board.window.music.getTime())*h), 
						w-1,
						(int)(h*size));
	}
}
