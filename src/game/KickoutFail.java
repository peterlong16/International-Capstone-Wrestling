package game;

public class KickoutFail extends Action {
    KickoutFail(Character c) {
        super(c);
        mover = false;
        type = "kickout";
        cost = 0;
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){}
}
