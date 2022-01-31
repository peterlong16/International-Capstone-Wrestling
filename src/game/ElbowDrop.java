package game;

import java.awt.image.BufferedImage;

public class ElbowDrop extends Action{
    ElbowDrop(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        range = new int[]{1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,13,14};
        targets = new Character[1];
        mover = false;
        type = "Dive";
        name = "Elbow Drop";
        desc = "Drive your elbow into the target from the turnbuckle";
        stmdmg = 0;
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[user.sprites.length - 1];
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

    @Override
    boolean entrymod() {
        return true;
    }

    void Execute(){
        user.atk = this;
        Character target = targets[0];
        user.attacking = true;
        user.orientation = user.FindDir(target.CurTile, CharMove);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Tile[] path = {target.CurTile,CharMove};

        DelayTrigger = target.CurTile;

        user.setTile(CharMove, path );
        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }

        user.moving = true;
        user.Dive();
        target.changeHealth((dmg + target.CurTile.SlamEntryModifier) * -1);
    }

    @Override
    void DelayAction() {
        user.changeHealth((2 + targets[0].CurTile.SlamEntryModifier) * -1);
    }
}
