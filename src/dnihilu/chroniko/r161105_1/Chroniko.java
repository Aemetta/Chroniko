package dnihilu.chroniko.r161105_1;


public class Chroniko {

	GameWindow game;
	MusicSystem music;
	NoteSequencer notes;
	
	public static void main(String[] args) {
		
		new Chroniko().play();
	}
	
	
	public void play(){
		
		String song = "/home/d/.PlayOnLinux/wineprefix/Phase_Shift/drive_c/Program Files/Phase Shift/music/Miscellaneous/Tarby - What You Hoped To Find/";
		
		Board board[] = {new Board("PART GUITAR"), 
						 new Board("PART DRUMS"), 
						 new Board("PART KEYS")};
		notes = new NoteSequencer(board, game, music, song);
		music = new MusicSystem(game, song);
		game = new GameWindow(board, music);
		new Ticker(game, notes).start();
	}
}
