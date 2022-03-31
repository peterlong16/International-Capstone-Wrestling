package game;

import Utilities.SoundManager;

import javax.sound.sampled.Clip;

public class Impacts extends Sounds {
    Impacts(){
        clips = new Clip[]{SoundManager.strike,SoundManager.slam};
        current = clips[0];
        setVolume(0.2f);
    }
}
