package game;

import java.awt.image.BufferedImage;

public class FrogSplash extends Action{
    FrogSplash(Character c) {
        super(c);
        cost = 3;
        dmg = 6;
        range = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        targets = new Character[1];
        mover = false;
        type = "Dive";
        name = "Frog Splash";
        hype = 20;
        desc = "Dive onto the target with all of your body weight.";
        stmdmg = 0;
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[user.sprites.length - 1];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
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
        Tile[] path = {target.CurTile,CharMove};
        DelayTrigger = target.CurTile;

        user.setTile(target.CurTile);
        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }

        user.moving = true;
        user.Dive();

    }

    @Override
    void DelayAction() {
        DelayTrigger = null;
        targets[0].changeHealth((dmg + targets[0].CurTile.SlamEntryModifier) * -1);
        user.setTile(CharMove);
        user.changeHealth((2 + targets[0].CurTile.SlamEntryModifier) * -1);
        Map.impactSounds.change(1);
        Map.impactSounds.play();

    }
}
