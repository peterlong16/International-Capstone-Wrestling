package game;

import java.awt.image.BufferedImage;

public class superkick extends Action {
    superkick(Character c) {
        super(c);
        cost = 2;
        dmg = 2;
        range = new int[1];
        range[0] = 2;
        targets = new Character[1];
        mover = false;
        type = "Strike";
        name = "Superkick";
        hype = 10;
        desc = "Take a stride forwards and deliver a big kick to a target";
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[4];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
    }

    @Override
    void addTarget(Character c) {
        super.addTarget(c);


    }

    @Override
    boolean canCharMove(Tile t) {
        return !t.Occupied() && t.canMove() && Map.distance(t,user.CurTile) == 1 && Map.distance(t,targets[0].CurTile) == 1;
    }

    @Override
    boolean canHit(Tile t, int distance) {
        boolean canHit = false;

        for(int i: range){
            if (i == distance && (t.x == user.CurTile.x || t.y == user.CurTile.y) ) {
                canHit = true;
                break;
            };

        }

        return canHit && spaceBetween(t, user.CurTile);
    }

    @Override
    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(CharMove,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));


        user.strikemod++;
        user.changeStam(cost * -1);
        user.setTile(CharMove);
        for(Character i:targets){
            if(i.state == 2) {
                i.cancelPin(Map.neighbourTiles(i.CurTile), this);
                i.state = 0;
            }
            i.changeHealth(dmg * -1);

        }
        user.moving = true;
        Map.impactSounds.change(0);
        Map.impactSounds.play();

    }
}
