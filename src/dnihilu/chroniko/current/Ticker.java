package dnihilu.chroniko.current;


public class Ticker extends Thread{
	
	static final int fpsCap = 120;
	
	Chroniko game;
	
	public Ticker(Chroniko game){
		this.game = game;
	}
	
	public void run(){
		while(true){
			if(this.isInterrupted())
				break;
			
			long time = System.nanoTime();
			game.music.setTime(time);
			game.window.repaint();
			game.notes.refresh();
			
			Thread.yield();
			
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
