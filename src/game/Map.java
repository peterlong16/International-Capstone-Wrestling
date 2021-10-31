package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static game.Constants.*;

public class Map extends JPanel {


    static boolean running = true;
    static boolean start = true;
    boolean atkPrimed = false;
    boolean moverPrimed = false;
    boolean i = true;
    static boolean ended = false;

    Action primedAtk;

    static int TurnCounter = 0;
    int MoveDelay = 100000;
    static int pinCount = 0;

    static Character CurrentPlayer;
    static Character[] Characters = new Character[3];
    public static final Tile[][] TileGrid = new Tile[ROWS][COLS];
    Tile Selected;
    Button[] buttons;
    Button[] context;
    Button[] Kickoutbuttons;
    static String winText = "";
    Button exitButton;
    static Button[] resetButton = new Button[1];


    public static final int[] TileArray = {
            /*
            * This array of numbers dictates which type each tile will be, with each number corresponding to a different
            * tile type.
            *
            * */



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

        /*
        * Map constructor assigns each tile it's place in the grid alongside its type.
        * */
        int i = 0;
        for (int r = 0; r < ROWS; r++){
            for (int c = 0; c < COLS; c++){
                TileGrid[r][c] = new Tile(TileArray[i]);
                i++;
            }
        }
        int GridWidth = COLS * TILE_SIZE;
        int GridHeight = ROWS * TILE_SIZE;
        setPreferredSize(new Dimension(GridWidth,GridHeight));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                Tile TargetTile = FindTile(e.getX(), e.getY());
                Button button = findButton(e.getX(),e.getY());
                if(button!=null && (button.action.type.equals("misc") || button.action.type.equals("reset"))){
                    button.action.Execute();
                    if(button.action.type.equals("misc")) {
                        changeTurn();
                    }
                }
                else {
                    if (atkPrimed) {

                    /* Activates if the player has clicked an attack button. the next input from the right mouse button
                       designates a target for the attack
                     */

                        if (SwingUtilities.isRightMouseButton(e)) {

                        /* If an attack is labelled as a 'mover' it will move the target of the attack. If this is the
                           case, an additional step is entered in which the next right click will designate the tile that
                           the target will be moved to, in accordance with the parameters set in the attack's class. if
                           a left click is registered while the attack or move target are primed, the attack is cancelled.
                     */
                            if (!moverPrimed) {
                                if (primedAtk.canHit(TargetTile, distance(TargetTile, CurrentPlayer.CurTile))) {
                                    primedAtk.addTarget(TargetTile.Occupant());
                                    primedAtk.setCharMove(TileGrid[CurrentPlayer.CurTile.y + primedAtk.CharMovey][CurrentPlayer.CurTile.x + primedAtk.CharMovex]);
                                }
                            } else {
                                if (primedAtk.canTargetMove(TargetTile, distance(TargetTile, CurrentPlayer.CurTile))) {
                                    primedAtk.setTargetMove(TargetTile);
                                }
                            }
                            if (primedAtk.mover && !moverPrimed) {
                                moverPrimed = true;
                            } else {

                                if (primedAtk.gotTargets()) {
                                    primedAtk.Execute();
                                    atkPrimed = false;
                                    primedAtk = null;
                                    moverPrimed = false;
                                    createContextual();
                                }

                            }
                        } else {
                            primedAtk.emptyTargets();
                            atkPrimed = false;
                            moverPrimed = false;
                            primedAtk = null;
                        }


                    } else {

                        /*
                         * If a click is registered over a button, selecting a tile is suppressed its corresponding action
                         * is primed
                         * */

                        if (button != null && button.action.canAfford()) {
                            primedAtk = button.action;
                            atkPrimed = true;
                        } else {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                if (ValidMove(TargetTile, CurrentPlayer) && CurrentPlayer.state == 0) {
                                    charMove(TargetTile);
                                }
                            } else {
                                if (Selected == TargetTile) {
                                    Selected = null;
                                } else {
                                    Selected = TargetTile;
                                }


                            }
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        new Thread(this::loop).start();

    }

    static void resetGame(){
        start = true;
        TurnCounter = 0;
        pinCount = 0;
        resetButton[0] = null;
        ended = false;
        winText = "";
        CurrentPlayer = null;




    }
    
    int moveCost(Tile t){
         // Calculates the cost of moving from the current player's tile to a given tile
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
        return Math.abs(moveCost);
    }

    void charMove(Tile t){
        //Moves the current player to their selected tile
        int cost = moveCost(t);
        CurrentPlayer.setTile(t, createPath(t,CurrentPlayer));
        CurrentPlayer.MovePoints = CurrentPlayer.MovePoints - cost;
        CurrentPlayer.moving = true;
        context = new Button[1];
        createContextual();
    }

    int distance(Tile t1, Tile t2){
        return  Math.abs(t1.x-t2.x) + Math.abs(t1.y-t2.y);
    }

    Boolean ValidMove(Tile t, Character c){
        if(!t.canMove()){
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
        // Checks if the player has a clear path to the selected tile
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
            neighbours.removeIf(t -> !t.canMove());
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

        //creates a movement path for the player character

        Tile[] path = new Tile[c.MovePoints];
        Tile current = c.CurTile;
        int i = 0;
        while(current!=t){
            ArrayList<Tile> neighbours = neighbourTiles(current);
            int close = 100;
            neighbours.removeIf(tile -> !tile.canMove());

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

    static ArrayList<Tile> neighbourTiles(Tile t){

        //returns a list of tiles surrounding a given tile;

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

    Character SpawnJon(int x, int y){
        return new jon(TileGrid[x][y]);
    }

    Character SpawnJak(int x, int y){
        return new jak(TileGrid[x][y]);
    };

    Character SpawnJay(int x, int y){
        return new jay(TileGrid[x][y]);
    };

    Tile FindTile(int x, int y){

        // Finds the tile that's been drawn at the given coordinates

        int Tx = 0;
        int Ty = 0;
        for (int r = 0; r < ROWS; r++) {
            if(y < TileGrid[r][0].HighY){
                Ty = r;
                break;
            }

        }

        for (int c = 0; c < COLS; c++) {
            if(x < TileGrid[Ty][c].HighX){
                Tx = c;
                break;
            }


        }
        return TileGrid[Ty][Tx];
    }

    Button findButton(int x, int y){

        // returns true if a button has been drawn on the given coordinates

        Button button = null;


        button = getButton(x, y, button, buttons);
        button = getButton(x,y,button,context);
        button = getButton(x, y, button, Kickoutbuttons);
        button = getButton(x,y,button,resetButton);





        return button;
    }

    private Button getButton(int x, int y, Button button, Button[] buttons) {
        if(buttons !=null && buttons[0]!=null) {
            for (Button b : buttons) {
                if (x > b.x1
                        && x < b.x2
                        && y > b.y1
                        && y < b.y2
                ) {

                    button = b;
                    break;
                }
            }
        }
        return button;
    }


    void update(){
        if(running) {
            repaint();


            if (CurrentPlayer != null && Characters[0]!=null) {
                if (MoveDelay == 0) {
                    for (Character i : Characters) {
                        i.Update();
                    }
                    MoveDelay = 50000;
                } else {
                    MoveDelay--;
                }
                if (CurrentPlayer.MovePoints == 0 || CurrentPlayer.state == 2) {

                    changeTurn();

                }
            }
        }
    }

    void endGame(){
        ended = true;
        Kickoutbuttons = null;
        winText = CurrentPlayer.teamname + " wins!";
        resetButton[0] = new Button(new resetGame(CurrentPlayer), 40, getWidth()/2,getHeight()/2);
    }



    void changeTurn(){

        if ((CurrentPlayer.state == 2 || CurrentPlayer.state == 3)  && !ended) {
            pinCount++;

        }

        if (pinCount >= 3) {
            endGame();
            return;
        }
        if(!ended) {
            TurnCounter++;
            if (TurnCounter >= Characters.length) {

                TurnCounter = 0;
            }

            Characters[TurnCounter].resetTurn();
            CurrentPlayer = Characters[TurnCounter];


            switch (CurrentPlayer.state) {
                case 0 -> {
                    Kickoutbuttons = null;
                    buttons = new Button[CurrentPlayer.moves.length];
                    for (int i = 0; i < CurrentPlayer.moves.length; i++) {
                        buttons[i] = new Button(CurrentPlayer.moves[i], 20);
                    }
                    createContextual();
                }
                case 1 -> {
                    changeTurn();

                    Kickoutbuttons = null;
                }
                case 2 -> {
                    changeTurn();


                    buttons = null;
                }
                case 3 -> {
                    createKickout();
                    buttons = null;
                }
                default -> {

                }
            }
        }



    }

    Action[] findContextual(){
        Action[] context = new Action[2];
        int i = 0;

       for(Tile t : neighbourTiles(CurrentPlayer.CurTile)){
           if(t.Occupied() && t.Occupant().state==1 && !t.Occupant().teamname.equals(CurrentPlayer.teamname) && CurrentPlayer.state == 0){
               context[i] = new Pin(CurrentPlayer);
               i++;
           }
        }



       return context;
    }

    void createContextual(){
        context = new Button[1];
        if(findContextual()[0]!=null){
            context[0] = new Button(findContextual()[0], 20);
        }
    }

    void createKickout(){

        Kickoutbuttons = new Button[CurrentPlayer.MaxHealth - CurrentPlayer.Health];

        int randomNum = ThreadLocalRandom.current().nextInt(0, (CurrentPlayer.MaxHealth - CurrentPlayer.Health));


        System.out.println("Kickout: " + randomNum);
        for(int i = 0;i<CurrentPlayer.MaxHealth - CurrentPlayer.Health;i++){
            if(i == randomNum){
                Kickoutbuttons[i] = new Button(new Kickout(CurrentPlayer), 40);
            }
           else{
                Kickoutbuttons[i] = new Button(new KickoutFail(CurrentPlayer), 40);
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
        Color underhalf = new Color(0,255,0, transparency);
        Color overhalf = new Color(248, 223, 2, transparency);
        Color all = new Color(246, 116, 0, transparency);
        Color grid = new Color(0, 0, 0, transparency);
        Color canHit = new Color(255,0,0, transparency);
        String tileName;
        String tileOccupant;
        String tileAccess;
        String state;

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
                    if(!atkPrimed) {
                        if (ValidMove(TileGrid[r][c], CurrentPlayer) && CurrentPlayer.state == 0) {
                            if (moveCost(TileGrid[r][c]) == (CurrentPlayer.MovePoints)) {
                                g.setColor(all);
                            } else if (moveCost(TileGrid[r][c]) > (CurrentPlayer.MovePoints / 2)) {
                                g.setColor(overhalf);
                            } else {
                                g.setColor(underhalf);
                            }

                            g.fillRect(x, y, width, height);
                        }
                    }
                }

                if(atkPrimed){
                    Selected = null;
                    if(primedAtk.canHit(TileGrid[r][c], distance(primedAtk.user.CurTile, TileGrid[r][c] ))
                            && TileGrid[r][c].Occupant() != null){

                        g.setColor(canHit);
                        g.fillRect(x, y, width, height);
                    }
                }
                if(moverPrimed){
                    if(primedAtk.canTargetMove(TileGrid[r][c], distance(TileGrid[r][c],primedAtk.user.CurTile))){
                        g.setColor(canHit);
                        g.fillRect(x, y, width, height);
                    }
                }
                TileGrid[r][c].setCenter(x,y);
                TileGrid[r][c].setBounds(x,y,r,c);
            }
        }
        tileOccupant = "";
        state = "";

        if(start){
            Characters[0] = SpawnJon(10,9);
            Characters[1] = SpawnJak(10, 8);
            Characters[2] = SpawnJay(10, 7);
            CurrentPlayer = Characters[0];
            buttons = new Button[CurrentPlayer.moves.length];
            for(int i = 0; i < CurrentPlayer.moves.length; i++){
                buttons[i] = new Button(CurrentPlayer.moves[i], 20);
            }
            Kickoutbuttons = null;
            pinCount = 0;
            start = false;
            TurnCounter = 0;
        }

        for(Character i: Characters){
            g.setColor(i.image);
            g.fillOval(i.x,i.y,10,10);
        }

        if(Selected!=null){
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.blue);
            g2.drawRect(Selected.AccX,Selected.AccY,width,height);
            g2.setStroke(new BasicStroke(1));

            tileName = Selected.name;
            if(Selected.Occupant()!=null){
                tileOccupant = Selected.Occupant().name;
                state = Selected.Occupant().states[Selected.Occupant().state];

                int Xhbars = Selected.CenterX - ((((Selected.Occupant().healthBar.length * 10) + (Selected.Occupant().healthBar.length - 1))/2) - 5)  ;
                int Yhbars = Selected.CenterY - TILE_SIZE;

                int Xsbars = Selected.CenterX - ((((Selected.Occupant().staminaBar.length * 10) + (Selected.Occupant().staminaBar.length - 1))/2) - 5)  ;
                int Ysbars = Selected.CenterY - (TILE_SIZE - 10);
                int c = 0;

                for(boolean i:Selected.Occupant().healthBar){
                    if(i && c>=Selected.Occupant().painThresh){
                        g.setColor(Color.green);
                    }
                    else if(i){
                        g.setColor(Color.red);
                    }
                    else{
                        g.setColor(Color.black);
                    }
                    g.fillRect(Xhbars,Yhbars,10,5);
                    if(Selected.Occupant().painThresh == c){
                        g.setColor(Color.white);
                        g.drawLine(Xhbars - 1, Yhbars + 5, Xhbars - 1, Yhbars - 2);
                    }
                    Xhbars = Xhbars + 11;
                    c++;
                }
                for(boolean i:Selected.Occupant().staminaBar){
                    if(i){
                        g.setColor(Color.blue);
                    }
                    else{
                        g.setColor(Color.black);
                    }

                    g.fillRect(Xsbars,Ysbars,10,5);

                    Xsbars = Xsbars + 11;
                }







            }

            tileAccess = Selected.accessible;

            Font font = new Font("Verdana", Font.PLAIN, 15);
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString(Selected.toString(), TILE_SIZE / 2,getHeight() - (TILE_SIZE * 2) - font.getSize() * 2 );
            g.drawString(tileOccupant + "(" + state + ")", TILE_SIZE / 2,getHeight() - (TILE_SIZE * 2) - font.getSize() );
            g.drawString(tileName, TILE_SIZE / 2,getHeight() - TILE_SIZE * 2 );
            g.drawString(tileAccess, TILE_SIZE / 2,getHeight() - (TILE_SIZE * 2) + font.getSize());

        }


        if(buttons!=null && !atkPrimed) {
            int buttonx = CurrentPlayer.CurTile.CenterX - ((((buttons.length * buttons[0].size) + (buttons.length - 1))/2) - (buttons[0].size/2));
            int buttony = CurrentPlayer.CurTile.CenterY + buttons[0].size;
            drawButtons(g, buttonx, buttony, 10, buttons);
        }

        if(context!=null && context[0]!=null && !atkPrimed){

            int contextx = CurrentPlayer.CurTile.CenterX - ((((context.length * context[0].size) + (context.length - 1))/2) - (context[0].size/2));
            int contexty = CurrentPlayer.CurTile.CenterY + (context[0].size * 2) + 2;

            drawButtons(g,contextx,contexty,10,context);
        }

        if(Kickoutbuttons!=null) {
            drawButtons(g, (getWidth()) / 2, getHeight() - ((Kickoutbuttons[0].size) + 10), 30, Kickoutbuttons);
        }

        if(resetButton[0]!=null){
            Button reset = resetButton[0];

            g.setColor(Color.black);
            g.fillRect(reset.x1,reset.y1,reset.size,reset.size);

        }

        g.drawString(winText, getWidth()/2,20);
        g.drawString(CurrentPlayer.toString(), 100,30);
        int namex = 200;
        int namey = 50;
        g.drawString("Player order: ", 100,50);
        for(Character i: Characters){
            g.drawString(i.name,namex,namey);
            namex += 40;
        }
        g.drawString("Pin count : " + pinCount,100,70);
        g.drawString("Turn counter : " + TurnCounter,100, 90);

    }

    private void drawButtons(Graphics g, int x, int y, int gap, Button[] buttons) {

        for(Button b: buttons){
            b.setX(x);
            b.setY(y);
            g.setColor(Color.black);
            g.fillRect(b.x1,b.y1,b.size,b.size);

            x += (buttons[0].size + gap);
        }
    }
}
