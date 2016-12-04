package dnihilu.chroniko.r161107_1;

import java.awt.Graphics2D;

public class Scrolling implements Visible {
	protected double start;
	protected double timeLeft;
	protected Board board;
	
	public Scrolling(double start, Board board){
		this.start = start;
		this.board = board;
	}
	
	protected void update(){
		this.timeLeft = start - board.window.music.getTime();
		if(timeLeft < cutoff()) board.removeNote(this);
	}
	
	protected double cutoff(){
		return -board.getRunoff();
	}

	public void paint(Graphics2D g2d, int x, int y, int w, int h) {
		update();
	}
}
