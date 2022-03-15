package game;

import java.awt.image.BufferedImage;

public class SBrana extends Action{
    SBrana(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        range = new int[]{3, 4, 5, 6};
        targets = new Character[1];
        mover = true;
        type = "SpringBoard";
        name = "SB Hurricanrana";
        desc = "Springboard from the ropes and perform a hurricanrana on the target, throwing the target up to 2 tiles away.";
        stmdmg = 0;
        hype = 20;
        sequence = new Boolean[]{true,true,true};
        img = user.sprites[2];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
    }

    @Override
    boolean canHit(Tile t, int distance) {
        return super.canHit(t, distance) && (t.x == user.CurTile.x || t.y == user.CurTile.y) ;
    }

    boolean canCharMove(Tile t) {

        for(Tile i: Map.neighbourTiles(targets[0].CurTile)){
            if(t == i){
                return t.canMove() && Map.distance(t,targets[0].CurTile) == 1 && t!=targetMove;
            }
        }
        return false;
    }

    boolean canTargetMove(Tile t, int distance){
        return Map.distance(t,targets[0].CurTile) < 3 &&
                t.canMove();
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
        user.orientation = user.FindDir(Map.GetClosest(targets[0].CurTile, user.CurTile),user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Character target = targets[0];
        Tile[] path2 = new Tile[user.MaxMove];
        path2[0] = target.CurTile;
        path2[1] = CharMove;
        DelayTrigger = target.CurTile;
        user.setTile(CharMove,path2);
        user.moving = true;

        user.changeStam((cost) * -1);
        user.changeHealth(-1);
    }

    void DelayAction(){
        Character target = targets[0];
        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }


        target.changeHealth((dmg + targetMove.SlamEntryModifier + SlamExit(targetMove,target.CurTile)) * -1);
        target.changeStam(stmdmg * -1);
        target.setTile(targetMove);

        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }
        target.moving = true;
        Map.impactSounds.change(1);
        Map.impactSounds.play();
    }


}
