package game;

public class Kickout extends Action{
    Kickout(Character c) {
        super(c);
        mover = false;
        type = "kickout";
        cost = 0;
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){
        Character pinner = user.CurTile.Pinner;
        pinner.cancelPin(Map.neighbourTiles(user.CurTile), this);
        pinner.changeHealth(0);
        user.state = 1;
    }
}
