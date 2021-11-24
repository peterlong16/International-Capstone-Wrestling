package game;

public class EndTurn extends Action{
    EndTurn(Character c) {
        super(c);
        mover = false;
        type = "misc";
        cost = 0;
        name = "End Turn";
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){

    }
}
