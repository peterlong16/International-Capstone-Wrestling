package game;

import java.awt.image.BufferedImage;

public class dropKick extends Action{
    dropKick(Character c) {
        super(c);
        cost = 2;
        dmg = 2;
        range = new int[1];
        range[0] = 2;
        targets = new Character[1];
        mover = true;
        type = "Strike";
        name = "DropKick";
        hype = 10;
        desc = "Jump forward 1 space with both legs and kick the target backwards 1 space. Deals 1 damage to the user. Strike combo finisher.";
        sequence = new Boolean[]{true,true,true};
        finisher = true;
        img = user.sprites[5];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
    }

    @Override
    void addTarget(Character c) {
        super.addTarget(c);

    }

    boolean canTargetMove(Tile t, int distance){
        if(targets[0].CurTile.x == user.CurTile.x){
            if(targets[0].CurTile.y > user.CurTile.y){
                return distance == 3 && t.canMove() && t.y == (targets[0].CurTile.y + 1);
            }
            else{
                return distance == 3 && t.canMove() && t.y == (targets[0].CurTile.y - 1);
            }
        }
        else if(targets[0].CurTile.y == user.CurTile.y){
            if(targets[0].CurTile.x > user.CurTile.x){
                return  distance == 3 &&t.canMove() && t.x == (targets[0].CurTile.x + 1);
            }
            else{
                return distance == 3 && t.canMove() && t.x == (targets[0].CurTile.x - 1);
            }
        }
        return false;
    }

    @Override
    boolean canHit(Tile t, int distance) {
        boolean canHit = false;

        for(int i: range){
            if (i == distance && (t.x == user.CurTile.x || t.y == user.CurTile.y) ) {
                canHit = true;
                break;
            }

        }

        return canHit && t.Occupied() && spaceBetween(user.CurTile, t);
    }

    boolean exitmod(){
        return true;
    }

    boolean gotTargets(){
        for(Character i: targets){
            if(i==null){
                return false;
            }
        }
        return true;
    }

    boolean canCharMove(Tile t){

        return Map.distance(t, user.CurTile) == 1 && Map.distance(t, targets[0].CurTile) == 1 && t.canMove();
    }

    @Override
    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(CharMove,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));

        DelayTrigger = CharMove;

        user.changeStam(cost * -1);
        user.changeHealth(-1);
        user.setTile(CharMove);
        user.moving = true;

    }

    void DelayAction(){
        System.out.println(targetMove);
        Character target = targets[0];



        if(target.state == 2) {
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state = 0;
        }

        target.changeHealth((dmg + user.strikemod + SlamExit(targetMove,target.CurTile)) * -1);

        user.strikemod = 0;

        target.setTile(targetMove);

        target.moving = true;

        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }



        DelayTrigger=null;

    }
}
