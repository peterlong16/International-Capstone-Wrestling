package game;


import Utilities.ImageManager;

import java.awt.*;
import java.io.IOException;

import static game.Constants.TILE_SIZE;

public class Tile {
    int x, y;
    int AccX, AccY;
    Integer type;
    Image image;
    String name;
    int CenterX;
    int CenterY;
    int HighX;
    int HighY;
    Character Pinner;
    String accessible;

    public static Image TL_CORNER;
    public static Image TR_CORNER;
    public static Image BL_CORNER;
    public static Image BR_CORNER;
    public static Image GROUND;
    public static Image CROWD;
    public static Image ROPE;
    public static Image CANVAS;

    static {
        try {
            TL_CORNER = ImageManager.loadImage("ringpostTL");
            TR_CORNER = ImageManager.loadImage("ringpostTR");
            BL_CORNER = ImageManager.loadImage("ringpostBL");
            BR_CORNER = ImageManager.loadImage("ringpostBR");
            GROUND = ImageManager.loadImage("ground");
            CROWD = ImageManager.loadImage("crowd");
            ROPE = ImageManager.loadImage("rope");
            CANVAS = ImageManager.loadImage("canvas");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static final Image[] CONTENTS = {
            TL_CORNER,
            TR_CORNER,
            BL_CORNER,
            BR_CORNER,
            GROUND,
            CROWD,
            ROPE,
            CANVAS
    };

    Tile(int x){
        this.type = x;
        this.image = CONTENTS[x-1];
        switch (type) {
            case 5 -> {
                this.name = "Ground";
                accessible = "";
            }
            case 6 -> {
                this.name = "Crowd";
                accessible = "Inaccessible";
            }
            case 7 -> {
                this.name = "Rope";
                accessible = "Can Climb";
            }
            case 8 -> {
                this.name = "Canvas";
                accessible = "";
            }

            default -> {
                this.name = "RingPost";
                accessible = "Can Climb";
            }
        }
    }


    Character Occupant(){
        for(Character i: Map.Characters){
            if(i.CurTile == this && i.state!=2){
                return i;
            }
        }

        return null;
    }

    boolean Occupied(){
        return Occupant()!=null;
    }

    boolean canMove(){
        return !Occupied() && (this.type == 5 || this.type == 8);
    }

    void setCenter(int x, int y){
        this.CenterX = x;
        this.CenterY = y;
    }

    void setBounds(int x, int y,int r, int c){
        this.AccX = x;
        this.AccY = y;
        this.HighX = x + TILE_SIZE;
        this.HighY = y + TILE_SIZE;
        this.y = r;
        this.x = c;
    }


    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }
}
