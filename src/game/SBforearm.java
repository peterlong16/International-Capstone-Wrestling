package game;

import java.awt.image.BufferedImage;

public class SBforearm extends Action {
    SBforearm(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        range = new int[]{3, 4, 5};
        targets = new Character[1];
        mover = false;
        type = "SpringBoard";
        name = "SB Forearm";
        stmdmg = 0;
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[3];
    }

    @Override
    boolean canCharMove(Tile t) {

        for(Tile i: Map.neighbourTiles(targets[0].CurTile)){
            if(t == i){
                return t.canMove() && Map.distance(t,user.CurTile) == Map.distance(targets[0].CurTile,user.CurTile) - 1;
            }
        }
        return false;
    }

    @Override
    boolean canHit(Tile t, int distance) {
        return super.canHit(t, distance) && (t.x == user.CurTile.x || t.y == user.CurTile.y) ;
    }

    void Execute(){
        user.atk = this;
        Character target = targets[0];
        user.attacking = true;
        user.orientation = user.FindDir(target.CurTile, CharMove);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Tile[] path = {target.CurTile,CharMove};

        user.setTile(CharMove, path );
        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }

        user.moving = true;
        target.changeHealth(dmg * -1);
        emptyTargets();
    }
}
