package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static game.Constants.*;

public class Map extends JPanel {

    boolean running = true;
    boolean start = true;
    int TurnCounter = 0;
    int MoveDelay = 100000;
    Character CurrentPlayer;
    Character[] Characters = new Character[2];
    public final Tile[][] TileGrid = new Tile[ROWS][COLS];
    Tile Selected;


    public static final int[] TileArray = {
            6, 6, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 2, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 6,
            6, 5, 5, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 4, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
    };

    public Map(){
        int i = 0;
        for (int r = 0; r < ROWS; r++){
            for (int c = 0; c < COLS; c++){
                this.TileGrid[r][c] = new Tile(TileArray[i]);
                i++;
            }
        }
        int GridWidth = COLS * TILE_SIZE;
        int GridHeight = ROWS * TILE_SIZE;
        setPreferredSize(new Dimension(GridWidth,GridHeight));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tile TargetTile = FindTile(e.getX(),e.getY());
                if (SwingUtilities.isRightMouseButton(e)){
                    if(ValidMove(TargetTile, CurrentPlayer)){
                        charMove(TargetTile);
                    }
                }
                else{
                    if(Selected == TargetTile){
                        Selected = null;
                    }
                    else{
                        Selected = TargetTile;
                    }


                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        new Thread(this::loop).start();

    }

    void charMove(Tile t){
        int moveCost;

        if(t.x == CurrentPlayer.CurTile.x){
             moveCost = t.y - CurrentPlayer.CurTile.y;
        }
        else if(t.y == CurrentPlayer.CurTile.y){
            moveCost = t.x - CurrentPlayer.CurTile.x;
        }
        else{
            moveCost = Math.abs(t.x - CurrentPlayer.CurTile.x) + Math.abs(t.y - CurrentPlayer.CurTile.y);
        }

        CurrentPlayer.setTile(t, createPath(t,CurrentPlayer));
        CurrentPlayer.MovePoints = CurrentPlayer.MovePoints - Math.abs(moveCost);
        CurrentPlayer.moving = true;



    }

    int distance(Tile t1, Tile t2){
        return  Math.abs(t1.x-t2.x) + Math.abs(t1.y-t2.y);
    }

    Boolean ValidMove(Tile t, Character c){
        if(!t.CanMove){
           return false;
        }

        if(distance(t, c.CurTile) > c.MovePoints){
            return false;
        }

        

        if(c.MovePoints<1){
            return false;
        }

        return checkTiles(c.CurTile,t,c.MovePoints);
    }

    boolean checkTiles(Tile start, Tile end, int moves){
        Tile current = start;
        Tile closest = start;
        int points = moves;
        int i = 0;

        if(start == end){
            return false;
        }

        while(points>0) {
            int LowDis = 100;

            ArrayList<Tile> neighbours = neighbourTiles(current);
            for(Tile t:neighbours){
                if(t == end){
                    return true;
                }
            }

            neighbours.removeIf(t -> !t.CanMove);
            for(Tile t: neighbours){

                if(distance(t,end) < LowDis){
                    LowDis = distance(t,end);
                    closest = t;
                }

            }

            current = closest;
            points--;
        }

        return false;

    }

    Tile[] createPath(Tile t, Character c){
        Tile[] path = new Tile[c.MovePoints];
        Tile current = c.CurTile;
        int i = 0;
        while(current!=t){
            ArrayList<Tile> neighbours = neighbourTiles(current);
            int close = 100;

            neighbours.removeIf(tile -> !tile.CanMove);

            for(Tile tile: neighbours){
                if(distance(tile, t) < close){
                    close = distance(tile, t);
                    current = tile;
                }
            }
            path[i]=current;
            i++;
        }
        return path;
    }

    ArrayList<Tile> neighbourTiles(Tile t){
        ArrayList<Tile> neighbours = new ArrayList<>();

        neighbours.add(TileGrid[t.y][t.x+1]);
        neighbours.add(TileGrid[t.y+1][t.x+1]);
        neighbours.add(TileGrid[t.y+1][t.x]);
        neighbours.add(TileGrid[t.y+1][t.x-1]);
        neighbours.add(TileGrid[t.y][t.x-1]);

        if(t.y != 0){
            neighbours.add(TileGrid[t.y - 1][t.x-1]);
            neighbours.add(TileGrid[t.y - 1][t.x]);
            neighbours.add(TileGrid[t.y - 1][t.x+1]);

        }

        return neighbours;
    }

    void SpawnJon(int x, int y){
        Characters[0] = new jon(TileGrid[x][y]);
    }

    void SpawnJak(int x, int y){
        Characters[1] = new jak(TileGrid[x][y]);

    }

    Tile FindTile(int x, int y){
        int Tx = 0;
        int Ty = 0;
        for (int r = 0; r < ROWS; r++) {
            if(y > TileGrid[r][0].HighY){
            }
            else{
                Ty = r;
                break;
            }
        }

        for (int c = 0; c < COLS; c++) {
            if(x > TileGrid[Ty][c].HighX){
            }
            else{
                Tx = c;
                break;
            }
        }
        return TileGrid[Ty][Tx];
    }

    void update(){
        repaint();
        if(CurrentPlayer != null){
            if(MoveDelay ==0) {
                for (Character i : Characters) {
                    if(i.moving){
                        i.move();
                    }

                }
                MoveDelay = 50000;
            }
            else{
                MoveDelay--;
            }
            if (CurrentPlayer.MovePoints == 0){
                TurnCounter++;
                if (TurnCounter >= Characters.length){
                    TurnCounter = 0;
                    for(Character i: Characters){
                        i.resetTurn();
                    }
                }
                CurrentPlayer = Characters[TurnCounter];
            }
        }
    }

    public void loop(){
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                delta--;
            }
            update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth() / COLS;
        int height = getHeight() / ROWS;
        int transparency = 40;
        Color moveable = new Color(0,255,0, transparency);
        Color grid = new Color(0, 0, 0, transparency);
        String tileName;
        String tileOccupant;
        String tileAccess;


        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int y = r * width;
                int x = c * height;
                Color TileColour = TileGrid[r][c].image;
                g.setColor(TileColour);
                g.fillRect(x, y, width, height);
                g.setColor(grid);
                g.drawRect(x,y, width, height);
                if(CurrentPlayer!=null) {
                    if (ValidMove(TileGrid[r][c], CurrentPlayer)) {
                        g.setColor(moveable);
                        g.fillRect(x, y, width, height);
                    }
                }

                TileGrid[r][c].setCenter(x,y);
                TileGrid[r][c].setBounds(x,y,r,c);
            }
        }

        tileOccupant = "";

        if(Selected!=null){
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.blue);
            g2.drawRect(Selected.AccX,Selected.AccY,width,height);
            g2.setStroke(new BasicStroke(1));

            tileName = Selected.name;
            if(Selected.Occupant!=null){
                tileOccupant = Selected.Occupant.name;
            }

            tileAccess = Selected.accessible;

            Font font = new Font("Verdana", Font.PLAIN, 15);
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString(Selected.toString(), TILE_SIZE / 2,getHeight() - (TILE_SIZE * 2) - font.getSize() * 2 );
            g.drawString(tileOccupant, TILE_SIZE / 2,getHeight() - (TILE_SIZE * 2) - font.getSize() );
            g.drawString(tileName, TILE_SIZE / 2,getHeight() - TILE_SIZE * 2 );
            g.drawString(tileAccess, TILE_SIZE / 2,getHeight() - (TILE_SIZE * 2) + font.getSize());
        }




        if(start){
            SpawnJon(10,10);
            SpawnJak(10 , 9);

            CurrentPlayer = Characters[0];
            start = false;
        }

        for(Character i: Characters){
            g.setColor(i.image);
            g.fillOval(i.x,i.y,10,10);
        }
    }
}
