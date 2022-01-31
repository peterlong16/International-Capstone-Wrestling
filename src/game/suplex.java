package game;

import java.awt.image.BufferedImage;

public class suplex extends Action{

    suplex(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "Slam";
        name = "Suplex";
        desc = "Pick up a target and slam them towards your back, moving them to the opposite adjacent tile.";
        stmdmg = 0;
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[2];
    }

    boolean canTargetMove(Tile t, int distance){
        if(targets[0].CurTile.x == user.CurTile.x){
            if(targets[0].CurTile.y > user.CurTile.y){
                return distance==1 && t.canMove() && t.y == (user.CurTile.y - 1);
            }
            else{
                return distance==1 && t.canMove() && t.y == (user.CurTile.y + 1);
            }
        }
        else if(targets[0].CurTile.y == user.CurTile.y){
            if(targets[0].CurTile.x > user.CurTile.x){
                return distance==1 && t.canMove() && t.x == (user.CurTile.x - 1);
            }
            else{
                return distance==1 && t.canMove() && t.x == (user.CurTile.x + 1);
            }
        }
        return false;
    }

    boolean gotTargets(){
        for(Character i: targets){
            if(i==null){
                return false;
            }
        }

        return true;
    }

    @Override
    boolean entrymod() {
        return true;
    }

    @Override
    boolean exitmod() {
        return true;
    }

    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(targets[0].CurTile,user.CurTile);
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
