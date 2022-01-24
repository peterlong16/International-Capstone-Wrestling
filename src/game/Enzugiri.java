package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enzugiri extends Action {
    Enzugiri(Character c) {
        super(c);
        cost = 2;
        dmg = 2;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = false;
        type = "Strike";
        name = "Enzugiri";
        sequence = new Boolean[]{true,false,false};
        img = user.sprites[4];
    }

    boolean canHit(Tile t, int distance) {
        ArrayList<Tile> neighbours = Map.neighbourTiles(user.CurTile);
        for(Tile i: neighbours){
            if(i == t){
                return t.Occupied();
            }
        }
        return false;
    }

    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(targets[0].CurTile,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Character target = targets[0];
        user.changeStam(cost * -1);
        if(target.state == 2) {
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state = 0;
        }

        target.changeHealth((dmg + user.strikemod) * -1);
        user.strikemod = 0;
        target.changeStam(stmdmg * -1);

        target = null;
        emptyTargets();

    }
}
