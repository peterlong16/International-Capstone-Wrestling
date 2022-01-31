package game;

import java.awt.image.BufferedImage;

public class Pin extends Action{

    Pin(Character c) {
        super(c);
        cost = 0;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = false;
        type = "pin";
        name = "Pin";
        desc = "Attempt to Pin the target, match can be won on your next turn";
        sequence = new Boolean[]{true,false,false};
        img = user.sprites[user.sprites.length-1];
    }

    boolean canHit(Tile t, int distance) {
        return range[0] == distance && t.Occupied() && t.Occupant().state == 1 && !t.Occupant().teamname.equals(user.teamname) &&
                t.type == 8;
    }

    void Execute(){
        user.atk = this;
        user.pinning = true;
        user.orientation = user.FindDir(targets[0].CurTile,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        targets[0].state = 3;
        user.state = 2;
        user.setPin(targets[0].CurTile);
    }
}
