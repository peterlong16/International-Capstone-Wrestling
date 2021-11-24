package game;

public class punch extends Action{
    punch(Character c) {
        super(c);
        cost = 1;
        dmg = 6;
        stmdmg = 0;
        range = new int[1];
        range[0] = 1;
        targets = new Character[1];
        mover = false;
        type = "strike";
        name = "Forearm Smash";
        sequence = new Boolean[]{true,false,false};
    }

    @Override
    boolean canHit(Tile t, int distance) {
        boolean canHit = false;

        for(int i: range){
            if (i == distance) {
                canHit = true;
                break;
            };

        }

        return canHit;
    }

    @Override
    void Execute() {
        for(Character i:targets){
            if(i.state==2){
                i.cancelPin(Map.neighbourTiles(i.CurTile), this);
            }
            i.changeHealth(dmg * -1);
        }
        user.changeStam(cost * -1);
        emptyTargets();
    }
}
