package game;

public class Mute extends Action{
    Mute(Character c) {
        super(c);
        type = "menu";
        name = "Mute Audio";
    }

    @Override
    void Execute() {
        if(Map.muted){
            Map.UnMuteAudio();
            this.name = "Mute Audio";
        }
        else{
            Map.MuteAudio();
            this.name = "Unmute Audio";
        }
    }
}
