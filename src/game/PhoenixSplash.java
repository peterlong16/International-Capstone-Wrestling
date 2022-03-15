package game;

import java.awt.image.BufferedImage;

public class PhoenixSplash extends Action {
    PhoenixSplash(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        range = new int[]{3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        targets = new Character[1];
        mover = false;
        type = "Signature";
        name = "Phoenix Splash";
        desc = "Somersault into the air and land on your target. Requires level 3 Hype.";
        stmdmg = 0;
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[user.sprites.length - 1];
    }

    @Override
    boolean canAfford() {
        return user.MovePoints>=cost && user.signature;
    }

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
        DelayTrigger = target.CurTile;

        user.setTile(target.CurTile);
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
        DelayTrigger = null;
        user.setTile(CharMove);
        user.changeHealth((2 + targets[0].CurTile.SlamEntryModifier) * -1);
        Map.impactSounds.change(1);
        Map.impactSounds.play();

    }
}