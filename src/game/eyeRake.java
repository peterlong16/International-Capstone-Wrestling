package game;

import java.awt.image.BufferedImage;

public class eyeRake extends Action {
    eyeRake(Character c){
        super(c);
        cost = 2;
        dmg = 5;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "illegal";
        name = "Eye Rake";
        hype = 10;
        desc = "Scratch the targets eyes, sending them recoiling backward 1 space. Cannot be performed in view of the referee.";
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[3];
        if(user.teamname.equals("Blue")){
            this.hype = this.hype * -1;
        }
    }

    @Override
    boolean canHit(Tile t, int distance) {
        boolean canHit = false;

        for(int i: range){
            if (i == distance) {
                canHit = true;
                break;
            };

        }

        if(Map.ref.inView(user.CurTile) || Map.ref.inView(t)){
            canHit = false;
        }


        return canHit;
    }

    boolean canTargetMove(Tile t, int distance){
        return Map.distance(t,targets[0].CurTile) == 1 &&
                t!=user.CurTile &&
                (t.x == user.CurTile.x || t.y == user.CurTile.y);
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
        user.strikemod++;
        user.changeStam(cost * -1);
        if(target.state == 2) {
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state = 0;
        }
        target.changeHealth((dmg +SlamExit(targetMove,target.CurTile)) * -1);
        target.changeStam(stmdmg * -1);
        if(targetMove.Occupied()){
            targetMove.Occupant().changeHealth(-2);
        }
        else{
            target.setTile(targetMove);
            target.moving = true;
        }

        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }



    }


}
