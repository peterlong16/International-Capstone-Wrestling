package game;

import java.awt.image.BufferedImage;

public class KneeStrike extends Action{
    KneeStrike(Character c) {
        super(c);
        cost = 2;
        dmg = 2;
        range = new int[]{3, 4, 5};
        targets = new Character[1];
        mover = true;
        type = "Signature";
        desc = "Run toward the target and deliver a powerful knee strike. Must have clear path to target. Requires level 3 Hype. ";
        name = "Trigger Knee";
        finisher = true;
        sequence = new Boolean[]{true,false,true};
        img = user.sprites[4];
    }

    boolean canCharMove(Tile t) {
        for(Tile i: Map.neighbourTiles(targets[0].CurTile)){
            if(t == i){
                return t.canMove();
            }
        }
        return false;
    }


    @Override
    boolean canHit(Tile t, int distance) {
        boolean canHit = false;

        for(int i: range){
            if (i == distance ) {
                canHit = true;
                break;
            };
        }

        return canHit && t.Occupied() && spaceBetween(user.CurTile,t);
    }

     @Override
    boolean canAfford() {
        return user.MovePoints>=cost && user.signature;
    }

    @Override
    void Execute() {
        user.atk = this;
        user.attacking = true;
        System.out.println(Map.GetClosest(targets[0].CurTile,user.CurTile));
        user.orientation = user.FindDir(Map.GetClosest(targets[0].CurTile,user.CurTile), user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Character target = targets[0];
        user.changeStam(cost * -1);
        Tile[] path = new Tile[]{targets[0].CurTile,CharMove};

        if(target.state == 2) {
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state = 0;
        }
        target.changeHealth((dmg * -1));


        user.setTile(CharMove,path);
        user.Dive();


    }
}
