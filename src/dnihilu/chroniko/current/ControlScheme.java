package dnihilu.chroniko.current;

public interface ControlScheme {
	public NoteGem tryPlaying(boolean frets[], int newFret, double hitWindow, NoteGem notes[]);
}
