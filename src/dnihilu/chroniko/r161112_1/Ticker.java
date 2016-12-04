package dnihilu.chroniko.r161112_1;


public class Ticker extends Thread{
	
	static final int fpsCap = 120;
	
	GameWindow game;
	NoteSequencer notes;
	
	public Ticker(GameWindow game, NoteSequencer notes){
		this.game = game;
		this.notes = notes;
	}
	
	public void run(){
		while(true){
			if(this.isInterrupted())
				break;
			
			long time = System.nanoTime();
			game.music.setTime(time);
			game.repaint();
			notes.refresh();
			
		/*	long diff = System.nanoTime() - time;
			
			try {Thread.sleep((int)
								(
									((double)1/fpsCap) - 
									((double)diff/1000000000)
								*1000)
								);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}*/
		}
	}
}
