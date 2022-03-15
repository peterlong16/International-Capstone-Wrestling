package game;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sounds {
    Clip[] clips;
    Clip current;

    Sounds() {}

    public void play(){
        current.start();
    }

    public void change(int i){
        current.stop();
        current.setMicrosecondPosition(0);
        current.setFramePosition(0);
        current = clips[i];
    }


    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        for(Clip c:clips) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
    }

}
