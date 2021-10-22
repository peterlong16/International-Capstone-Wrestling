package game;

import java.awt.*;
import java.util.Arrays;

public abstract class Character {

    int x;
    int y;
    Tile CurTile;
    Color image;
    int MovePoints;
    int MaxMove;
    int Health;
    int MaxHealth;
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
        CurTile.setOccupant(this);
        moves = new Action[3];
        moves[0] = new punch(this);
        moves[1] = new superkick(this);
        moves[2] = new SpineBuster(this);
        movePath = new Tile[MaxMove];
    }

    void changeHealth(int change){this.Health += change;}

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
        CurTile.CanMove = true;
        CurTile.Occupant = null;
        this.CurTile = t;
        CurTile.CanMove = false;
        CurTile.Occupant = this;
    }

    void setTile(Tile t) {
        CurTile.CanMove = true;
        CurTile.Occupant = null;
        this.CurTile = t;
        CurTile.CanMove = false;
        CurTile.Occupant = this;

    }

    void emptyPath(){
        Arrays.fill(movePath, null);
    }

    void resetTurn(){
        MovePoints += MaxMove;
    }

    void printPath(){
        for (Tile tile : movePath) {
            System.out.println(tile);
        }
    }

    void addPath(Tile t, int i){
        movePath[i] = t;

    }

    void move() {
        if (movePath[0] == null) {
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



    }

