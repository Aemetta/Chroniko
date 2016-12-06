package dnihilu.chroniko.current;

import java.io.File;
import java.util.Arrays;

public class Chroniko {

	GameWindow window;
	MusicSystem music;
	NoteSequencer notes;
	Board board[];
	
	public static void main(String[] args) {
		if(args.length == 1){
			new Chroniko().play(args[0]);
		}
		else if(args.length == 0) System.err.println("Please provide the path of the song.");
		else System.err.println("Put the path as one argument. If there are spaces, put quotes around the whole thing.");
	}
	
	
	public void play(String path){
		window = new GameWindow();
		
		char c = File.separatorChar;
		File dir = new File(path);
		if(!dir.exists()){System.err.println("Directory does not exist.");return;}
		
		String[] files=dir.list();
		File[] audio = {};
		File midi = null;
		boolean check[] = {false,false,false};
	    for (int i=0; i<files.length; i++) {
	    	String name=files[i];
	    	if (name.toLowerCase().endsWith(".ogg")) {
	    		int len = audio.length;
	    		audio = Arrays.copyOf(audio, len + 1);
	    		audio[len] = new File(path + c + files[i]);
	    		check[0] = true;
	    	}
	    	if (name.toLowerCase().endsWith(".mid")) {
	    		midi = new File(path + c + files[i]);
	    		check[1] = true;
	    	}
	    	if (name.toLowerCase().endsWith(".ini")) {
	    		//TODO Read ini information
	    		check[2] = true;
	    	}
	    }
		
	    board = new Board[]{new Board(this, BoardType.BASS, 0.8, false), 
							new Board(this, BoardType.DRUMS, 0.9, false), 
							new Board(this, BoardType.KEYS, 1, true)};
	    
		window.addPlayers(board);
		
		notes = new NoteSequencer(board, midi);
		music = new MusicSystem(this, audio);
		new Ticker(this).start();
	}
	
	public GameWindow getWindow(){return window;}
	public Board[] getBoards(){return board;}
	public MusicSystem getMusic(){return music;}
	public NoteSequencer getNoteSequencer(){return notes;}
}
