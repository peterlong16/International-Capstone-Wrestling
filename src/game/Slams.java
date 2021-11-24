package game;

import static game.Map.CurrentPlayer;
import static game.Map.slams;

public class Slams extends Action{
    Slams(Character c) {
        super(c);
        mover = false;
        type = "menu";
        cost = 0;
        name = "Slams";
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){
        slams = new Button[CurrentPlayer.slams.length];
        for (int i = 0; i < CurrentPlayer.slams.length; i++) {
            slams[i] = new Button(CurrentPlayer.slams[i], 25,120);
        }
        Map.MenuSelect = 2;
    }
}
