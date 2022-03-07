package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Character {

    float x;
    float y;
    int DEFAULT_MAX_HEALTH;
    int DEFAULT_MAX_HEALTHREGEN;
    int DEFAULT_MAX_STAMINA;
    int DEFAULT_MAX_STAMREGEN;

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
    int strikemod = 0;
    int slammod = 0;

    double rot;
    Image[] sprites;
    Sprite sprite;
    int diveint = 7;
    int interv = diveint;


    // 0 = neutral , 1 = down 2 = pinning 3 = pinned
    public final String[] states = {
            "Neutral",
            "Down",
            "Pinning",
            "Pinned",
            "Counting"

    };


    String teamname;
    String name;
    Tile[] movePath;
    int pathpos = 0;
    boolean moving = false;
    boolean attacking = false;
    boolean pinning = false;
    boolean selfMove;
    boolean flying = false;
    boolean[] healthBar;
    boolean[] staminaBar;
    boolean signature = false;
    Action atk;
    Action[] strikes;
    Action[] slams;
    Action[] dives;
    Action[] springs;


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

    boolean Arrived(Tile t){
        return (Math.abs(t.CenterX - x) + Math.abs(t.CenterY - y) < 3);
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

    void changeStam(int change){
        this.MovePoints += change;
        if(this.MovePoints > this.MaxMove){
           this.MovePoints = this.MaxMove;
        }
    }

    void printBar(){
        for(boolean i:this.healthBar){
            System.out.println(i);
        }
    }

    void updateSBar(){
        int sp = MovePoints;

        if(staminaBar.length != MaxMove){
            this.staminaBar = new boolean[MaxMove];
            Arrays.fill(this.staminaBar,false);
        }

        for(int i = 0;i<staminaBar.length;i++){
            staminaBar[i] = sp > 0;
            sp--;
        }

    }

    void updateHBar(){
        int hp = Health;

        if(healthBar.length != MaxHealth){


                this.healthBar = new boolean[MaxHealth];

            Arrays.fill(this.healthBar,false);
        }

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
        this.moving = true;
        Map.CalculatePaths();

    }

    void setPin(Tile t){
        this.CurTile = t;
        CurTile.Pinner = this;
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
        this.PrevTile = CurTile;
        if(selfMove) {
            MoveRot(CurTile, PrevTile);
        }
        this.CurTile = t;
        moving = true;
        movePath = null;
        Map.CalculatePaths();
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

        this.strikemod = 0;
        this.slammod = 0;
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
        if(flying){
            fly();
        }
        else if(moving){
            move();
        }
        if(attacking){

                if (!moving && !pinning && atk.DelayTrigger==null && (atk.targets[0]==null || atk.targets[0].Arrived(atk.targets[0].CurTile)) && this.Arrived(this.CurTile)) {

                        try{
                            Thread.sleep(500);
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }

                    finishedMove();

                }

        }

        changeHealth(0);


    }

    void Dive(){
        flying = true;

    }

    void finishedMove(){

        orient(orientation);
        atk.emptyTargets();
        attacking = false;
        atk.DelayTrigger = null;
        atk = null;
        changeHealth(0);
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
        if(this.atk!=null&&atk.DelayTrigger!=null&&Arrived(atk.DelayTrigger)){
            atk.DelayAction();
        }


         if(Arrived(CurTile) && (movePath == null || pathpos >= movePath.length -1)){
             selfMove = false;
             moving = false;
             if (movePath != null) {
                 emptyPath();
             }
             pathpos = 0;
             flying = false;
         }
         if ( movePath==null || movePath.length == 0 || movePath[pathpos] == null) {


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
            if (x != movePath[pathpos].CenterX || y != movePath[pathpos].CenterY) {


                    if (x > movePath[pathpos].CenterX) {
                        if(flying){
                            x= x -2;
                        }
                        else {
                            x--;
                        }
                    }
                    if (x < movePath[pathpos].CenterX) {
                        if(flying){
                            x = x + 2;
                        }
                        else {
                            x++;
                        }
                    }
                    if (y > movePath[pathpos].CenterY) {
                        y--;

                    }
                    if (y < movePath[pathpos].CenterY) {
                        y++;

                    }



            } else if (x == movePath[pathpos].CenterX && y == movePath[pathpos].CenterY &&
                     pathpos!=movePath.length-1) {

            Tile prev = movePath[pathpos];

            if(atk != null && atk.DelayTrigger!=null && Arrived(atk.DelayTrigger)){
                atk.DelayAction();
            }
            pathpos++;
            if(selfMove && !attacking){
                MoveRot(movePath[pathpos], prev);
            }

            }

        }
    }

    void fly(){
        float dx;
        float dy;

        if(atk != null && atk.DelayTrigger!=null && Arrived(atk.DelayTrigger) && attacking){
            System.out.println("yes");
            atk.DelayAction();
        }

        if(Arrived(CurTile) && (movePath == null || pathpos >= movePath.length -1)){
            x = CurTile.CenterX;
            y = CurTile.CenterY;
            flying = false;
            moving = false;


        }
        else {
            if(movePath!=null && movePath[pathpos] != null) {
                if(Arrived(movePath[pathpos])){
                    pathpos++;
                }
                dx = movePath[pathpos].CenterX - x;
                dy = movePath[pathpos].CenterY - y;
            }
            else {
                dx = CurTile.CenterX - x;
                dy = CurTile.CenterY - y;
            }

                float length = (float) Math.sqrt(dx * dx + dy * dy);

                dx /= length;
                dy /= length;


                dx *= 1.5;
                dy *= 1.5;


                x += dx;
                y += dy;

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
        sprite.draw(g);
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

