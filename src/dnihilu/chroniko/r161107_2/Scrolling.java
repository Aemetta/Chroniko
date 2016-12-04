package dnihilu.chroniko.r161107_2;

import java.awt.Graphics2D;

public class Scrolling implements Visible {
	protected double start;
	protected double timeLeft;
	protected Board board;
	protected double blen;
	protected double brun;
	
	public Scrolling(double start, Board board){
		this.start = start;
		this.board = board;
		this.blen = board.getFullLength();
		this.brun = board.getRunoff();
	}
	
	public void update(){
		this.timeLeft = start - board.window.music.getTime();
		if(timeLeft < cutoff()) board.removeNote(this);
	}
	
	public double timeLeft(){
		return timeLeft;
	}
	
	protected double cutoff(){
		return -brun;
	}

	public void paint(Graphics2D g2d, int x, int y, int w, int h) {
		update();
	//	g2d.drawString(("" + timeLeft).substring(0,6), x, y+(int)((1-(timeLeft)/blen-brun)*h));
	}
}
