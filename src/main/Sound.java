package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[100];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/Amogus.wav");
		soundURL[1] = getClass().getResource("/sound/Gunshot.wav");
		soundURL[2] = getClass().getResource("/sound/MonsterGunshot.wav");
		soundURL[3] = getClass().getResource("/sound/Oof.wav");
		soundURL[4] = getClass().getResource("/sound/StartGame.wav");
		soundURL[5] = getClass().getResource("/sound/Select.wav");
		soundURL[6] = getClass().getResource("/sound/Death.wav");
		soundURL[7] = getClass().getResource("/sound/Burn.wav");
		soundURL[8] = getClass().getResource("/sound/IceBullet.wav");
	}
	
	public void setFile(int i) {
		
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
			
		}
		
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(gainControl.getValue()-10.0f);

	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}

	public void volumeUp() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(gainControl.getValue()+5.0f);
	}
	
	public void volumeDown() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(gainControl.getValue()-5.0f);
	}
	
}
