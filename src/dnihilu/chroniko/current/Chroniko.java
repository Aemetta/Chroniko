package dnihilu.chroniko.current;


public class Chroniko {

	GameWindow game;
	MusicSystem music;
	NoteSequencer notes;
	
	public static void main(String[] args) {
		
		new Chroniko().play();
	}
	
	
	public void play(){
		
		String song = "/home/d/music/Miscellaneous/GatoPaint - Half Beast/";
		
		Board board[] = {new Board(BoardType.GUITAR, 1.2, false), 
						 new Board(BoardType.DRUMS, 0.9, false), 
						 new Board(BoardType.KEYS, 1, true)};
		notes = new NoteSequencer(board, game, music, song);
		music = new MusicSystem(game, song);
		game = new GameWindow(board, music);
		new Ticker(game, notes).start();
	}
}
