package game;
import Utilities.ImageManager;
import Utilities.SortedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

import static game.Constants.*;

public class Map extends JPanel {


    static boolean running = true;
    static boolean start = true;
    boolean atkPrimed = false;
    boolean moverPrimed = false;
    boolean i = true;
    boolean PinCountUpdate = false;
    static boolean ended = false;
    static int MenuSelect = 0;
    static boolean reveal;



    //0 = none, 1= strikes, 2= slams, 3 = context

    Action primedAtk;

    static int TurnCounter = 0;
    int MoveDelay = 100000;
    static int pinCount = 0;
    int Screenwidth;

    static Character CurrentPlayer;
    static Character[] Characters = new Character[6];
    TurnOrder[] turnOrder = new TurnOrder[6];
    public static final Tile[][] TileGrid = new Tile[ROWS][COLS];
    Tile Selected;
    Button[] menu;
    static Button[] strikes;
    static Button[] slams;
    static Button[] context;
    static Button[] Kickoutbuttons;
    static String winText = "";
    Button exitButton;
    Boolean[] sequence;
    int mousestep = 0;
    static Button[] resetButton = new Button[1];
    String desc;
    Action hoverA;
    Character hoverC;
    int transparencyVal = 0;



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
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
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
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Button hoverbut = findButton(e.getX(),e.getY());
                TurnOrder turn = findturnOrder(e.getX(),e.getY());

                if(hoverbut!=null && hoverbut.action!=null){
                    hoverA = hoverbut.action;
                    Selected = null;
                    hoverC = null;
                }

                if(turn!=null){
                    hoverC = turn.c;
                    Selected = hoverC.CurTile;
                    hoverA = null;
                }
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                Tile TargetTile = FindTile(e.getX(), e.getY());
                Button button = findButton(e.getX(),e.getY());
                if(reveal){
                    reveal = false;
                    Kickoutbuttons = null;
                }

