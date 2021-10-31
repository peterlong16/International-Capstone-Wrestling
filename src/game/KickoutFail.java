package game;

public class KickoutFail extends Action {
    KickoutFail(Character c) {
        super(c);
        mover = false;
        type = "misc";
        cost = 0;
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){}
}
