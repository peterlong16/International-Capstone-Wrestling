package game;

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

    @Override
    boolean canHit(Tile t, int distance) {
        boolean canHit = false;

        for(int i: range){
            if (i == distance && (t.x == user.CurTile.x || t.y == user.CurTile.y) ) {
                canHit = true;
                break;
            };

        }

        return canHit;
    }

    @Override
    void Execute() {

        for(Character i:targets){
            if(i.state == 2) {
                i.cancelPin(Map.neighbourTiles(i.CurTile), this);
                i.state = 0;
            }
            i.changeHealth(dmg * -1);

        }
        user.changeStam(cost * -1);
        user.setTile(CharMove);
        user.moving = true;
        emptyTargets();
        CharMovey = 0;
        CharMovex = 0;
        CharMove = null;
    }
}
