package dnihilu.chroniko.current;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

public class MusicSystem {
	
	Chroniko game;
	double time = 0;
//	private boolean paused;
	/*
	String track[] = new String[13];
	{
	track[0] = "guitar.ogg";
	track[1] = "drums_1.ogg";
	track[2] = "drums_2.ogg";
	track[3] = "drums_3.ogg";
	track[4] = "drums_4.ogg";
	track[5] = "drums.ogg";
	track[6] = "vocals_1.ogg";
	track[7] = "vocals_2.ogg";
	track[8] = "vocals.ogg";
	track[9] = "rhythm.ogg";
	track[10] = "keys.ogg";
	track[11] = "crowd.ogg";
	track[12] = "song.ogg";
	}*/
	File track[];
	
	OggDecoder ogg[] = new OggDecoder[0];
	private double startTime = -0.2;
	
	public MusicSystem(Chroniko game, File track[]){
		this.game = game;
		this.track = track;
		
		this.constructTracks();
		this.setTime(System.nanoTime());
		for(int i = 0; i < ogg.length; i++){
			ogg[i].initialize();
		}
		startTime = this.getTime();
		for(int i = 0; i < ogg.length; i++){
			ogg[i].start();
		}
	}
	
	private void constructTracks(){
		Vector<OggDecoder> ogg = new Vector<OggDecoder>();
		for(int i = 0; i < track.length; i++){
			try{ogg.add(new OggDecoder("file://" + track[i], this));}
			catch(FileNotFoundException f){}
			catch(Exception e){System.err.println(e);}
		}
		this.ogg = ogg.toArray(this.ogg);
	}
	
	public double getTime(){
		return time;
	}
	
	public void setTime(long time){
		this.time = time/(double)1000000000 - startTime;
	}
	
	public void setTimeOffset(double seconds, boolean plusminus){
		if(plusminus) startTime += seconds;
		else startTime = seconds;
	}
}
