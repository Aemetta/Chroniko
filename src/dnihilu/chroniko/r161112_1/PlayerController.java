package dnihilu.chroniko.r161112_1;

import java.awt.event.KeyEvent;

public class PlayerController {
	
	Board board;
	
	public PlayerController(Board board){
		this.board = board;
	}

	public void event(KeyEvent e, boolean on) {
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_SLASH)	board.setFretState(0, on);
		if(k == KeyEvent.VK_SHIFT)	board.setFretState(0, on);
		if(k == KeyEvent.VK_Q)		board.setFretState(1, on);
		if(k == KeyEvent.VK_W)		board.setFretState(2, on);
		if(k == KeyEvent.VK_F)		board.setFretState(3, on);
		if(k == KeyEvent.VK_P)		board.setFretState(4, on);
		if(k == KeyEvent.VK_SPACE)	board.setFretState(5, on);
	}
}
