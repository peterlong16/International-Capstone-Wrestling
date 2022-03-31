package game;

import Utilities.SoundManager;

import javax.sound.sampled.Clip;

public class miscSounds extends Sounds{
    miscSounds(){
        clips = new Clip[]{SoundManager.boo,SoundManager.bell};
        current = clips[0];
        setVolume(0.2f);
    }
}
