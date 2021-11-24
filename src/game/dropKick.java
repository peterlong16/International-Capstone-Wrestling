package game;

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
        sequence = new Boolean[]{true,true,false};
    }

    @Override
    void addTarget(Character c) {
        super.addTarget(c);

        if(c!=null) {

            if (c.CurTile.x > user.CurTile.x) {
                this.CharMovex = 1;
            } else if (c.CurTile.x < user.CurTile.x) {
                this.CharMovex = -1;
            } else if (c.CurTile.y > user.CurTile.y) {
                this.CharMovey = 1;
            } else if (c.CurTile.y < user.CurTile.y) {
                this.CharMovey = -1;
            }
        }
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

    boolean gotTargets(){
        for(Character i: targets){
            if(i==null){
                return false;
            }
        }
        return true;
    }

    @Override
    void Execute() {
        Character target = targets[0];
        Tile[] path = new Tile[target.MaxMove];
        path[0] = targetMove;

        user.changeStam(cost * -1);
        user.changeHealth(-1);
        user.setTile(CharMove);
        user.moving = true;
        emptyTargets();





            if(target.state == 2) {
                target.cancelPin(Map.neighbourTiles(target.CurTile), this);
                target.state = 0;
            }

        target.changeHealth(dmg * -1);
        target.setTile(targetMove,path);
        target.moving = true;

        if(target.x > user.x){
            target.x--;
        }
        else{
            target.x++;
        }



        user.setTile(CharMove);
        target = null;
        targetMove = null;
        user.moving = true;
        emptyTargets();
        CharMovey = 0;
        CharMovex = 0;
        CharMove = null;
    }
}
