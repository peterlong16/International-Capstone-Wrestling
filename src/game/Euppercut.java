package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Euppercut extends Action{
    Euppercut(Character c) {
        super(c);
        cost=2;
        dmg=2;
        stmdmg = 2;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        type = "Strike";
        desc = "Deliver an uppercut to the target while swivelling around them, moving you to an adjacent tile";
        name = "Euro Uppercut";
        hype = 6;
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[3];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
    }

    boolean canCharMove(Tile t){
        ArrayList<Tile> neighbours = Map.neighbourTiles(targets[0].CurTile);
        for(Tile x: neighbours){
            if(t == x){
                return t.canMove() && !t.Occupied() &&
                        Map.distance(targets[0].CurTile,t) == 1 &&
                        Map.distance(user.CurTile, t) < 3;
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
        user.strikemod++;


        user.changeStam(cost * -1);
        user.setTile(CharMove);
        user.moving = true;







        if(target.state == 2) {
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state = 0;
        }

        target.changeHealth(dmg * -1);
        Map.impactSounds.change(0);
        Map.impactSounds.play();

        user.moving = true;


    }

}
