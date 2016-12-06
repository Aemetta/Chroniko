package com.dnihilu.chroniko;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BoardPS2D board[];
//	private boolean paused;

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(int i = 0; i < board.length; i++)
			board[i].paint(g2d, i*getWidth()/board.length, 0, getWidth()/board.length, getHeight());
	}
	
	public GameWindow() {
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				for(int i = 0; i < board.length; i++)
					if(board[i].hasController())
						board[i].getController().event(arg0, true);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				for(int i = 0; i < board.length; i++)
					if(board[i].hasController())
						board[i].getController().event(arg0, false);
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		
		JFrame frame = new JFrame("Chroniko");
		frame.add(this);
		frame.setSize(720, 405);
		frame.setVisible(true);
		frame.setFocusable(true);
		this.requestFocus();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public GameWindow(Board b[]) {
		this();
		this.addPlayers(b);
	}
	
	public void addPlayers(Board b[]) {
		this.board = new BoardPS2D[b.length];
		for(int i = 0; i < board.length; i++){
			this.board[i] = (BoardPS2D) b[i];
			
		//	this.add
		}
	}
}