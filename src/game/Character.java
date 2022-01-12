package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Character {

    int x;
    int y;
    Tile CurTile;
    Tile PrevTile;
    Color image;
    int MovePoints;
    int MaxMove;
    int Health;
    int MaxHealth;
    int painThresh;
    int regen;
    int stamregen;
    int state;
    int orientation;
    double rot;
    Image[] sprites;
    Sprite sprite;


    // 0 = neutral , 1 = down 2 = pinning 3 = pinned
    public final String[] states = {
            "Neutral",
            "Down",
            "Pinning",
            "Pinned"

    };


    String teamname;
    String name;
    Tile[] movePath;
    int pathpos = 0;
    boolean moving = false;
    boolean attacking = false;
    boolean pinning = false;
    boolean selfMove;
    boolean[] healthBar;
    boolean[] staminaBar;
    Action[] strikes;
    Action[] slams;


    Character(Tile t){
        this.x = t.CenterX;
        this.y = t.CenterY;
        this.CurTile = t;
        movePath = new Tile[MaxMove];
        state = 0;

    }

    Character(Tile t, String name){
        this.x = t.CenterX;
        this.y = t.CenterY;
        this.CurTile = t;
        movePath = new Tile[MaxMove];
        state = 0;
        this.name = name;

    }

    void changeHealth(int change){this.Health += change;

        if(Health<=0){
            Health = 0;
        }

        if(Health<=painThresh){
            if(state != 3){
                state = 1;
                sprite.setImage(sprites[1]);
            }
        }
        else if(state != 2 && state !=3){
            state = 0;
            if(!attacking) {
                sprite.setImage(rotate((BufferedImage) sprites[0], rot));
            }
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
        if(selfMove) {
            MoveRot(path[0], CurTile);
        }
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
        CurTile.Occupant().finishedMove();
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
        this.PrevTile = CurTile;
        if(selfMove) {
            MoveRot(CurTile, PrevTile);
        }
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
        if(attacking){
            if(!moving && !pinning){
                finishedMove();
            }
        }

    }

    void finishedMove(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        orient(orientation);
        sprite.setImage(rotate((BufferedImage)sprites[0], rot));
        attacking = false;
    }

    BufferedImage rotate(BufferedImage sprite, double rotation){
        int width = sprite.getWidth();
        int height = sprite.getHeight();

        BufferedImage dest = new BufferedImage(height, width, sprite.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(rotation, height / 2, width / 2);
        graphics2D.drawRenderedImage(sprite, null);

        return dest;

    }

    void move() {
        if ( movePath.length == 0 || movePath==null || movePath[pathpos] == null) {


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
            else{
                selfMove = false;
                moving = false;
                emptyPath();
                pathpos = 0;
            }
        } else {
            if (x != CurTile.CenterX || y != CurTile.CenterY) {

                if (x == movePath[pathpos].CenterX && y == movePath[pathpos].CenterY) {
                    Tile prev = movePath[pathpos];
                    pathpos++;
                    if(selfMove && !attacking){
                        MoveRot(movePath[pathpos], prev);
                    }
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
                selfMove = false;
                moving = false;
                emptyPath();
                pathpos = 0;
            }
        }
    }

    private void MoveRot(Tile dest, Tile cur) {
        int dir = FindDir(dest,cur);

        if(dir > -1){
            orient(dir);
            orientation = dir;
            sprite.setImage(rotate((BufferedImage)sprites[0], rot));
        }
    }

    public int FindDir(Tile dest, Tile cur){
        int dir = -1;
        ArrayList<Tile> neighbours = Map.neighbourTiles(cur);
        for(int i = 0;i<neighbours.size();i++){
            if(neighbours.get(i) == dest){
                dir = i;
                break;
            }
        }

        return dir;
    }

    public void orient(int orientation) {
        switch (orientation) {
            case 0 -> rot = Math.PI / 2;
            case 1 -> rot = (Math.PI / 2) + (Math.PI / 4);
            case 2 -> rot = Math.PI;
            case 3 -> rot = ((Math.PI / 2) + (Math.PI / 4)) * -1;
            case 4 -> rot = (Math.PI / 2) * -1;
            case 5 -> rot = (Math.PI / 4) * -1;
            case 6 -> rot = 0;
            case 7 -> rot = (Math.PI / 4);
        }
    }

    void draw(Graphics2D g){
        g.setColor(this.image);
        g.fillOval(this.x,this.y,10,10);
    }

    @Override
    public String toString() {
        return "Character{" +
                "CurTile=" + CurTile +
                ", MovePoints=" + MovePoints +
                ", Health=" + Health +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", orientation=" + orientation +
                '}';
    }
}

