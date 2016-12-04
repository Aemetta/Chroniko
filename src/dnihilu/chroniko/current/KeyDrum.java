package dnihilu.chroniko.current;

public class KeyDrum implements ControlScheme {
	@Override
	public NoteGem tryPlaying(boolean[] frets, int newFret, double hitWindow, NoteGem[] notes) {
		
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
