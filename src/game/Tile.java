package game;


import Utilities.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static game.Constants.TILE_SIZE;

public class Tile implements Comparable<Tile> {
    int x, y;
    int AccX, AccY;
    Integer type;
    Image image;
    String name;
    int CenterX;
    int CenterY;
    BufferedImage rope;
    int HighX;
    int HighY;
    int SlamEntryModifier;
    int SlamExitModifier;
    Character Pinner;
    String accessible;
    Path path;

    int cost;
    Tile parent;
    int heuristic;
    int depth;

    public static Image TL_CORNER;
    public static Image TR_CORNER;
    public static Image BL_CORNER;
    public static Image BR_CORNER;
    public static Image GROUND;
    public static Image CROWD;
    public static Image ROPE;
    public static Image ROPEFG;
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
            ROPEFG = ImageManager.loadImage("ropefg");
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
                this.SlamEntryModifier = 2;
                this.SlamExitModifier = 0;
            }
            case 6 -> {
                this.name = "Crowd";
                accessible = "Inaccessible";

            }
            case 7 -> {
                this.name = "Rope";
                this.rope = (BufferedImage) ROPEFG;
                accessible = "Can Climb";
                this.SlamEntryModifier = 1;
                this.SlamExitModifier = 1;
            }
            case 8 -> {
                this.name = "Canvas";
                accessible = "";
                this.SlamEntryModifier = 0;
                this.SlamExitModifier = 1;
            }

            default -> {
                this.name = "RingPost";
                accessible = "Can Climb";
                this.SlamEntryModifier = 1;
                this.SlamExitModifier = 2;
            }
        }
    }

    public int setParent(Tile parent){
        depth = parent.depth + 1;
        this.parent = parent;

        return depth;
    }

    Character Occupant(){
        for(Character i: Map.Characters){
            if(i.CurTile == this && i.state!=2){
                return i;
            }
        }

        if(Map.ref.CurTile == this){
            return Map.ref;
        }

        return null;
    }

    public int compareTo(Tile other) {

        float f = heuristic + cost;
        float of = other.heuristic + other.cost;

        if (f < of) {
            return -1;
        } else if (f > of) {
            return 1;
        } else {
            return 0;
        }
    }

    boolean Occupied(){
        return Occupant()!=null;
    }

    boolean canMove(){
        return !Occupied() && ((this.type == 5 || this.type == 8));
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
