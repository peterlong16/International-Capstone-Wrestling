package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static game.Constants.*;

public class Map extends JPanel {


    static boolean running = true;
    static boolean start = true;
    boolean atkPrimed = false;
    boolean moverPrimed = false;
    boolean i = true;
    static boolean ended = false;
    static int MenuSelect = 0;


    //0 = none, 1= strikes, 2= slams, 3 = context

    Action primedAtk;

    static int TurnCounter = 0;
    int MoveDelay = 100000;
    static int pinCount = 0;

    static Character CurrentPlayer;
    static Character[] Characters = new Character[6];
    public static final Tile[][] TileGrid = new Tile[ROWS][COLS];
    Tile Selected;
    Button[] menu;
    static Button[] strikes;
    static Button[] slams;
    static Button[] context;
    Button[] Kickoutbuttons;
    static String winText = "";
    Button exitButton;
    Boolean[] sequence;
    int mousestep = 0;
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
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                Tile TargetTile = FindTile(e.getX(), e.getY());
                Button button = findButton(e.getX(),e.getY());

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
                    if (button != null && (button.action.type.equals("misc") || button.action.type.equals("reset") || button.action.type.equals("menu"))) {
                        strikes = null;
                        slams = null;
                        context = null;
                        button.action.Execute();
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
    }

    static void resetGame(){
        start = true;
        TurnCounter = 0;
        pinCount = 0;
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
        CurrentPlayer.setTile(t, createPath(t,CurrentPlayer));
        CurrentPlayer.MovePoints = CurrentPlayer.MovePoints - cost;
        CurrentPlayer.moving = true;
        context = new Button[1];
    }

