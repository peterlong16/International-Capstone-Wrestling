package game;

import java.awt.image.BufferedImage;

public class rana extends Action{
    rana(Character c) {
        super(c);
        cost = 3;
        dmg = 4;
        range = new int[1];
        range[0] = 2;
        targets = new Character[1];
        mover = true;
        type = "Slam";
        name = "Hurricanrana";
        hype = 10;
        desc = "Use all your body weight to throw the target to your original position and move to a tile adjacent to the targets starting position.";
        stmdmg = 1;
        sequence = new Boolean[]{true,true,true};
        img = user.sprites[2];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }
    }

    boolean canTargetMove(Tile t, int distance){
        return t == user.CurTile;
    }

    @Override
    void addTarget(Character c) {
        super.addTarget(c);

        CharMovex = 0;
        CharMovey = 0;
    }


    boolean canHit(Tile t, int distance) {
        boolean canHit = false;
        boolean canMove = false;

        for(int i: range){
            if (i == distance && (t.x == user.CurTile.x || t.y == user.CurTile.y) ) {
                canHit = true;
                break;
            };

        }



        return canHit && spaceBetween(t, user.CurTile); //&& canMove;
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
        Tile target = targets[0].CurTile;

        return Map.distance(t, target) == 1 && t.canMove();
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
        DelayTrigger = target.CurTile;

        user.setTile(CharMove,path2);






        user.changeStam((cost + user.slammod) * -1);
        user.slammod++;
        user.changeHealth(-1);


    }

    @Override
    boolean entrymod() {
        return true;
    }

    @Override
    boolean exitmod() {
        return true;
    }

    void DelayAction(){
        System.out.println("yes");
        DelayTrigger = null;
        Character target = targets[0];
        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }

        target.changeHealth((dmg + targetMove.SlamEntryModifier + SlamExit(targetMove,target.CurTile)) * -1);
        target.changeStam(stmdmg * -1);
        target.setTile(targetMove);

        target.moving = true;
        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }

    }
}
