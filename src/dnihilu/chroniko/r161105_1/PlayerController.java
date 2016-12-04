package dnihilu.chroniko.r161105_1;

import java.awt.event.KeyEvent;

public class PlayerController {
	
	Board board;
	
	public PlayerController(Board board){
		this.board = board;
	}

	public void event(KeyEvent e, boolean on) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_1: if(on) board.window.music.setTimeOffset(0.1, true);
		case KeyEvent.VK_2: if(on) board.window.music.setTimeOffset(0.01, true);
		case KeyEvent.VK_3: if(on) board.window.music.setTimeOffset(-0.01, true);
		case KeyEvent.VK_4: if(on) board.window.music.setTimeOffset(-0.1, true);
		}
	}
}
