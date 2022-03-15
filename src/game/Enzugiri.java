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
        hype = 11;
        desc = "Jump and kick the target. Strike combo finisher.";
        name = "Enzugiri";
        finisher = true;
        sequence = new Boolean[]{true,false,false};
        img = user.sprites[4];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
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
        Map.impactSounds.change(0);
        Map.impactSounds.play();

        target.changeHealth((dmg + user.strikemod) * -1);
        user.strikemod = 0;
        target.changeStam(stmdmg * -1);

    }
}
