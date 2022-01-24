package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Action {

    Character user;
    Character[] targets;
    Boolean[] sequence = new Boolean[3];
    // [ needs target, needs move target, needs character move ]
    int cost;
    int dmg;
    int stmdmg;
    int[] range;
    boolean mover;
    String type;
    String name;
    int CharMoveRange;
    Image img;

    Tile CharMove;
    Tile targetTile;
    Tile DelayTrigger;
    int CharMovex;
    int CharMovey;

    Tile targetMove;




    Action(Character c){
        this.user = c;
        this.CharMovex = 0;
        this.CharMovey = 0;
    }



    void addTarget(Character c){
        if(c!=null) {
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

    Tile GetClosest(Tile dest,Tile source){
        //returns closest tile to source in direction of dest
        int dist = Map.distance(dest,source);
        int x = 0;
        int y = 0;

        if(dest.x==source.x){
            x = dest.x;
            if(dest.y<source.y){
                y = dest.y + (dist - 1);
            }
            else{
                y = dest.y - (dist - 1);
            }
        }
        else if(dest.y==source.y){
            y = dest.y;
            if(dest.x<source.x){
                x = dest.x + (dist - 1);
            }
            else{
                x = dest.x - (dist - 1);
            }

        }
        if(x == 0 && y == 0){
            return null;
        }

        return Map.TileGrid[y][x];
    }

    boolean spaceBetween(Tile t,Tile t2){
        int dist = Map.distance(t,t2);

        if(t.x==t2.x){
            if(t.y<t2.y){
                dist--;
                while(dist>0){
                    if(Map.TileGrid[t.y + dist][t.x].Occupied()){
                        return false;
                    }
                    dist--;
                }
            }
            else{
                dist = dist * -1;
                dist++;
                while(dist<0){
                    if(Map.TileGrid[t.y + dist][t.x].Occupied()){
                        return false;
                    }
                    dist++;
                }
            }
            return true;
        }
        else if(t.y==t2.y){
            if(t.x<t2.x){
                dist--;
                while(dist>0){
                    if(Map.TileGrid[t.y][t.x + dist].Occupied()){
                        return false;
                    }
                    dist--;
                }
            }
            else{
                dist = dist * -1;
                dist++;
                while(dist<0){
                    if(Map.TileGrid[t.y][t.x + dist].Occupied()){
                        return false;
                    }
                    dist++;
                }
            }
            return true;

        }
        return false;
    }

    void addTarget(Tile t) {
        targetTile = t;
    }

    void emptyTargets(){
        Arrays.fill(targets,null);
        CharMove = null;
        targetMove = null;
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

    boolean canCharMove(Tile t){
        return true;
    }

    boolean canHit(Tile t, int distance){
        boolean canHit = false;

        for(int i: range){
            if (i == distance ) {
                canHit = true;
                break;
            };
        }

        return canHit && t.Occupied();
    }

    boolean canTargetMove(Tile t, int distance){
        return false;
    }

    void Execute(){
    }

    boolean canAfford(){
        return user.MovePoints >= cost;
    }

    void DelayAction(){}


    void setUser(Character c){
        this.user = c;
    }

    public String toString(){
        return "(" + "name= " + this.name + " user= " + this.user.name +  ")";
    }


}
