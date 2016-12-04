package dnihilu.chroniko.r161111_1;

public class KeyDrum implements ControlScheme {
	public static final double hitWindow = 0.05;
	
	@Override
	public NoteGem tryPlaying(boolean[] frets, int newFret, NoteGem[] notes) {
		
		for(int i = 0; i < notes.length; i++){
			if(newFret == notes[i].getLane()){
				notes[i].update();
				if(Math.abs(notes[i].timeLeft()) < hitWindow)
					return notes[i];
			}
		}
		return null;
	}
}
