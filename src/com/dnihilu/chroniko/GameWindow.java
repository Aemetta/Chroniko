package com.dnihilu.chroniko;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JPanel boardPanel;
	private Board board[] = {};
	
	private final int width;
	private final int height;
	
	public void paint(Graphics g){
		for(int i = 0; i < board.length; i++)
			board[i].repaint();
	}
	
	public GameWindow() {
		super("Chroniko");
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
		
		width = 700;
		height = 400;
		setSize(width, height);
		
		setVisible(true);
		setFocusable(true);
		setResizable(false);
		requestFocus();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());
		add(mainPanel);
		boardPanel = new JPanel();
		mainPanel.add(boardPanel, BorderLayout.CENTER);
	}
	
	public GameWindow(Board b[]) {
		this();
		this.addPlayers(b);
	}
	
	public void addPlayers(Board b[]) {
		this.board = new Board[b.length];
		for(int i = 0; i < board.length; i++){
			this.board[i] = (Board) b[i];
			
			boardPanel.add(board[i]);
			board[i].setPreferredSize(new Dimension(width/b.length, height));
		}
		pack();
	}
}
