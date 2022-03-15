package game;

import Utilities.SoundManager;

import javax.sound.sampled.Clip;


public class ambientCrowd extends Sounds{
    ambientCrowd(){
        clips = new Clip[]{SoundManager.crowd1, SoundManager.crowd2, SoundManager.crowd3,SoundManager.crowd4};
        current = clips[0];
    }

    @Override
    public void play() {
        current.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
