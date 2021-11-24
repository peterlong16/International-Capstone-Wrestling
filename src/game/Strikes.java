package game;

import static game.Map.CurrentPlayer;

public class Strikes extends Action{
    Strikes(Character c) {
        super(c);
        mover = false;
        type = "menu";
        cost = 0;
        name = "Strikes";
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){
        Map.strikes = new Button[CurrentPlayer.strikes.length];
        for (int i = 0; i < CurrentPlayer.strikes.length; i++) {
            Map.strikes[i] = new Button(CurrentPlayer.strikes[i], 25,130);
        }
        Map.MenuSelect = 1;
    }
}