package game;

import java.awt.image.BufferedImage;

public class SpineBuster extends Action{
    SpineBuster(Character c) {
        super(c);
        cost = 1;
        dmg = 3;
        stmdmg = 1;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "Slam";
        name = "Spinebuster";
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[2];

    }


    boolean canTargetMove(Tile t, int distance){

        return distance==1 && (t.canMove() || t.Occupant() == targets[0]);
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
        targetMove = null;
    }
}
