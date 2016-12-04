package dnihilu.chroniko.r161113_1;

import java.awt.Color;

public enum BoardType {
	
	DRUMS (
			new Color[] {
					new Color(200, 125, 25),
					new Color(230, 75, 75),
					new Color(200, 200, 50),
					new Color(50, 100, 230),
					new Color(50, 200, 100)},
			PSMidiTrack.DRUMS,
			new KeyDrum()
			),
	GUITAR (
			new Color[] {
					new Color(125, 25, 175),
					new Color(50, 200, 100),
					new Color(230, 75, 75),
					new Color(200, 200, 50),
					new Color(50, 100, 230),
					new Color(230, 150, 50)},
			PSMidiTrack.GUITAR,
			new Guitar()
			),
	BASS (
			new Color[] {
					new Color(125, 25, 175), //Same as guitar
					new Color(50, 200, 100),
					new Color(230, 75, 75),
					new Color(200, 200, 50),
					new Color(50, 100, 230),
					new Color(230, 150, 50)},
			PSMidiTrack.BASS,
			new Guitar()
			),
	KEYS (
			new Color[] {
					new Color(0, 0, 0),
					new Color(100, 220, 160),
					new Color(255, 150, 150),
					new Color(220, 220, 130),
					new Color(120, 150, 255),
					new Color(255, 190, 120)},
			PSMidiTrack.KEYS,
			new KeyDrum()
			);
	
	private final Color notecolor[];
	private final int numlanes;
	private final PSMidiTrack track;
	private final ControlScheme scheme;
	
	BoardType(Color notecolor[], PSMidiTrack track, ControlScheme scheme){
		this.notecolor = notecolor;
		this.numlanes = notecolor.length - 1;
		this.track = track;
		this.scheme = scheme;
	}
	
	public Color[] noteColors(){
		return notecolor;
	}
	
	public PSMidiTrack associatedTrack(){
		return track;
	}
	
	public int lanes(){
		return numlanes;
	}
	
	public ControlScheme controlScheme(){
		return scheme;
	}
}
