package game;

import java.util.Arrays;

public abstract class Action {

    Character user;
    Character[] targets;
    int cost;
    int dmg;
    int stmdmg;
    int[] range;
    boolean mover;
    String type;

    Tile CharMove;
    int CharMovex;
    int CharMovey;

    Tile targetMove;




    Action(Character c){
        this.user = c;
        this.CharMovex = 0;
        this.CharMovey = 0;
    }

    void addTarget(Character c){
        if(c!=null && !c.teamname.equals(user.teamname)) {
            for (int i = 0; i < targets.length; i++) {
                if (targets[i] == null) {
                    if(c.state == 3){
                        targets[i] = c.CurTile.Pinner;
                    }
                    else {
                        targets[i] = c;
                    }
                    break;
                }
            }
        }
    }

    void emptyTargets(){
        Arrays.fill(targets,null);
    }

    void setCharMove(Tile t){
        this.CharMove = t;
    }

    void setTargetMove(Tile t){this.targetMove = t;}

    boolean gotTargets(){
        for(Character i: targets){
            if(i==null){
                return false;
            }
        }
        return true;
    }

    boolean canHit(Tile t, int distance){
        return false;
    }

    boolean canTargetMove(Tile t, int distance){
        return false;
    }

    void Execute(){}

    boolean canAfford(){
        return user.MovePoints >= cost;
    }


    void setUser(Character c){
        this.user = c;
    }


}
