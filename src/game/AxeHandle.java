package game;

import java.awt.image.BufferedImage;

public class AxeHandle extends Action{
    AxeHandle(Character c) {
        super(c);
        cost = 3;
        dmg = 6;
        range = new int[]{1, 2, 3, 4, 5, 6};
        targets = new Character[1];
        mover = false;
        type = "Dive";
        name = "Axe Handle";
        desc = "Jump from the turnbuckle and deliver a large blow with both hands";
        stmdmg = 0;
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[3];
    }

    @Override
    boolean canCharMove(Tile t) {
        for(Tile i: Map.neighbourTiles(targets[0].CurTile)){
            if(t == i){
                return t.canMove();
            }
        }
        return false;
    }

    void Execute(){
        user.atk = this;
        Character target = targets[0];
        user.attacking = true;
        user.orientation = user.FindDir(target.CurTile, CharMove);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));


        user.setTile(CharMove);
        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }

        user.moving = true;
        user.Dive();
        target.changeHealth(dmg * -1);
        emptyTargets();
    }
}
