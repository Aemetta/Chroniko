package com.dnihilu.chroniko;

public interface ControlScheme {
	public NotePS tryPlaying(boolean frets[], int newFret, double hitWindow, NotePS notes[]);
}
