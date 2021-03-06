package game;

import java.awt.image.BufferedImage;

public class SBforearm extends Action {
    SBforearm(Character c) {
        super(c);
        cost = 3;
        dmg = 6;
        range = new int[]{3, 4, 5};
        targets = new Character[1];
        mover = false;
        type = "SpringBoard";
        name = "SB Forearm";
        desc = "Springboard from the ropes and deliver a flying forearm smash";
        stmdmg = 0;
        hype = 20;
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[3];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
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
        DelayTrigger = user.CurTile;
        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }

        user.moving = true;
        target.changeHealth((dmg + target.CurTile.SlamEntryModifier) * -1);
    }

    @Override
    boolean entrymod() {
        return true;
    }

    @Override
    void DelayAction() {
        DelayTrigger = null;
        System.out.println(user.atk);
        user.changeHealth((1 + targets[0].CurTile.SlamEntryModifier) * -1);
        Map.impactSounds.change(1);
        Map.impactSounds.play();
    }
}
