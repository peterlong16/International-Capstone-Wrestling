package game;

public class Pin extends Action{

    Pin(Character c) {
        super(c);
        cost = 0;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = false;
        type = "pin";
    }

    boolean canHit(Tile t, int distance) {
        return range[0] == distance && t.Occupied() && t.Occupant().state == 1;
    }

    void Execute(){
        targets[0].state = 3;
        user.state = 2;
        user.setPin(targets[0].CurTile);
    }
}
