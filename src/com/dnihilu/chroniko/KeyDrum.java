package com.dnihilu.chroniko;

public class KeyDrum implements ControlScheme {
	@Override
	public NotePS tryPlaying(boolean[] frets, int newFret, double hitWindow, NotePS[] notes) {
		
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
