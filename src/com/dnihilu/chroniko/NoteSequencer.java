package com.dnihilu.chroniko;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;


public class NoteSequencer {
	
	Sequence sequence;
	boolean ppqMode;
	int res;
	
	/*
	 * The following variables all have to do with each tempo marker.
	 * The index is which marker it is, in chronological order.
	 * Tempo is currently only accounted for in PPQ timing.
	 */
	
	double ticksPerSecond[] = new double[1];
	double tempoStartTime[];
	long tempoMarker[];
	
	/*
	 * The following variables all have to do with each board.
	 * The first index in the array is the board number it's associated with.
	 * This first set is variables that don't change as notes get read:
	 */
	
	BoardPS board[];
	Track track[];
	boolean finished[];
	int offset[];
	
	// This second set does change as notes get loaded.

	int index[];
	double loadedTime[];
	long loadedTick[];
	
	/*
	 * Since notes are only fully parsed once the 'end' event is read,
	 * this array keeps track of where notes have started;
	 * the second index in the array is the lane it's in,
	 * and the value is the tick that it starts.
	 */
	
	long startedNote[][];
	
	Track trackList[] = new Track[30];
	
	public NoteSequencer(BoardPS board[], File path) {
		this.board = board;
		for(int i = 0; i < board.length; i++)
		try
		{
			sequence = MidiSystem.getSequence(new FileInputStream(path));
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Constructs the trackList based on the names of each track in the sequence
		
		for(int i = 1; i < sequence.getTracks().length; i++){
			Track n = sequence.getTracks()[i];
			for(int j = 0; j < n.size(); j++){
				byte[] e = n.get(j).getMessage().getMessage();
				if((int)e[0] == -1 && (int)e[1] == 3){
					String a = "";
					for(int k = 3; k < e.length; k++)
						a += String.valueOf((char)e[k]);
					if(a.startsWith("PART ")) a = a.substring(5);
				//	System.out.println(a);
					trackList[PSMidiTrack.valueOf(a).ordinal()] = n;
					break;
				}
			}
		}
		
	//	ticksPerSecond[0] = (double)sequence.getTickLength() / (sequence.getMicrosecondLength()/1000000);
	//	this.ppqMode = false;
		
		this.res = sequence.getResolution();
		System.out.println(res);
		if(sequence.getDivisionType() != Sequence.PPQ){
			this.ppqMode = false;
			float type = sequence.getDivisionType();
			
			if(type == Sequence.SMPTE_24) ticksPerSecond[0] = res * 24;
			if(type == Sequence.SMPTE_25) ticksPerSecond[0] = res * 25;
			if(type == Sequence.SMPTE_30) ticksPerSecond[0] = res * 30;
			if(type == Sequence.SMPTE_30DROP) ticksPerSecond[0] = res * 29.97;
			
			System.out.println("SMPTE load");
		}else{
			this.ppqMode = true;
			ticksPerSecond[0] = .5 * res;
			tempoMarker = new long[]{0};
			tempoStartTime = new double[]{0.0};
			
			Track n = sequence.getTracks()[0];
			for(int j = 0; j < n.size(); j++){
				byte[] e = n.get(j).getMessage().getMessage();
				if((int)e[0] == -1 && (int)e[1] == 3){ //Track Name
					String a = "";
					for(int k = 3; k < e.length; k++)
						a += String.valueOf((char)e[k]);
					System.out.println("PPQ load: \"" + a + "\"");
				}
				if((int)e[0] == -1 && (int)e[1] == 81){
					int len = ticksPerSecond.length;
					int newtempo = (int)e[3] * 0x10000 + (int)e[4] * 0x100 + (int)e[5];
					
					ticksPerSecond = Arrays.copyOf(ticksPerSecond, len + 1);
					ticksPerSecond[len] = 
							((double)res / ((double)newtempo/1000000));
					
					tempoMarker = Arrays.copyOf(tempoMarker, len + 1);
					tempoMarker[len] = n.get(j).getTick();
					
					tempoStartTime = Arrays.copyOf(tempoStartTime, len + 1);
					tempoStartTime[len] = getTime(tempoMarker[len]);
					
				//	System.out.println(newtempo);
				//	System.out.println(((double)newtempo/1000000)*4/60);
				}
			}
		}
		
		this.track = new Track[board.length];
		
		this.prime();
	}
	
	public void prime() {
		this.finished = new boolean[board.length];
		this.index = new int[board.length];
		this.loadedTime = new double[board.length];
		this.loadedTick = new long[board.length];
		this.offset = new int[board.length];
		this.startedNote = new long[board.length][];
		for(int i = 0; i < board.length; i++){
			track[i] = trackList[board[i].getType().associatedTrack().ordinal()];
			finished[i] = false;
			index[i] = 0;
			setLoadedTime(i, 0);
			setLoadedTick(i, 0);
			offset[i] = getOffset(board[i].getType());
			startedNote[i] = new long[board[i].getNumLanes()+1];
			while(!finished[i] && getLoadedTime(i) < board[0].getLength() + 0.5)
				parseNext(i);
			makeBeatMarkers(i, 0, getLoadedTick(i)+3);
			System.out.println("Primed "+board[i].getType());
		}
	}
	
	public void refresh(){
		for(int i = 0; i < board.length; i++){
			if(!finished[i] && (getLoadedTime(i) < board[i].getLength() + board[i].
					getGame().
					music.
					getTime() + 0.5)){
				long tick = getLoadedTick(i);
				parseNext(i);
				makeBeatMarkers(i, tick+3, getLoadedTick(i)+3);
			}
		}
	}
	
	private void parseNext(int i){
		
		MidiEvent e;
		try {e = track[i].get(index[i]);}
		catch(Exception f){finished[i] = true; return;}
		
		byte[] m = e.getMessage().getMessage();
		long tick = e.getTick();
		
	/*	for(int j = 0; j < m.length; j++)
			System.out.print(m[j]+" ");
		System.out.println();
	*/
		if(m[0] /*>>> 4 == 9*/ == -112){//Start of note
			int lane = m[1]-offset[i];
			if(lane < startedNote[i].length && lane >= 0){
				startedNote[i][lane] = tick;
		//		System.out.println("Strt "+lane+" "+getTime(tick));
			}
		}
		else if(m[0] /*>>> 4 == 8*/ == -128){//End of note
			int lane = m[1]-offset[i];
			if(lane < startedNote[i].length && lane >= 0){
				board[i].addNote(getTime(startedNote[i][lane]),
								getTime(tick),
								lane,
								getNoteLength(tick-startedNote[i][lane]));
				this.setLoadedTime(i, getTime(startedNote[i][lane]));
				this.setLoadedTick(i, tick);
		//		System.out.println("NEnd "+lane+" "+getTime(tick));
			}
		}
		else if((int)m[0] == 0xF0){//SYSEX messages
			//TODO SYSEX message event handler
		}
		else if((int)m[0] == -1){ //Meta messages
			switch((int)m[1]){
				case 1: { //Text event
					//TODO Text event handler
				}
				case 5: { //Lyric
					//TODO Lyric event handler
				}
				case 47: //End of track 
				//	finished[i] = true;
			}
		}
		else {}
		index[i]++;
	}
	
	private void makeBeatMarkers(int i, long tick0, long tick1){
		tick0 /= res;
		tick1 /= res;
		for(long j = tick0; j < tick1; j++){
			board[i].addBeatMarker((tick0+j)%4 == 0, getTime(j*res));
		}
	}
	
	private int getNoteLength(long ticks){
		if(ticks*4/res <= 1) return 0;
		else if(ticks*2/res <= 1) return 1;
		else return 2;
	}
	
	private int getOffset(BoardTypePS type){
		if(type == BoardTypePS.DRUMS) return 96;
		else return 95;
	}
	
	private double getTime(long tick){
		if(ppqMode){
			int i = 0;
			while(true)
				try{
				if(tick > tempoMarker[i+1]) i++;
				else break;
				} catch(Exception e){ break;}
			return tempoStartTime[i] + 
					(tick - tempoMarker[i]) / 
					ticksPerSecond[i];
		}
		else return tick / ticksPerSecond[0];
	}
	
	private void setLoadedTime(int i, double ter){
		this.loadedTime[i] = ter;
	}
	
	private double getLoadedTime(int i){
		return loadedTime[i];
	}
	
	private void setLoadedTick(int i, long ter){
		this.loadedTick[i] = ter;
	}
	
	private long getLoadedTick(int i){
		return loadedTick[i];
	}
}
