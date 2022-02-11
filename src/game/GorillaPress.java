package game;

import java.awt.image.BufferedImage;

public class GorillaPress extends Action {
    GorillaPress(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        stmdmg = 2;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "Signature";
        name = "Gorilla Press";
        desc = "Pickup the target and throw them 2 - 5 tiles away. Requires level 3 Hype.";
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[2];
    }

    boolean canTargetMove(Tile t,int distance){
        return (Map.distance(t,user.CurTile) > 1 && Map.distance(t,user.CurTile) < 6) &&
                (!t.Occupied() || t==targets[0].CurTile) &&
                (t.x == user.CurTile.x || t.y == user.CurTile.y );

    }

    @Override
    boolean canAfford() {
        return user.MovePoints >= cost && user.signature;
    }

    @Override
    boolean exitmod() {
        return true;
    }

    @Override
    boolean entrymod() {
        return true;
    }

    void Execute() {
        user.atk = this;

        Character target = targets[0];
        user.attacking = true;
        user.orientation = user.FindDir(GetClosest(targetMove, user.CurTile),user.CurTile);
        user.orient(user.orientation);
        target.orientation = target.FindDir(user.CurTile,target.CurTile);
        target.orient(target.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));

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

        target.flying = true;
        target.moving = true;


        user.changeStam((cost + user.slammod) * -1);
        user.slammod++;




    }

}
