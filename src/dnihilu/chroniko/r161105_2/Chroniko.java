package dnihilu.chroniko.r161105_2;


public class Chroniko {

	GameWindow game;
	MusicSystem music;
	NoteSequencer notes;
	
	public static void main(String[] args) {
		
		new Chroniko().play();
	}
	
	
	public void play(){
		
		String song = "/home/d/music/Miscellaneous/GatoPaint - Half Beast/";
		
		Board board[] = {new Board("PART GUITAR", false), 
						 new Board("PART DRUMS", false), 
						 new Board("PART KEYS", true)};
		notes = new NoteSequencer(board, game, music, song);
		music = new MusicSystem(game, song);
		game = new GameWindow(board, music);
		new Ticker(game, notes).start();
	}
}
