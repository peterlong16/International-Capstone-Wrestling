package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Character {

    int x;
    int y;
    Tile CurTile;
    Color image;
    int MovePoints;
    int MaxMove;
    int Health;
    int MaxHealth;
    int painThresh;
    int regen;
    int stamregen;
    int state;

    // 0 = neutral , 1 = down 2 = pinning 3 = pinned
    public final String[] states = {
            "Neutral",
            "Down",
            "Pinning",
            "Pinned"

    };


    String name;
    Tile[] movePath;
    int pathpos = 0;
    boolean moving = false;
    boolean[] healthBar;
    boolean[] staminaBar;
    Action[] moves;

    Character(Tile t){
        this.x = t.CenterX;
        this.y = t.CenterY;
        this.CurTile = t;
        moves = new Action[3];
        moves[0] = new punch(this);
        moves[1] = new superkick(this);
        moves[2] = new SpineBuster(this);
        movePath = new Tile[MaxMove];
        state = 0;

    }

    void changeHealth(int change){this.Health += change;

        if(Health<=0){
            Health = 0;
        }

        if(Health<=painThresh){
            if(state != 3){
                state = 1;
            }
        }
        else if(state != 2 && state !=3){
            state = 0;
        }
    }

    void changeStam(int change){this.MovePoints += change;}

    void printBar(){
        for(boolean i:this.healthBar){
            System.out.println(i);
        }
    }

    void updateSBar(){
        int sp = MovePoints;

        for(int i = 0;i<staminaBar.length;i++){
            staminaBar[i] = sp > 0;
            sp--;
        }

    }

    void updateHBar(){
        int hp = Health;

        for(int i = 0;i<healthBar.length;i++){
            healthBar[i] = hp > 0;
            hp--;
        }

    }

    void setTile(Tile t, Tile[] path) {
        movePath = path;
        this.CurTile = t;

    }

    void setPin(Tile t){
        this.CurTile = t;
        CurTile.Pinner = this;
        moving = true;
        state = 2;
    }

    void cancelPin(ArrayList<Tile> adj, Action a){
        Tile[] vacant = new Tile[adj.size()];
        int i = 0;


        CurTile.Occupant().state = 0;
        CurTile.Occupant().changeHealth(0);
        CurTile.Pinner = null;

        if(!a.mover) {

            for (Tile t : adj) {
                if (t.canMove()) {
                    vacant[i] = t;
                    i++;
                }
            }


            int randomNum = ThreadLocalRandom.current().nextInt(0, i);
            this.CurTile = vacant[randomNum];
            this.moving = true;
        }
        this.state = 0;

        Map.pinCount = 0;

    }

    void setTile(Tile t) {
        this.CurTile = t;
    }

    void emptyPath(){
        Arrays.fill(movePath, null);
    }

    void resetTurn(){
        if(this.MovePoints < 0){
            this.MovePoints = 0;
        }

        changeStam(stamregen);
        if(this.MovePoints > MaxMove){
            this.MovePoints = MaxMove;
        }
        changeHealth(regen);
        if(this.Health > MaxHealth){
            this.Health = MaxHealth;
        }
    }

    void printPath(){
        for (Tile tile : movePath) {
            System.out.println(tile);
        }
    }

    void addPath(Tile t, int i){
        movePath[i] = t;

    }


    void Update(){
        updateHBar();
        updateSBar();
        if(moving){
            move();
        }
    }

    void move() {
        if ( movePath.length == 0 || movePath==null || movePath[0] == null) {
            if (x != CurTile.CenterX || y != CurTile.CenterY) {
                if (x > CurTile.CenterX){
                    x--;
                }
                if (x < CurTile.CenterX) {
                    x++;
                }
                if (y > CurTile.CenterY) {
                    y--;
                }
                if (y < CurTile.CenterY) {
                    y++;
                }
            }
        } else {
            if (x != CurTile.CenterX || y != CurTile.CenterY) {

                if (x == movePath[pathpos].CenterX && y == movePath[pathpos].CenterY) {
                    pathpos++;
                } else {
                    if (x > movePath[pathpos].CenterX) {
                        x--;
                    }
                    if (x < movePath[pathpos].CenterX) {
                        x++;
                    }
                    if (y > movePath[pathpos].CenterY) {
                        y--;
                    }
                    if (y < movePath[pathpos].CenterY) {
                        y++;
                    }
                }


            } else {
                moving = false;
                emptyPath();
                pathpos = 0;
            }
        }
    }

    @Override
    public String toString() {
        return "Character{" +
                "CurTile=" + CurTile +
                ", MovePoints=" + MovePoints +
                ", Health=" + Health +
                ", state=" + state +
                ", name='" + name + '\'' +
                '}';
    }
}

