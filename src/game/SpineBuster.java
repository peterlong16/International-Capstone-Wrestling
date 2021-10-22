package game;

public class SpineBuster extends Action{
    SpineBuster(Character c) {
        super(c);
        cost = 3;
        dmg = 3;
        stmdmg = 1;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = true;

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

        return canHit;
    }

    boolean canTargetMove(Tile t, int distance){

        return distance==1;
    }

    boolean gotTargets(){
        for(Character i: targets){
            if(i==null){
                return false;
            }
        }

        return targetMove != null;
    }

    @Override
    void Execute() {
        Character target = targets[0];
        Tile[] path = new Tile[target.MaxMove];
        path[0] = user.CurTile;
        path[1] = targetMove;

        target.changeHealth(dmg * -1);
        target.changeStam(stmdmg * -1);
        target.setTile(targetMove,path);
        target.moving = true;

        user.changeStam(cost * -1);
        emptyTargets();
    }
}
