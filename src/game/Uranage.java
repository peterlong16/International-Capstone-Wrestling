package game;

import java.awt.image.BufferedImage;

public class Uranage extends Action{
    Uranage(Character c) {
        super(c);
        cost = 1;
        dmg = 3;
        stmdmg = 0;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "Slam";
        name = "Uranage";
        desc = "Pick up an adjacent target and slam them on an adjacent tile to the target 1 space away";
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[2];

    }


    boolean canTargetMove(Tile t, int distance){

        return Map.distance(t,user.CurTile)==1 && !t.Occupied()  &&
                t.x!=targets[0].CurTile.x &&
                t.y!=targets[0].CurTile.y;
    }

    @Override
    boolean entrymod() {
        return true;
    }

    @Override
    boolean exitmod() {
        return true;
    }

    @Override
    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(targetMove,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Character target = targets[0];
        Tile[] path = new Tile[target.MaxMove];
        path[0] = user.CurTile;
        path[1] = targetMove;

        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }

        target.changeHealth((dmg + targetMove.SlamEntryModifier + SlamExit(targetMove,target.CurTile)) * -1);
        target.changeStam(stmdmg * -1);
        target.setTile(targetMove,path);
        target.orient(target.FindDir(user.CurTile,target.CurTile));


        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }
        target.moving = true;

        user.changeStam((cost + user.slammod) * -1);
        user.slammod++;
        targetMove = null;
    }
}
