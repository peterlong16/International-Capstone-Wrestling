package game;

import java.awt.image.BufferedImage;

public class SBChokeslam extends Action{

    SBChokeslam(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        range = new int[]{1};
        targets = new Character[1];
        mover = true;
        type = "SpringBoard";
        name = "OTR Chokeslam";
        desc = "Grab and slam a target inside the ring from the apron. Targets can be slammed onto the canvas or the outside of the ring.";
        stmdmg = 2;
        hype = 8;
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[2];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
    }

    boolean canHit(Tile t, int distance) {
        return super.canHit(t, distance) && t.type != 5;
    }

    boolean canTargetMove(Tile t, int distance){
        return Map.distance(t,user.CurTile) == 1 &&
                (t.type >= 5 && t.type!=6) &&
                !t.Occupied();
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

    }
}
