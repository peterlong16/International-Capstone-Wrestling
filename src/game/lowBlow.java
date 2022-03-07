package game;

import java.awt.image.BufferedImage;

public class lowBlow extends Action{
    lowBlow(Character c) {
        super(c);
        cost = 1;
        dmg = 8;
        stmdmg = 0;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = false;
        hype = 15;
        type = "illegal";
        desc = "Deliver a blow the belt punch to the target. Cannot be performed in view of the referee.";
        name = "Low Blow";
        sequence = new Boolean[]{true,false,false};
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

    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(targets[0].CurTile,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        user.strikemod++;
        for(Character i:targets){
            if(i.state==2 && i!=Map.ref){
                i.cancelPin(Map.neighbourTiles(i.CurTile), this);
            }
            i.changeHealth(dmg * -1);
        }
        user.changeStam(cost * -1);

    }
}
