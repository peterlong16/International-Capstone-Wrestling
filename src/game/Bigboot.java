package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Bigboot extends Action {
    Bigboot(Character c) {
        super(c);
        cost = 2;
        dmg = 2;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "Strike";
        name = "Big Boot";
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[4];
    }

    boolean canCharMove(Tile t) {
        ArrayList<Tile> neighbours = Map.neighbourTiles(targets[0].CurTile);
        for (int i = 0; i < neighbours.size(); i++) {
            if (neighbours.get(i).Occupant() == user) {
                for (int x = 0; x < neighbours.size(); x++){
                    if(neighbours.get(x) == t){
                        return Math.abs(i - x) == 4;
                    }
                }
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
        user.setTile(CharMove);
        user.moving = true;







        if(target.state == 2) {
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state = 0;
        }

        target.changeHealth((dmg + user.strikemod) * -1);

        user.strikemod = 0;


        target = null;
        user.moving = true;


        CharMove = null;

    }
}
