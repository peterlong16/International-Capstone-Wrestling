package game;

import java.awt.image.BufferedImage;

public class Slingblade extends Action {

    Slingblade(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        range = new int[]{1, 2, 3, 4, 5};
        targets = new Character[1];
        mover = false;
        type = "Slam";
        name = "Slingblade";
        desc = "Run through the target, using your momentum to slam them. Must have clear path to target. You move up to 5 spaces in the direction of the target";
        stmdmg = 0;
        hype = 10;
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[2];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
    }

    boolean canHit(Tile t, int distance){
        for(int i: range){
            if(Map.distance(t,user.CurTile) == i) {
                return t.x == user.CurTile.x || t.y == user.CurTile.y;
            };
        }
        return false;
    }

    @Override
    boolean entrymod() {
        return true;
    }

    boolean canCharMove(Tile t){
        return Map.distance(t,user.CurTile) > Map.distance(user.CurTile,targets[0].CurTile)  &&
                Map.distance(t, user.CurTile) < 6 &&
                spaceBetween(t,targets[0].CurTile) &&
                !t.Occupied() &&
                t.canMove() &&
                t.type.equals(user.CurTile.type) &&
                (t.x == user.CurTile.x || t.y == user.CurTile.y);

    }

    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(GetClosest(targets[0].CurTile, user.CurTile),user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Character target = targets[0];
        Tile[] path2 = new Tile[2];
        path2[0] = target.CurTile;
        path2[1] = CharMove;






        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }
        user.setTile(CharMove,path2);
        user.moving = true;

        target.changeHealth((dmg + target.CurTile.SlamEntryModifier)* -1);
        target.changeStam(stmdmg * -1);





        user.changeStam((cost + user.slammod) * -1);
        user.slammod++;
        user.changeHealth(-1);



    }
}
