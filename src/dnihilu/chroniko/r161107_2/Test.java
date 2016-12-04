package dnihilu.chroniko.r161107_2;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AudioFormat audioFormat = new AudioFormat((float) 48000, 16, 1,
				true, false);
			DataLine.Info datalineInfo = new DataLine.Info(SourceDataLine.class,
				audioFormat, AudioSystem.NOT_SPECIFIED);
		
		javax.sound.sampled.Mixer.Info[] info = AudioSystem.getMixerInfo();
		javax.sound.sampled.Line.Info info2 = datalineInfo;
		for(int i = 0; i < info.length; i++){
			System.out.println(AudioSystem.getMixer(info[i]).isLineSupported(info2));
		}
	}

}
