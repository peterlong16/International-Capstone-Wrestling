package game;

public class EndTurn extends Action{
    EndTurn(Character c) {
        super(c);
        mover = false;
        type = "misc";
        cost = 0;
        desc = "End your turn";
        name = "End Turn";
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){

    }
}
