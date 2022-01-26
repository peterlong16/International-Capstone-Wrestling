package game;

import static game.Map.CurrentPlayer;

public class Strikes extends Action{
    Strikes(Character c) {
        super(c);
        mover = false;
        type = "menu";
        cost = 0;
        name = "Strikes";
        desc = "Display a list of all strikes. Using multiple strikes in the same turn will increase the damage of your Strike combo finisher.";
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){
        user.atk = this;
        Map.strikes = new Button[CurrentPlayer.strikes.length];
        for (int i = 0; i < CurrentPlayer.strikes.length; i++) {
            Map.strikes[i] = new Button(CurrentPlayer.strikes[i], 25,130);
        }
        Map.MenuSelect = 1;
    }
}
