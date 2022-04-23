package game;

import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static game.Constants.*;

public class Referee extends Character{
    private final int MinDistToChars;
    private final int MaxDistToChars;
    public final int ViewDistance;
    public final float FOV;
    ArrayList<Tile> ViewTiles;




    Referee(Tile t) {
        super(t);
        this.name = "Referee";
        this.DEFAULT_MAX_HEALTH = 3;
        this.DEFAULT_MAX_STAMINA = 1;
        DEFAULT_MAX_HEALTHREGEN = 1;
        DEFAULT_MAX_STAMREGEN = 1;
        state = 0;
        this.teamname = "ref";
        painThresh = 2;
        this.healthBar = new boolean[MaxHealth];
        Arrays.fill(healthBar, true);
        this.staminaBar = new boolean[MaxMove];
        Arrays.fill(staminaBar, true);
        MinDistToChars = 2;
        MaxDistToChars = 3;

        ViewDistance = 7;
        FOV = 90;
        this.sprite = new Sprite(this,Constants.TILE_SIZE,Constants.TILE_SIZE);

        ViewTiles = new ArrayList<Tile>();

        MaxMove = DEFAULT_MAX_STAMINA;
        MovePoints = MaxMove;

        MaxHealth = DEFAULT_MAX_HEALTH;
        Health = MaxHealth;

        regen = DEFAULT_MAX_HEALTHREGEN;
        stamregen = DEFAULT_MAX_STAMREGEN;

        sprites = new Image[]{
                Sprite.RefIdle,
                Sprite.RefDown,
                Sprite.RefPin,
        };


        sprite.setImage(sprites[0]);
    }

    @Override
    void changeHealth(int change) {

        this.Health += change;

        if(Health<=0){
            Health = 0;
        }

        if(Health<=painThresh){

                state = 1;
                sprite.setImage(sprites[1]);

        }
        else if(this.state!=4){
            state = 0;
            if(!attacking) {
                sprite.setImage(rotate((BufferedImage) sprites[0], rot));
            }
        }

        if(this.state == 1){
        ViewTiles.clear();
    }
    }

    void pin(){
        this.state = 4;
        this.orientation = TowardMost();
        this.orient(orientation);
        sprite.setImage(rotate((BufferedImage)sprites[2], rot));

    }

    Tile findMovement(){

        int [][] NeighbourScores = new int[8][1];

        int lowdis = 100;
        Character closest = null;

        for(Character c: Map.Characters){
            if(Map.distance(this.CurTile,c.CurTile) < lowdis){
                lowdis = Map.distance(this.CurTile,c.CurTile);
                closest = c;
            }
        }

        if(closest==null){
            return this.CurTile;
        }


        if(lowdis<MinDistToChars){
            for(int i = 0;i<7;i++){
                for(Character c:Map.Characters) {
                    if (Map.neighbourTiles(this.CurTile).get(i).canMove() && !Map.neighbourTiles(this.CurTile).get(i).Occupied()) {
                        NeighbourScores[i][0] += Map.distance(Map.neighbourTiles(this.CurTile).get(i), c.CurTile);
                    }
                }
            }
        }
        else if(lowdis>MaxDistToChars){
            for(int i = 0;i<8;i++){
                    if(!Map.neighbourTiles(this.CurTile).get(i).Occupied()){
                        NeighbourScores[i][0] = Map.distance(Map.neighbourTiles(this.CurTile).get(i), closest.CurTile) * -1;
                    }
                    else{
                        NeighbourScores[i][0] = -101;
                    }

            }
        }
        else{
            return this.CurTile;
        }


        int highestScore = -100;
        Tile best = null;

        for(int x = 0; x<8; x++){

            if(NeighbourScores[x][0] > highestScore){
                highestScore = NeighbourScores[x][0];
                best = Map.neighbourTiles(this.CurTile).get(x);
            }
        }

        if(highestScore == 0){
            return this.CurTile;
        }

        return best;
    }

    int TowardMost(){
        int [][] NeighbourScores = new int[8][1];

        for(int i = 0;i<8;i++){
            for(Character c:Map.Characters){

                if(Map.GetClosest(c.CurTile,this.CurTile) == Map.neighbourTiles(this.CurTile).get(i)){
                    if(c.state == 2 || c.state == 3){
                        return i;
                    }
                    NeighbourScores[i][0]++;
                }
            }
        }

        int highestScore = 0;
        int best = 0;

        for(int x = 0; x<8; x++){
            if(NeighbourScores[x][0] > highestScore){
                highestScore = NeighbourScores[x][0];
                best = x;
            }
        }



        return best;

    }

    void TilesinView(){


       ViewTiles.clear();
       double angle;


       angle = 0;

        angle = switch (this.orientation) {
            case 0 -> 0;
            case 1 -> 45;
            case 2 -> 90;
            case 3 -> 135;
            case 4 -> 180;
            case 5 -> 225;
            case 6 -> 270;
            case 7 -> 315;
            default -> angle;
        };



        ArrayList<point> allPoints = new ArrayList<>();
        float val = FOV/10;

        for(int i = 1; i<6;i++){
            float cur = val * i;
            double angleval;
            if(angle+cur>360){
                angleval =  cur - (360 - angle);
            }
            else{
                angleval =  (angle + cur);
            }
            allPoints.addAll(LinePoints(angleval));

            if(angle - cur < 0){
                angleval = 360 - Math.abs((angle - cur));
            }
            else{
                angleval = angle - cur;
            }

            allPoints.addAll(LinePoints(angleval));
        }

        allPoints.addAll(LinePoints(angle));












        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Tile t = Map.TileGrid[r][c];

                for(point p: allPoints){
                    if(p.x<t.HighX && p.x>t.CenterX && p.y<t.HighY && p.y>t.CenterY){
                        ViewTiles.add(t);
                        break;
                    }
                }
            }
        }










    }

    ArrayList<point> LinePoints(double angle){
        int x,x1,x2 = 0,y,y1,y2 = 0,dx,dy,d;

        int length = ViewDistance * TILE_SIZE;
        double xv = length * Math.cos(Math.toRadians(angle));
        double yv = length * Math.sin(Math.toRadians(angle));
        x1 = (int)this.CurTile.CenterX + (TILE_SIZE/2);
        y1 = (int)this.CurTile.CenterY + (TILE_SIZE/2);
        x2 = x1 + (int)xv;
        y2 = y1 + (int)yv;






         dx = Math.abs(x1 - x2);
         dy = Math.abs(y1 - y2);

        int sx = x2 < x1 ? -1 : 1;
        int sy = y2 < y1 ? -1 : 1;

         double err = dx-dy;
         double e2;

        ArrayList<point> points = new ArrayList<point>();



        while (x1 != x2 || y1 != y2)
        {

            points.add(new point(x1,y1));

            e2 = 2 * err;
            if (e2 > -dy)
            {
                err = err - dy;
                x1 = x1 + sx;
            }

            if (e2 < dx)
            {
                err = err + dx;
                y1 = y1 + sy;
            }
        }









       return points;
    }

    boolean inView(Tile t){



        for(int i = 0; i<ViewTiles.size();i++){
            if(ViewTiles.get(i) == t){
                return true;
            }
        }

        return false;
    }

    public void draw(Graphics2D g){
        this.sprite.draw(g);
    }

    class point{
        double x;
        double y;

        public point(double x1, double y1) {
            this.x = x1;
            this.y = y1;
        }
    }
}


