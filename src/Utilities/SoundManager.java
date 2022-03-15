package Utilities;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    public final static Clip crowd1 = getClip("cheering1");
    public final static Clip crowd2 = getClip("cheering2");
    public final static Clip crowd3 = getClip("cheering3");
    public final static Clip crowd4 = getClip("cheering4");
    public final static Clip boo = getClip("boo");
    public final static Clip strike = getClip("strike");
    public final static Clip bell = getClip("bell");
    public final static Clip slam = getClip("slam");




    private static Clip getClip(String filename){
        Clip clip = null;
        try{
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File("src/Sounds/" + filename + ".wav"));
            clip.open(sample);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }
}