    static int distance(Tile t1, Tile t2){
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

    Character SpawnJon(int x, int y,String name, String team, Color colour){
        return new Light(TileGrid[x][y], name, team, colour);
    }

    Character SpawnJak(int x, int y, String name, String team, Color Colour){
        return new Heavy(TileGrid[x][y], name,team, Colour);
    };

    Character SpawnJay(int x, int y, String name, String team, Color Colour){
        return new Medium(TileGrid[x][y],name, team, Colour);
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
                if (CurrentPlayer.MovePoints <= 0 || CurrentPlayer.state == 2) {

                    changeTurn();

                }
            }
        }
    }

    void endGame(){
        ended = true;
        Kickoutbuttons = null;
        menu = null;
        strikes = null;
        slams = null;
        context = null;
        winText = CurrentPlayer.teamname + " wins!";
        resetButton[0] = new Button(new resetGame(CurrentPlayer), 40, 50, getWidth()/2,getHeight()/2);
        resetButton[0].active = true;
    }

    void changeTurn(){
        strikes = null;
        slams = null;
        context = null;
        menu = null;
        createMenu();

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
                    createMenu();

                }
                case 1 -> {
                    changeTurn();

                    Kickoutbuttons = null;
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

    static Action[] findContextual(){
        Action[] context = new Action[2];
        int i = 0;

       for(Tile t : neighbourTiles(CurrentPlayer.CurTile)){
           if(distance(t,CurrentPlayer.CurTile)==1 && t.Occupied() && t.Occupant().state==1 && !t.Occupant().teamname.equals(CurrentPlayer.teamname) && CurrentPlayer.state == 0 && !pinning()){
               context[i] = new Pin(CurrentPlayer);
               i++;
           }
        }



       return context;
    }

    void createKickout(){

        Kickoutbuttons = new Button[CurrentPlayer.MaxHealth - CurrentPlayer.Health];

        int randomNum = ThreadLocalRandom.current().nextInt(0, (CurrentPlayer.MaxHealth - CurrentPlayer.Health));


        System.out.println("Kickout: " + randomNum);
        for(int i = 0;i<CurrentPlayer.MaxHealth - CurrentPlayer.Health;i++){
            if(i == randomNum){
                Kickoutbuttons[i] = new Button(new Kickout(CurrentPlayer), 40,40);
                Kickoutbuttons[i].active = true;
            }
           else{
                Kickoutbuttons[i] = new Button(new KickoutFail(CurrentPlayer), 40,40);
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

    public Character[] findPinned(){
        Character[] pinned;
        pinned = new Character[6];
        int index = 0;

        for(Character i: Characters){
            if(i.state==3){
                pinned[index]=i;
                index++;
            }
        }

        return pinned;
    }

    public void paintComponent(Graphics g) {
        Font font = new Font("Verdana", Font.PLAIN, 15);
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

        String tileName;
        String tileOccupant;
        String tileAccess;
        String state;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int y = r * width;
                int x = c * height;
                BufferedImage TileImage = (BufferedImage) TileGrid[r][c].image;
                BufferedImage rotated;
                if(TileGrid[r][c].type == 7) {
                    if (c == 15) {
                        rotated = rotateClockwise(TileImage, 1);
                        TileImage = rotated;
                    }
                    if(r == 4){
                        rotated = rotateClockwise(TileImage,0.5);
                        TileImage = rotated;
                    }

                    if(r == 15){
                        rotated = rotateClockwise(TileImage,-0.5);
                        TileImage = rotated;
                    }

                }
                g.drawImage(TileImage,x, y, width, height, null);
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
                    if(TileGrid[r][c].Occupied() && TileGrid[r][c].Occupant() == CurrentPlayer){
                        g.setColor(Color.GREEN);
                        g.drawOval(x,y,40,40);
                    }
                }

                if(atkPrimed) {
                    Selected = null;
                    if (mousestep == 0 && primedAtk.canHit(TileGrid[r][c], distance(primedAtk.user.CurTile, TileGrid[r][c]))
                            && TileGrid[r][c].Occupant() != null) {

                        g.setColor(canHit);
                        g.fillRect(x, y, width, height);
                    }

                    if (mousestep == 1) {
                        if (primedAtk.canTargetMove(TileGrid[r][c], distance(TileGrid[r][c], primedAtk.user.CurTile))) {
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
        tileOccupant = "";
        state = "";

        if(start){
            Characters[0] = SpawnJay(10, 9, "red2","red", Color.red);
            Characters[1] = SpawnJay(10, 8, "blue2","blue", Color.blue);
            Characters[2] = SpawnJon(6,6,"red1","red", Color.red);
            Characters[3] = SpawnJon(13, 13, "blue1", "blue", Color.blue);
            Characters[4] = SpawnJak(5, 7, "red3","red", Color.red);
            Characters[5] = SpawnJak(14, 12, "blue3","blue", Color.blue);
            CurrentPlayer = Characters[0];
            createMenu();
            Kickoutbuttons = null;
            pinCount = 0;
            start = false;
            TurnCounter = 0;
        }

        Character[] pinned = findPinned();
        if(pinned[0]!=null){
            for (Character character : pinned) {
                if (character != null) {
                    character.draw((Graphics2D) g);
                } else {
                    break;
                }
            }
        }
        for(Character i: Characters){
            if(i.state!=3) {
                i.draw((Graphics2D) g);
            }
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
            g.drawString(Selected.toString(), TILE_SIZE / 2,getHeight() - (TILE_SIZE * 6) - font.getSize() * 2 );
            g.drawString(tileOccupant + "(" + state + ")", TILE_SIZE / 2,getHeight() - (TILE_SIZE * 6) - font.getSize() );
            g.drawString(tileName, TILE_SIZE / 2,getHeight() - TILE_SIZE * 6 );
            g.drawString(tileAccess, TILE_SIZE / 2,getHeight() - (TILE_SIZE * 6) + font.getSize());
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



        if(Kickoutbuttons!=null) {
            drawButtons(g, ((getWidth()) / 2) - (Kickoutbuttons.length * Kickoutbuttons[0].width) , getHeight() - ((Kickoutbuttons[0].height) + 10), 30, Kickoutbuttons);
        }

        if(resetButton[0]!=null){
            Button reset = resetButton[0];


            g.setColor(Color.black);
            g.fillRect(reset.x1,reset.y1,reset.width,reset.height);

        }

        g.drawString(winText, getWidth()/2,20);
        g.drawString(CurrentPlayer.toString(), 100,30);
        int namex = 200;
        int namey = 50;
        g.drawString("Player order: ", 100,50);
        for(Character i: Characters){
            g.drawString(i.name + " ",namex,namey);
            namex += 40;
        }
        g.drawString("Pin count : " + pinCount,100,70);
        g.drawString("Turn counter : " + TurnCounter,100, 90);

    }

    private void DrawMenu(Graphics g, int menux, int menuy, Button[] buttons) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        Color TeamCol;
        if(CurrentPlayer.teamname.equals("blue")){
            TeamCol = Color.blue;
        }
        else{
            TeamCol = Color.red;
        }



        for(Button b: buttons){
            if(b!=null) {
                b.active = true;
                g.setColor(TeamCol);
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


                menuy += b.height + 2;
            }
        }




    }

    private void drawButtons(Graphics g, int x, int y, int gap, Button[] buttons) {

        for(Button b: buttons){
            b.setX(x);
            b.setY(y);
            g.setColor(Color.black);
            g.fillRect(b.x1,b.y1,b.width,b.height);

            x += (buttons[0].width + gap);
        }
    }
}
