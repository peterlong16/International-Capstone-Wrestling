package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Action {

    Character user;
    Character[] targets;
    Boolean[] sequence = new Boolean[3];
    boolean finisher;
    // [ needs target, needs move target, needs character move ]
    int cost;
    int dmg;
    int stmdmg;
    int hype;
    int[] range;
    boolean mover;
    String type;
    String name;
    String desc;
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
        int dist = 100;
        Tile closest = dest;
        for(Tile t:Map.neighbourTiles(source)){
            if(Map.distance(t,dest) < dist){
                dist = Map.distance(t,dest);
                closest = t;
            }
        }

        return closest;
    }

    boolean spaceBetween(Tile t,Tile t2){
       Tile cur = GetClosest(t2,t);


       while(cur!=t2){
           System.out.println( "--------------");
           System.out.println( t2 );
           System.out.println( cur);
           System.out.println( "--------------");

           if(cur.canMove() ){
               cur = GetClosest(t2,cur);

           }
           else{
               //System.out.println(cur);
               return false;
           }
       }

       return true;
    }

    void addTarget(Tile t) {
        targetTile = t;
    }

    boolean entrymod()
    {
        return false;
    }

    boolean exitmod(){
        return false;
    }

    void emptyTargets(){
        Arrays.fill(targets,null);
        CharMove = null;
        targetMove = null;
    }

    int SlamExit(Tile dest, Tile start){
        switch(start.type){
            case 1,2,3,4,5 ->{
                return start.SlamExitModifier;
            }
            case 7 ->{
                if(dest.type == 5){
                    return start.SlamExitModifier;
                }
                else{return 0;}
            }
            case 8->{
                if(!start.type.equals(dest.type)){
                    return start.SlamExitModifier;
                }
                else {return 0;}
            }

        }

        return 0;
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
        if(this.type.equals("Slam")){
            return user.MovePoints >= cost + user.slammod;
        }
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
