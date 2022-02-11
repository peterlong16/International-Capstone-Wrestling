package game;

import java.awt.image.BufferedImage;

public class Taunt extends Action{
    Taunt(Character c) {
        super(c);
        if(user instanceof Light){
            hype = 25;
        }
        else if(user instanceof Medium){
            hype = 20;
        }
        else if(user instanceof Heavy){
            hype = 15;
        }
        cost = 4;
        targets = new Character[]{this.user};
        dmg = 0;
        name = "Showboat";
        type = "Misc.";
        desc = "Play up to the crowd for a large amount of Hype";
        sequence = new Boolean[]{false, false, false};
        img = user.sprites[user.sprites.length-2];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
    }

    @Override
    boolean canHit(Tile t, int distance) {
        return true;
    }

    @Override
    void Execute() {

        user.atk = this;
        user.attacking = true;
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        user.changeStam(cost * -1);
    }
}
