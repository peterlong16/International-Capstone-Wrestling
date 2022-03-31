package game;

import java.awt.image.BufferedImage;

public class SpineBuster extends Action{
    SpineBuster(Character c) {
        super(c);
        cost = 1;
        dmg = 3;
        stmdmg = 1;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;
        type = "Slam";
        name = "Spinebuster";
        hype = 9;
        desc = "Pick up a target and slam them on an adjacent tile 1 space away";
        sequence = new Boolean[]{true,true,false};
        img = user.sprites[2];
        if(user.teamname.equals("Red")){
            this.hype = this.hype * -1;
        }

    }


    boolean canTargetMove(Tile t, int distance){

        return distance==1 &&
                t.type != 6 &&
                (!t.Occupied() || t.Occupant() == targets[0]);
    }

    boolean gotTargets(){
        for(Character i: targets){
            if(i==null){
                return false;
            }
        }

        return true;
    }

    @Override
    boolean entrymod() {
        return true;
    }

    @Override
    boolean exitmod() {
        return true;
    }

    @Override
    void Execute() {
        user.atk = this;
        user.attacking = true;
        user.orientation = user.FindDir(targetMove,user.CurTile);
        user.orient(user.orientation);
        user.sprite.setImage(user.rotate((BufferedImage) img,user.rot));
        Character target = targets[0];
        Tile[] path = new Tile[target.MaxMove];
        path[0] = user.CurTile;
        path[1] = targetMove;

        if(target.state==2){
            target.cancelPin(Map.neighbourTiles(target.CurTile), this);
            target.state=0;
        }

        target.changeHealth((dmg+ targetMove.SlamEntryModifier+SlamExit(targetMove,target.CurTile)) * -1);
        target.changeStam(stmdmg * -1);
        target.setTile(targetMove,path);


        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }
        target.moving = true;

        user.changeStam((cost + user.slammod) * -1);
        user.slammod++;
        Map.impactSounds.change(1);
        Map.impactSounds.play();

    }
}
