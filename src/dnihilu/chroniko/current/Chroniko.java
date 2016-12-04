package dnihilu.chroniko.current;

import java.io.File;
import java.util.Arrays;

public class Chroniko {

	GameWindow game;
	MusicSystem music;
	NoteSequencer notes;
	
	public static void main(String[] args) {
		if(args.length == 1){
			new Chroniko().play(args[0]);
		}
		else if(args.length == 0) System.out.println("Please provide the path of the song.");
		else System.out.println("Put the path as one argument. If there are spaces, put quotes around the whole thing.");
	}
	
	
	public void play(String path){
		char c = File.separatorChar;
		File dir = new File(path);
		String[] files=dir.list();
		File[] audio = {};
		File midi = null;
	    for (int i=0; i<files.length; i++) {
	    	String name=files[i];
	    	if (name.toLowerCase().endsWith(".ogg")) {
	    		int len = audio.length;
	    		audio = Arrays.copyOf(audio, len + 1);
	    		audio[len] = new File(path + c + files[i]);
	    	}
	    	if (name.toLowerCase().endsWith(".mid")) {
	    		midi = new File(path + c + files[i]);
	    	}
	    	if (name.toLowerCase().endsWith(".ini")) {
	    		//TODO Read ini information
	    	}
	    }
		
		Board board[] = {new Board(BoardType.BASS, 0.8, false), 
						 new Board(BoardType.DRUMS, 0.9, false), 
						 new Board(BoardType.KEYS, 1, true)};
		notes = new NoteSequencer(board, game, music, midi);
		music = new MusicSystem(game, audio);
		game = new GameWindow(board, music);
		new Ticker(game, notes).start();
	}
}
