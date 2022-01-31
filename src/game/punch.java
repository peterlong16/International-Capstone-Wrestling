package game;

import java.awt.image.BufferedImage;

public class punch extends Action{
    punch(Character c) {
        super(c);
        cost = 1;
        dmg = 2;
        stmdmg = 0;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = false;
        type = "strike";
        desc = "Smash the target with your forearm";
        name = "Forearm Smash";
        sequence = new Boolean[]{true,false,false};
        img = user.sprites[3];
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

        return canHit;
    }

    @Override
    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(targets[0].CurTile,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        user.strikemod++;
        for(Character i:targets){
            if(i.state==2){
                i.cancelPin(Map.neighbourTiles(i.CurTile), this);
            }
            i.changeHealth(dmg * -1);
        }
        user.changeStam(cost * -1);

    }
}
