package game;


import java.awt.*;
import static game.Constants.TILE_SIZE;

public class Tile {
    int x, y;
    int AccX, AccY;
    Integer type;
    Color image;
    String name;
    int CenterX;
    int CenterY;
    int HighX;
    int HighY;
    Character Pinner;
    String accessible;




    public static final Color TL_CORNER = new Color(255, 0, 0);
    public static final Color TR_CORNER = new Color(255,255,255);
    public static final Color BL_CORNER = new Color(255,255,255);
    public static final Color BR_CORNER = new Color(0,0,255);
    public static final Color GROUND = new Color(48, 46, 97);
    public static final Color CROWD = new Color(224, 186, 19);
    public static final Color ROPE = new Color(15, 111, 5);
    public static final Color CANVAS = new Color(177, 161, 148);

    public static final Color[] CONTENTS = {
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
                accessible = "Can be thrown into";
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
        this.CenterX = x + (TILE_SIZE / 2) - 5;
        this.CenterY = y + (TILE_SIZE / 2) - 5;
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
