package game;

import java.awt.image.BufferedImage;

public class HeadButt extends Action{
    HeadButt(Character c) {
        super(c);
        cost = 1;
        dmg = 4;
        stmdmg = 2;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = false;
        type = "strike";
        name = "Headbutt";
        desc = "Hit the target with your head. Damaging the stamina and health of the enemy. Deals 2 damage to the user.";
        sequence = new Boolean[]{true,false,false};
        img = user.sprites[5];
    }

    @Override
    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(targets[0].CurTile,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Character target = targets[0];
        user.strikemod++;
        user.changeStam(cost * -1);
        user.changeHealth(-2);

        if(target.state == 2) {
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state = 0;
        }

        target.changeHealth(dmg * -1);
        target.changeStam(stmdmg * -1);
        target = null;


    }
}
