package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GiantSlap extends Action{
    GiantSlap(Character c) {
        super(c);
        cost=2;
        dmg=2;
        stmdmg = 2;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "Strike";
        hype = 4;
        name = "Giant Slap";
        desc = "Slap a neighbouring target with such force they are moved. Can be moved 1 space to a neighbouring tile.";
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[3];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
    }

    @Override
    boolean canHit(Tile t, int distance) {
        ArrayList<Tile> neighbours = Map.neighbourTiles(user.CurTile);
        for(Tile i: neighbours){
            if(i == t){
                return t.Occupied();
            }
        }
        return false;
    }

    boolean canTargetMove(Tile t, int distance){
        ArrayList<Tile> neighbours = Map.neighbourTiles(user.CurTile);
        for(Tile i: neighbours){
            if(i == t){
                return Map.distance(t,targets[0].CurTile) == 1 &&
                        t.type != 6 && !t.Occupied();
            }
        }
        return false;
    }

    @Override
    boolean exitmod() {
        return true;
    }

    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(targetMove,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Character target = targets[0];
        user.changeStam(cost * -1);
        user.strikemod++;
        if(target.state == 2) {
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state = 0;
        }
        target.changeHealth((dmg + SlamExit(targetMove,target.CurTile)) * -1);
        target.changeStam(stmdmg * -1);
        target.setTile(targetMove);
        target.moving = true;
        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }
        Map.impactSounds.change(0);
        Map.impactSounds.play();



    }
}