                if (SwingUtilities.isRightMouseButton(e)){
                    if(atkPrimed){
                        switch (mousestep) {
                            //Target Enemy
                            case 0 -> {
                                System.out.println("target enemy");
                                if (sequence[0] && primedAtk.canHit(TargetTile, distance(TargetTile, CurrentPlayer.CurTile))) {
                                    primedAtk.addTarget(TargetTile.Occupant());
                                    primedAtk.setCharMove(TileGrid[CurrentPlayer.CurTile.y + primedAtk.CharMovey][CurrentPlayer.CurTile.x + primedAtk.CharMovex]);
                                }

                                if(primedAtk.name.equals("Climb") && primedAtk.canHit(TargetTile,distance(TargetTile, CurrentPlayer.CurTile))){
                                    primedAtk.addTarget(TargetTile);
                                }

                                System.out.println(primedAtk.gotTargets());

                                if (sequence[1] && primedAtk.gotTargets()) {
                                    mousestep++;
                                }
                                else if(sequence[2] && primedAtk.gotTargets()){
                                    mousestep+=2;
                                }
                                else if(primedAtk.gotTargets()){
                                    ExecuteAttack();
                                }

                            }
                            //Target Movement
                            case 1 -> {
                                System.out.println("target movement");
                                if (primedAtk.canTargetMove(TargetTile, distance(TargetTile, CurrentPlayer.CurTile))) {
                                    primedAtk.setTargetMove(TargetTile);
                                }

                                if(sequence[2] && primedAtk.targetMove !=null) {
                                    mousestep++;
                                }else if(primedAtk.targetMove !=null){
                                    ExecuteAttack();
                                }
                            }
                            //Character Movement
                            case 2 -> {
                                System.out.println("Character movement");
                                if(primedAtk.canCharMove(TargetTile)){
                                    primedAtk.setCharMove(TargetTile);
                                }

                                if(primedAtk.CharMove!=primedAtk.user.CurTile){
                                    ExecuteAttack();
                                }
                            }
                        }
                    }
                    else{
                        if (ValidMove(TargetTile, CurrentPlayer) && CurrentPlayer.state == 0) {
                            charMove(TargetTile);
                        }
                    }
                }
                else {
                    if (atkPrimed) {
                        primedAtk.emptyTargets();
                        atkPrimed = false;
                        primedAtk = null;
                        sequence = null;
                        mousestep = 0;
                    }
                    if (button != null && !button.active) {
                        button = null;
                    }
                    if (button != null && (button.action.type.equals("misc") || button.action.type.equals("reset") || button.action.type.equals("menu") || button.action.type.equals("kickout") )) {
                        strikes = null;
                        slams = null;
                        context = null;
                        button.action.Execute();
                        if(button.action.type.equals("kickout")){
                            reveal = true;
                            repaint();
                            changeTurn();


                        }
                        if (button.action.type.equals("misc")) {
                            changeTurn();
                        }
                    } else {
                        if (button == null) {
                            Selected = TargetTile;
                        } else if (button.action.canAfford()) {
                            primedAtk = button.action;
                            atkPrimed = true;
                            sequence = primedAtk.sequence;
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

        Thread loop = new Thread(this::loop, "loop");
        loop.start();
    }

    void ExecuteAttack(){
        primedAtk.Execute();
        mousestep = 0;
        primedAtk = null;
        atkPrimed = false;
        CalculatePaths();
    }

    static void resetGame(){
        start = true;
        TurnCounter = 0;
        pinCount = 0;
        Kickoutbuttons = null;
        resetButton[0] = null;
        ended = false;
        winText = "";
        CurrentPlayer = null;
        strikes = null;
        slams = null;
        context = null;
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
        CurrentPlayer.selfMove = true;
        Path movepath = t.path;
        CurrentPlayer.MovePoints = CurrentPlayer.MovePoints - movepath.cost;
        CurrentPlayer.setTile(t, movepath.Tiles);
        CurrentPlayer.moving = true;
        context = new Button[5];
        CalculatePaths();
    }


    TurnOrder findturnOrder(int x, int y){

        TurnOrder turn = null;
        for(TurnOrder t: turnOrder){
            if(x>t.lowx &&
               x<t.highx &&
               y>t.lowy &&
               y<t.highy){
                turn = t;
            }
        }

        return turn;
    }

    static int distance(Tile t1, Tile t2){
        return  Math.abs(t1.x-t2.x) + Math.abs(t1.y-t2.y);
    }

    Boolean ValidMove(Tile t, Character c) {

        return t.canMove() &&
                t.path != null &&
                t.path.cost <= c.MovePoints;

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

    static Path createPath(Tile t, Character c){

        //creates a movement path for the player character

        if(distance(t,c.CurTile)>c.MovePoints || !t.canMove()){
            return null;
        }
        ArrayList<Tile> Closed = new ArrayList<>();
        SortedList Open = new SortedList();

        int MaxSearch = 100;
        c.CurTile.cost = 0;
        Open.add(c.CurTile);
        Closed.clear();
        t.parent = null;

        int depth = 0;
        while(Open.size()!=0 && depth<MaxSearch){
            Tile current = Open.first();
            if(current == t){
                break;
            }
            Open.remove(current);
            Closed.add(current);
            for(Tile n: neighbourTiles(current)){
                if(n.canMove()) {
                    int nextStepCost = current.cost + distance(n, current);

                    if (nextStepCost < n.cost) {
                        if (Open.contains(n)) {
                            Open.remove(n);
                        }
                        Closed.remove(n);

                    }

                    if (!Open.contains(n) && !Closed.contains(n)) {
                        n.cost = nextStepCost;
                        depth = Math.max(depth, n.setParent(current));
                        n.heuristic = distance(n, t);
                        Open.add(n);
                    }
                }
            }

        }

        if(t.parent==null){
            return null;
        }

        Path path = new Path(t);
        path.cost = t.parent.cost + distance(t,t.parent);
        Tile target = t;
        while(target!=c.CurTile){
            path.prependStep(target);
            target = target.parent;
        }
        path.prependStep(c.CurTile);

        return path;

    }

    static void CalculatePaths(){
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++){
                TileGrid[r][c].path = createPath(TileGrid[r][c],CurrentPlayer);
            }
        }
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

    Character SpawnJon(int x, int y,String name, String team, Color colour, int or){
        return new Light(TileGrid[x][y], name, team, colour, or);
    }

    Character SpawnJak(int x, int y, String name, String team, Color Colour, int or){
        return new Heavy(TileGrid[x][y], name,team, Colour , or);
    };

    Character SpawnJay(int x, int y, String name, String team, Color Colour, int or){
        return new Medium(TileGrid[x][y],name, team, Colour, or);
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

        button = getButton(x,y,button,menu);
        button = getButton(x, y, button, strikes);
        button = getButton(x, y, button, slams);
        button = getButton(x,y,button,context);
        button = getButton(x, y, button, Kickoutbuttons);
        button = getButton(x,y,button,resetButton);





        return button;
    }

    private Button getButton(int x, int y, Button button, Button[] buttons) {
        if(buttons !=null && buttons[0]!=null) {
            for (Button b : buttons) {
                if(b!=null) {
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
        }
        return button;
    }

    void update(){
        if(running) {
            repaint();

            if(CurrentPlayer == null){
                CurrentPlayer = Characters[0];
            }


            if (CurrentPlayer != null && Characters[0]!=null) {
                if (MoveDelay == 0) {
                    for (Character i : Characters) {
                        if(i!=null) {
                            i.Update();
                        }
                    }
                    MoveDelay = 50000;
                } else {
                    MoveDelay--;
                }
                if ((CurrentPlayer.state == 0 && CurrentPlayer.MovePoints == 0) || CurrentPlayer.state == 2 || CurrentPlayer.state == 1) {
                    if(CurrentPlayer!=null) {
                        changeTurn();
                    }

                }
            }
        }
    }

    void endGame(){
        ended = true;
        menu = null;
        strikes = null;
        slams = null;
        context = null;
        winText = "Team " +CurrentPlayer.teamname + " Wins!";
        resetButton[0] = new Button(new resetGame(CurrentPlayer), 25, 90, (getWidth()/2) - (90/2),getHeight()/2);
        resetButton[0].active = true;
    }

    synchronized void changeTurn(){
        if(!ended) {
            rotateOrder();
        }
        strikes = null;
        slams = null;
        context = null;
        menu = null;
        createMenu();

        if(CurrentPlayer!=null) {
            if ((CurrentPlayer.state == 2 || CurrentPlayer.state == 3) && !ended) {
                pinCount++;
                PinCountUpdate = true;

            }

            if (pinCount >= 3) {
                endGame();
                return;
            }
            if (!ended) {
                TurnCounter++;
                if (TurnCounter == Characters.length) {

                    TurnCounter = 0;
                    CurrentPlayer = Characters[TurnCounter];
                }

                Characters[TurnCounter].resetTurn();
                CurrentPlayer = Characters[TurnCounter];


                CalculatePaths();
                switch (CurrentPlayer.state) {
                    case 0 -> {

                        createMenu();

                    }
                    case 1 -> {


                    }
                    case 2 -> {
                        changeTurn();


                        strikes = null;
                        slams = null;
                    }
                    case 3 -> {
                        createKickout();
                        slams = null;
                        strikes = null;
                        context = null;
                    }
                    default -> {

                    }
                }
            }
        }



    }

    static Action[] findContextual(){
        Action[] context = new Action[4];
        boolean pin = false;
        boolean climb = false;
        int i = 0;

       for(Tile t : neighbourTiles(CurrentPlayer.CurTile)){
           if((distance(t,CurrentPlayer.CurTile)==1 && t.Occupied() && t.Occupant().state==1 && !t.Occupant().teamname.equals(CurrentPlayer.teamname) && CurrentPlayer.state == 0 && !pinning()) && !pin){
               context[i] = new Pin(CurrentPlayer);
               pin = true;
               i++;
           }

           if((t.type < 5 || t.type == 7) && !climb){
               context[i] = new Climb(CurrentPlayer);
               climb = true;
               i++;
           }
       }

        if(CurrentPlayer.CurTile.type < 5){
           for(Action x: CurrentPlayer.dives){

               context[i] = x;
               i++;
           }
       }

        if(CurrentPlayer.CurTile.type == 7){
            for(Action x: CurrentPlayer.springs){
                context[i] = x;
                i++;
            }
        }



       return context;
    }

    void createKickout(){

        Kickoutbuttons = new Button[CurrentPlayer.MaxHealth - CurrentPlayer.Health];
        System.out.println(CurrentPlayer);
        int randomNum = ThreadLocalRandom.current().nextInt(0, (CurrentPlayer.MaxHealth - CurrentPlayer.Health));



        System.out.println("Kickout: " + randomNum);
        for(int i = 0;i<CurrentPlayer.MaxHealth - CurrentPlayer.Health;i++){
            if(i == randomNum){
                Kickoutbuttons[i] = new Button(new Kickout(CurrentPlayer), 50,50);
                Kickoutbuttons[i].active = true;
            }
           else{
                Kickoutbuttons[i] = new Button(new KickoutFail(CurrentPlayer), 50,50);
                Kickoutbuttons[i].active = true;
            }
        }


    }

    void createMenu(){
        menu = new Button[4];
        menu[0] = new Button(new Strikes(CurrentPlayer),25,90);
        menu[1] = new Button(new Slams(CurrentPlayer),25,90);
        menu[2] = new Button(new Context(CurrentPlayer),25,90);
        menu[3] = new Button(new EndTurn(CurrentPlayer),25,90);
    }

    static boolean pinning(){
        for(Character i: Characters){
            if(i.state == 2 || i.state == 3 ){
                return true;
            }
        }

        return false;
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

    public static BufferedImage rotateClockwise(BufferedImage src, double dir) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI * dir, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }

    public Character[] findDowned(){
        Character[] downed;
        downed = new Character[6];
        int index = 0;

        for(Character i: Characters){
            if(i.state==3 || i.state == 1){
                downed[index]=i;
                index++;
            }
        }

        return downed;
    }

    void rotateOrder(){
        TurnOrder[] newOrder = new TurnOrder[6];

        for(int i = 0; i<6;i++){
            if(i == 0){
                newOrder[5] = turnOrder[0];
            }
            else{
                newOrder[i - 1] = turnOrder[i];
            }
        }

        turnOrder = newOrder;
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void paintComponent(Graphics g) {
        Font font = new Font("Verdana", Font.PLAIN, 15);
        Font myFont = new Font("sans-serif", Font.BOLD,15);
        g.setFont(font);
        super.paintComponent(g);
        int width = getWidth() / COLS;
        int height = getHeight() / ROWS;
        int transparency = 40;
        Color underhalf = new Color(0,255,0, transparency);
        Color overhalf = new Color(248, 223, 2, transparency);
        Color all = new Color(246, 116, 0, transparency);
        Color grid = new Color(0, 0, 0, transparency);
        Color canHit = new Color(255,0,0, transparency);
        Color canMove = new Color(69, 147, 234, transparency);
        Color infoBox = new Color(64, 64, 64, 129);

        Tile[] ropeTiles = new Tile[40];
        int ropeindex = 0;
        String tileName;
        String tileOccupant;
        String tileAccess;
        String state;

        //TILE HIGHLIGHT

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int y = r * width;
                int x = c * height;
                BufferedImage TileImage = (BufferedImage) TileGrid[r][c].image;
                BufferedImage rotated;
                BufferedImage ropes = (BufferedImage) Tile.ROPEFG;
                BufferedImage rotropes = ropes;
                if(TileGrid[r][c].type == 7) {
                    if (c == 15) {
                        rotated = rotateClockwise(TileImage, 1);
                        TileImage = rotated;
                        rotropes = rotateClockwise(ropes,1);
                    }
                    if(r == 4){
                        rotated = rotateClockwise(TileImage,0.5);
                        TileImage = rotated;
                        rotropes = rotateClockwise(ropes,0.5);

                    }

                    if(r == 15){
                        rotated = rotateClockwise(TileImage,-0.5);
                        TileImage = rotated;
                        rotropes = rotateClockwise(ropes,-0.5);

                    }

                    TileGrid[r][c].rope = rotropes;
                    ropeTiles[ropeindex] = TileGrid[r][c];
                    ropeindex++;
                }
                g.drawImage(TileImage,x, y, width, height, null);
                g.setColor(grid);
                g.drawRect(x,y, width, height);
                if(CurrentPlayer!=null) {
                    if(!atkPrimed) {
                        if (ValidMove(TileGrid[r][c], CurrentPlayer) && CurrentPlayer.state == 0) {
                            if(TileGrid[r][c].path!=null) {
                                if (TileGrid[r][c].path.cost == (CurrentPlayer.MovePoints)) {
                                    g.setColor(all);
                                } else if (TileGrid[r][c].path.cost > (CurrentPlayer.MovePoints / 2)) {
                                    g.setColor(overhalf);
                                } else {
                                    g.setColor(underhalf);
                                }

                                g.fillRect(x, y, width, height);
                            }
                        }
                    }
                    if(TileGrid[r][c].Occupied() && TileGrid[r][c].Occupant() == CurrentPlayer){
                        g.setColor(Color.GREEN);
                        g.drawOval(x,y,40,40);
                    }

                }

                if(atkPrimed) {
                    Selected = null;
                    if (mousestep == 0 && primedAtk.canHit(TileGrid[r][c], distance(primedAtk.user.CurTile, TileGrid[r][c]))
                            && (TileGrid[r][c].Occupant() != null || primedAtk.type.equals("Context"))) {

                        g.setColor(canHit);
                        g.fillRect(x, y, width, height);
                    }

                    if (mousestep == 1) {
                        if (primedAtk.canTargetMove(TileGrid[r][c], distance(TileGrid[r][c], primedAtk.user.CurTile))) {

                            int val = 0;
                            if(primedAtk.exitmod()){
                                val = val + primedAtk.SlamExit(TileGrid[r][c],primedAtk.targets[0].CurTile);
                            }
                            if(primedAtk.entrymod()){
                                val = val + TileGrid[r][c].SlamEntryModifier;
                            }

                            if(val > 0){
                                g.setColor(Color.yellow);
                                g.setFont(new Font("sans-serif",Font.BOLD,15));

                                g.drawString("+" + val,x + TILE_SIZE/3,y + TILE_SIZE/2 );

                            }
                            g.setColor(canHit);
                            g.fillRect(x, y, width, height);
                        }
                    }
                    if (mousestep == 2) {
                        if (primedAtk.canCharMove(TileGrid[r][c])) {
                            g.setColor(canMove);
                            g.fillRect(x, y, width, height);
                        }
                    }
                }
                TileGrid[r][c].setCenter(x,y);
                TileGrid[r][c].setBounds(x,y,r,c);
            }
        }

        if(start){
            Characters[0] = SpawnJay(8, 10, "Texas Redd","Red", Color.red, 1);
            turnOrder[0] = new TurnOrder(Characters[0]);
            Characters[1] = SpawnJay(10, 11, "Tommy Bluford","Blue", Color.blue, 5);
            turnOrder[1] = new TurnOrder(Characters[1]);
            Characters[2] = SpawnJon(8,11,"Crimson Lightning","Red", Color.red,1);
            turnOrder[2] = new TurnOrder(Characters[2]);
            Characters[3] = SpawnJon(9, 11, "El Mono Azul", "Blue", Color.blue,5);
            turnOrder[3] = new TurnOrder(Characters[3]);
            Characters[4] = SpawnJak(6, 11, "Brock Redstone","Red", Color.red,1);
            turnOrder[4] = new TurnOrder(Characters[4]);
            Characters[5] = SpawnJak(7, 11, "Karl Kobalt","Blue", Color.blue,5);
            turnOrder[5] = new TurnOrder(Characters[5]);
            CurrentPlayer = Characters[0];
            CalculatePaths();
            for(Character i: Characters){
                i.orient(i.orientation);
                i.sprite.setImage(i.rotate((BufferedImage) i.sprites[0], i.rot));
            }
            createMenu();
            Kickoutbuttons = null;
            pinCount = 0;
            start = false;
            TurnCounter = 0;
            rotateOrder();
        }

        Character[] downed = findDowned();
        if(downed[0]!=null){
            for (Character character : downed) {
                if (character != null) {
                    character.draw((Graphics2D) g);
                } else {
                    break;
                }
            }
        }

        for(Tile t:ropeTiles){
            if(t!=null){
                g.drawImage(t.rope,t.CenterX,t.CenterY,TILE_SIZE,TILE_SIZE,null);
            }
        }

        for(Character i: Characters){
            if(i == CurrentPlayer){
                g.drawImage(Sprite.TurnInd,(int)i.x + ((TILE_SIZE/2) - 10),(int)i.y - 20,20,20, null);
            }
            if(i.state!=3 && i.state!=1) {
                i.draw((Graphics2D) g);
            }
        }

        //SELECTED TILE

        if(Selected!=null){
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.blue);
            g2.drawRect(Selected.AccX,Selected.AccY,width,height);
            g2.setStroke(new BasicStroke(1));

            tileName = Selected.name;
            if(Selected.Occupant()!=null){
                hoverC = Selected.Occupant();
                hoverA = null;

                int Xhbars = (Selected.CenterX + (TILE_SIZE / 2) - 5) - ((((Selected.Occupant().healthBar.length * 10) + (Selected.Occupant().healthBar.length - 1))/2) - 5)  ;
                int Yhbars = Selected.CenterY - TILE_SIZE;

                int Xsbars = (Selected.CenterX + (TILE_SIZE / 2) - 5)  - ((((Selected.Occupant().staminaBar.length * 10) + (Selected.Occupant().staminaBar.length - 1))/2) - 5)  ;
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

            g.setColor(Color.white);

            int texty = getHeight() - (TILE_SIZE * 4) - font.getSize() * 2;
            g.drawString(Selected.toString(), TILE_SIZE / 2,texty);
            texty = texty + font.getSize();
            g.drawString(tileName, TILE_SIZE / 2,texty );
            texty = texty + font.getSize();
            g.drawString("+" + Selected.SlamEntryModifier + " damage to slams on this tile",TILE_SIZE / 2,texty);
            texty = texty + font.getSize();
            switch(Selected.type){
                case 7 -> {
                    g.drawString("+" + Selected.SlamExitModifier + " damage to targets moved to ground tiles",TILE_SIZE / 2,texty);
                    texty = texty + font.getSize();
                }
                case 1,2,3,4 -> {
                    g.drawString("+" + Selected.SlamExitModifier + " damage to targets moved from this tile",TILE_SIZE / 2,texty);
                    texty = texty + font.getSize();
                }
                case 8 -> {
                    g.drawString("+" + Selected.SlamExitModifier + " damage to targets moved to a non-canvas tile",TILE_SIZE / 2,texty);
                    texty = texty + font.getSize();
                }
            }


            g.drawString(tileAccess, TILE_SIZE / 2,texty);
        }

        if(CurrentPlayer.state == 0) {
            int menux = 10;
            int menuy = getHeight() - (((menu[0].height) * 4) + 10);


            DrawMenu(g, menux, menuy, menu);

            int subMenux = 10 + menu[0].width + 5;
            int subMenuy = menuy;

            switch (MenuSelect) {
                case 0 -> {
                    strikes = null;
                    slams = null;
                    context = null;
                }
                case 1 -> {
                    slams = null;
                    context = null;
                    if (strikes != null) {
                        DrawMenu(g, subMenux, subMenuy, strikes);
                    }
                }
                case 2 -> {
                    strikes = null;
                    context = null;
                    if (slams != null) {
                        DrawMenu(g, subMenux, subMenuy, slams);
                    }
                }
                case 3 -> {
                    strikes = null;
                    slams = null;
                    if (context != null) {
                        DrawMenu(g, subMenux, subMenuy, context);
                    }
                }
            }
        }

        //  INFORMATION BOX

        int infoBoxx = 250;
        int infoBoxy = getHeight() - 115;
        int textY = infoBoxy + 55;
        int textX = infoBoxx + 10;
        int maxWidth = 420;
        int curWidth = 0;
        int statTextx = infoBoxx + 10;
        int statTexty = infoBoxy + 20;

        g.setColor(infoBox);
        g.fillRect(infoBoxx, infoBoxy,510,110);
        g.setColor(Color.white);
        Font infoFont = new Font("sans-serif", Font.PLAIN,15);
        g.setFont(infoFont);
        if(hoverA!=null) {
            if (hoverA.range != null) {
                StringBuilder strb = new StringBuilder();
                strb.append("Range: ");

                for (int r = 0; r < hoverA.range.length; r++) {
                    strb.append(hoverA.range[r]);
                    if (r != hoverA.range.length - 1) {
                        strb.append("/");
                    }
                }

                g.drawString(String.valueOf(strb), statTextx, statTexty);
                statTextx = statTextx + g.getFontMetrics().stringWidth(String.valueOf(strb) + 5);
            }
            if(hoverA.dmg>0){

                g.drawString("Damage: " + hoverA.dmg,statTextx,statTexty);
                statTextx = statTextx + g.getFontMetrics().stringWidth("Damage: " + hoverA.dmg) + 5;
                if(hoverA.finisher){
                    g.setColor(Color.yellow);
                    g.drawString("+" + hoverA.user.strikemod,statTextx,statTexty);
                    statTextx = statTextx + g.getFontMetrics().stringWidth("+" + hoverA.user.strikemod + 5);
                    g.setColor(Color.white);
                }
                g.setColor(Color.white);
            }

            if(hoverA.stmdmg > 0){
                g.drawString("Stamina Damage: " + hoverA.stmdmg,statTextx,statTexty);
                statTextx = statTextx + g.getFontMetrics().stringWidth("Stamina Damage: " + hoverA.stmdmg + 5);
            }

            if(hoverA.cost>0){
                g.drawString("Cost: " + hoverA.cost,statTextx,statTexty);
                statTextx = statTextx + g.getFontMetrics().stringWidth("Cost: " + hoverA.cost + 5);
                if(hoverA.type.equals("Slam")){
                    g.setColor(new Color(138,43,226));
                    g.drawString("+" + hoverA.user.slammod,statTextx,statTexty);
                    statTextx = statTextx + g.getFontMetrics().stringWidth("+" + hoverA.user.strikemod + 5);
                    g.setColor(Color.white);
                }
            }


            if (hoverA.desc != null) {
                String[] descsplit = hoverA.desc.split(" ");
                for (String str : descsplit) {
                    int strwidth = g.getFontMetrics().stringWidth(str);
                    curWidth = curWidth + strwidth;
                    if (curWidth > maxWidth) {
                        curWidth = 0;
                        textY = textY + 15;
                        textX = infoBoxx + 10;
                    }
                    if(str.equals("Strike") ||
                       str.equals("combo")  ||
                       str.equals("finisher.")){
                        g.setColor(Color.yellow);
                    }
                    else if(isNumeric(str)){
                        g.setColor(Color.GREEN);
                    }
                    else{
                        g.setColor(Color.white);
                    }


                    g.drawString(str, textX, textY);
                    textX = textX + strwidth + 5;

                }
            }
        }

        if(hoverC!=null){
            g.drawString(hoverC.name + " (" + hoverC.states[hoverC.state] + ")", statTextx,statTexty);
            statTexty = statTexty + 30;
            g.drawString("Health: " + hoverC.Health,statTextx,statTexty);
            statTexty = statTexty + 20;
            g.drawString("Max Health: " + hoverC.MaxHealth,statTextx,statTexty);
            statTexty = statTexty + 20;
            g.drawString("Stamina: " + hoverC.MovePoints,statTextx,statTexty);
            statTextx = statTextx + 100;
            statTexty = infoBoxy + 50;
            g.drawString("Max Stamina: " + hoverC.MaxMove,statTextx,statTexty);
            statTexty = statTexty + 20;
            g.drawString("HP regeneration: " + hoverC.regen, statTextx,statTexty);
            statTexty = statTexty + 20;
            g.drawString("Stamina regeneration: " + hoverC.stamregen,statTextx,statTexty);

            statTextx = statTextx + 200;
            statTexty = statTexty = infoBoxy;
            g.drawImage(hoverC.sprite.image, statTextx,statTexty,null);

        }

        if(Kickoutbuttons!=null) {
            int kowidth = (Kickoutbuttons.length * Kickoutbuttons[0].width) + (30 * (Kickoutbuttons.length - 1));
            drawButtons(g,(getWidth()/2) - (kowidth/2) , getHeight() - 200, Kickoutbuttons);
        }

        if(resetButton[0]!=null){
            Button reset = resetButton[0];


            g.setColor(Color.black);
            g.fillRect(reset.x1,reset.y1,reset.width,reset.height);
            g.setColor(Color.white);
            g.drawString("Reset",reset.x1 + (reset.width/2) - (g.getFontMetrics().stringWidth("Reset")/2),reset.y1 +
                    ((reset.height / 2) + g.getFontMetrics().getHeight()/4));


        }

        //PIN COUNTER

        if(pinCount>0) {
            Color col = null;
            g.setFont(new Font("sans-serif", Font.BOLD, 90));
            g.drawString(winText, (getWidth() / 2) - (g.getFontMetrics().stringWidth(winText)/2), (getHeight()/2) - (g.getFontMetrics().getHeight()/2));
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString("Pin Count:", 20, 20);
            int Pstrwidth = g.getFontMetrics().stringWidth("Pin Count:");
            g.setFont(new Font("sans-serif", Font.BOLD, 50));
            switch (pinCount) {
                case 1 -> {
                    col = new Color(255,255,255);
                    g.setColor(col);
                    g.drawString(String.valueOf(pinCount), (Pstrwidth + 10) / 2, 70);
                }
                case 2 -> {
                    col = new Color(255, 234, 0);
                    g.setColor(col);
                    g.drawString(String.valueOf(pinCount), (Pstrwidth + 10) / 2, 70);
                }
                case 3 -> {
                    col = new Color(212, 95, 10);
                    g.setColor(col);
                    g.drawString(String.valueOf(pinCount), (Pstrwidth + 10) / 2, 70);
                }

            }

            if(PinCountUpdate){
                transparencyVal = 100;
                PinCountUpdate = false;
            }

            g.setFont(new Font("sans-serif",Font.BOLD,200));
            assert col != null;
            g.setColor(new Color(col.getRed(),col.getGreen(),col.getBlue(),transparencyVal));
            g.drawString(String.valueOf(pinCount),(getWidth()/2) - (g.getFontMetrics().stringWidth(String.valueOf(pinCount))/2),(getHeight()/2) - 50) ;

            if(transparencyVal> 0){
                transparencyVal--;
            }


            g.setFont(font);
        }


        //TURN ORDER

        if(turnOrder!=null){
            int TOx = getWidth() - turnOrder[0].width;
            int TOy = (getHeight()/2) - ((turnOrder.length * turnOrder[0].height) / 2);

            g.setFont(myFont);
            g.setColor(Color.white);
            g.drawString("NEXT",TOx + (turnOrder[0].width/4),TOy - 10);

            for(TurnOrder t:turnOrder){
                t.draw(g,TOx,TOy);
                t.setBounds(TOx,TOy);
                TOy = TOy + t.height + 10;
            }
        }

    }

    private void DrawMenu(Graphics g, int menux, int menuy, Button[] buttons) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        Color TeamCol;
        if(CurrentPlayer.teamname.equals("Blue")){
            TeamCol = Color.blue;
        }
        else{
            TeamCol = Color.red;
        }



        for(Button b: buttons){
            if(b!=null) {
                b.active = true;
                if(b.action.canAfford()){
                    g.setColor(TeamCol);
                }
                else{
                    g.setColor(Color.gray);
                }

                b.setX(menux);
                b.setY(menuy);
                if (b.action.name.equals("Context")) {
                    if (findContextual()[0] == null) {
                        g.setColor(Color.gray);
                        b.active = false;
                    } else {
                        b.active = true;
                    }
                }
                g.fillRect(menux, menuy, b.width, b.height);

                String name = b.action.name;
                FontMetrics fm = g.getFontMetrics();
                int sWidth = fm.stringWidth(name);
                int sHeight = fm.getHeight();

                g.setColor(Color.white);

                g.drawString(b.action.name, menux + (b.width / 2) - (sWidth / 2), menuy + ((b.height / 2) + sHeight / 4));

                if(b.action.finisher) {
                    g.setColor(Color.yellow);
                    Font myFont = new Font("sans-serif", Font.BOLD, 15);
                    g.setFont(myFont);
                    g.drawString("+" + b.action.user.strikemod, menux + (b.width - 5), menuy + 10);
                    Font font = new Font("Verdana", Font.PLAIN, 15);
                    g.setFont(font);
                }

                if(b.action.type.equals("Slam")){
                    g.setColor(new Color(138,43,226));
                    Font myFont = new Font("sans-serif", Font.BOLD, 15);
                    g.setFont(myFont);
                    g.drawString("-" + b.action.user.slammod, menux + (b.width - 5), menuy + 10);
                    Font font = new Font("Verdana", Font.PLAIN, 15);
                    g.setFont(font);
                }

                menuy += b.height + 2;
            }
        }




    }

    private void drawButtons(Graphics g, int x, int y, Button[] buttons) {



        for(Button b: buttons){
            b.setX(x);
            b.setY(y);
            g.setColor(Color.white);
            if(reveal){
                switch(b.action.name){
                    case "pass" -> {
                        g.drawImage(Sprite.KickoutPass,b.x1,b.y1,b.width,b.height,null);
                    }
                    case "fail" -> {
                        g.drawImage(Sprite.KickoutFail,b.x1,b.y1,b.width,b.height,null);
                    }
                }
            }
            else {
                g.drawImage(Sprite.KickoutGuess, b.x1, b.y1, b.width, b.height, null);
            }

            x += (buttons[0].width + 30);
        }
    }
}
