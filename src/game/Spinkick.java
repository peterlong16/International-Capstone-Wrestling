package game;

import java.awt.image.BufferedImage;

public class Spinkick extends Action{
    Spinkick(Character c) {
        super(c);
        cost = 2;
        dmg = 2;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "Strike";
        name = "Spin Kick";
        desc = "Deliver a spinning kick which pushes a target backwards 1 tile";
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[4];
    }

    boolean canTargetMove(Tile t, int distance){
        return Map.distance(t,targets[0].CurTile) == 1 &&
                t!=user.CurTile &&
                (t.x == user.CurTile.x || t.y == user.CurTile.y);
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
        if(target.state == 2) {
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state = 0;
        }
        target.changeHealth(dmg * -1);
        target.changeStam(stmdmg * -1);
        if(targetMove.Occupied()){
            targetMove.Occupant().changeHealth(-2);
        }
        else{
            target.setTile(targetMove);
            target.moving = true;
        }

        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }
        target = null;


    }
}
