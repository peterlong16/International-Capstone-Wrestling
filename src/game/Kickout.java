package game;

public class Kickout extends Action{
    Kickout(Character c) {
        super(c);
        mover = false;
        name = "pass";
        type = "kickout";
        cost = 0;
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){
        user.atk = this;
        Character pinner = user.CurTile.Pinner;
        pinner.cancelPin(Map.neighbourTiles(user.CurTile), this);
        pinner.changeHealth(0);
        user.state = 1;
    }
}
