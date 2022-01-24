package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Powerbomb extends Action{
    Powerbomb(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        stmdmg = 2;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "slam";
        name = "Powerbomb";
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[2];
    }

    boolean canTargetMove(Tile t,int distance){
        return (Map.distance(t,targets[0].CurTile) == 0 || Map.distance(t,targets[0].CurTile) == 1) &&
                (!t.Occupied() || t==targets[0].CurTile) &&
                (t.x == user.CurTile.x || t.y == user.CurTile.y );

    }

    void Execute() {
        user.atk = this;

        Character target = targets[0];
        user.attacking = true;
        user.orientation = user.FindDir(targets[0].CurTile,user.CurTile);
        user.orient(user.orientation);
        target.orientation = target.FindDir(user.CurTile,target.CurTile);
        target.orient(target.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));

        Tile[] path = new Tile[target.MaxMove];
        path[0] = user.CurTile;
        path[1] = targetMove;

        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }

        target.changeHealth(dmg * -1);
        target.changeStam(stmdmg * -1);
        target.setTile(targetMove,path);


        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }
        target.moving = true;

        user.changeStam((cost + user.slammod) * -1);
        user.slammod++;
        emptyTargets();
        targetMove = null;

    }
}
