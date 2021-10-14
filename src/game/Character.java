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
    String name;
    Tile[] movePath;
    int pathpos = 0;
    boolean moving = false;

    Character(Tile t){
        this.x = t.CenterX;
        this.y = t.CenterY;
        this.CurTile = t;
        CurTile.setOccupant(this);
    }


    void setTile(Tile t, Tile[] path) {
        movePath = path;
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
        MovePoints = MaxMove;
    }

    void printPath(){
        for (Tile tile : movePath) {
            System.out.println(tile);
        }
    }

    void addPath(Tile t, int i){
        movePath[i] = t;

    }

    void move(){
        if(x!=CurTile.CenterX || y!=CurTile.CenterY ){
            if(x==movePath[pathpos].CenterX && y==movePath[pathpos].CenterY ){
                pathpos++;
            }
            else{
                if(x > movePath[pathpos].CenterX){
                    x--;
                }
                if(x < movePath[pathpos].CenterX){
                    x++;
                }
                if(y > movePath[pathpos].CenterY){
                    y--;
                }
                if(y < movePath[pathpos].CenterY){
                    y++;
                }
            }


        }
        else{
            moving = false;
            emptyPath();
            pathpos = 0;
        }
        }



    }

