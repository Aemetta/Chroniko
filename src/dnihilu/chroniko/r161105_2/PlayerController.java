package dnihilu.chroniko.r161105_2;

import java.awt.event.KeyEvent;

public class PlayerController {
	
	Board board;
	
	public PlayerController(Board board){
		this.board = board;
	}

	public void event(KeyEvent e, boolean on) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_Q:		board.setFretState(1, on);
		case KeyEvent.VK_W:		board.setFretState(2, on);
		case KeyEvent.VK_F:		board.setFretState(3, on);
		case KeyEvent.VK_P:		board.setFretState(4, on);
		case KeyEvent.VK_SPACE:	board.setFretState(5, on);
		}
	}
}
