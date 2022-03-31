package game;
import Utilities.ImageManager;
import Utilities.SortedList;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

import static game.Constants.*;

public class Map extends JPanel {


    static int HYPE_STAGE_1 = 25;
    static int HYPE_STAGE_2 = 40;
    static int MAX_HYPE = 50;

    static boolean running = true;
    static boolean start = true;
    boolean atkPrimed = false;
    boolean i = true;
    boolean PinCountUpdate = false;
    boolean HoverHype = false;
    static boolean ended = false;
    static int MenuSelect = 0;
    static boolean reveal;
    static boolean settingsMenu = false;
    static boolean howToPlay = false;
    static boolean muted = false;
    static boolean debug = false;
    TextBox[] helps;

    int HypeBarWidth = 400;
    int HypeX = getWidth() + (HypeBarWidth/2);
    int HypeY = 10;

    int refPenalty = 15;



    //0 = none, 1= strikes, 2= slams, 3 = context

    Action primedAtk;

    static int TurnCounter = 0;
    int MoveDelay = 100000;
    static int pinCount = 0;


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
    Boolean[] sequence;
    int mousestep = 0;
    static Button[] resetButton = new Button[1];
    static Button[] settingsButton = new Button[1];
    static Button[] SMenuButtons = new Button[4];
    static Button[] closeHelp = new Button[1];
    Action hoverA;
    Character hoverC;
    static Referee ref;
    int transparencyVal = 0;
    static int crowd = 0;
    static boolean stage1 = false;
    static boolean stage2 = false;
    static boolean stage3 = false;
    static ambientCrowd crowdSounds;
    static Impacts impactSounds;
    static miscSounds otherSounds;




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
            6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 1, 7, 7, 7, 7, 7, 7, 7, 7, 2, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 4, 5, 5, 5, 5, 6,
            6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6,
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
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_Q){
                    debug = !debug;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Button hoverbut = findButton(e.getX(),e.getY());
                TurnOrder turn = findturnOrder(e.getX(),e.getY());

                if(hoverbut!=null && hoverbut.action!=null && hoverbut.active){
                    hoverA = hoverbut.action;
                    Selected = null;
                    hoverC = null;
                    HoverHype = false;
                }

                if(turn!=null){
                    hoverC = turn.c;
                    Selected = hoverC.CurTile;
                    hoverA = null;
                    HoverHype = false;
                }

                if(e.getX() > HypeX &&
                   e.getX() < HypeX + HypeBarWidth &&
                   e.getY() > HypeY &&
                  e.getY() < HypeY + 20){

                    HoverHype = true;
                    hoverC = null;
                    hoverA = null;
                    Selected = null;
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
                    if(atkPrimed) {

                            switch (mousestep) {
                                //Target Enemy
                                case 0 -> {


                                    if (sequence[0] && primedAtk.canHit(TargetTile, distance(TargetTile, CurrentPlayer.CurTile))) {
                                        primedAtk.addTarget(TargetTile.Occupant());

                                        primedAtk.setCharMove(TileGrid[CurrentPlayer.CurTile.y + primedAtk.CharMovey][CurrentPlayer.CurTile.x + primedAtk.CharMovex]);
                                    }

                                    if (primedAtk.name.equals("Climb") && primedAtk.canHit(TargetTile, distance(TargetTile, CurrentPlayer.CurTile))) {
                                        primedAtk.addTarget(TargetTile);
                                    }



                                    if (sequence[1] && primedAtk.gotTargets()) {
                                        mousestep++;
                                    } else if (sequence[2] && primedAtk.gotTargets()) {
                                        mousestep += 2;
                                    } else if (primedAtk.gotTargets()) {
                                        ExecuteAttack();
                                    }

                                }
                                //Target Movement
                                case 1 -> {

                                    if (primedAtk.canTargetMove(TargetTile, distance(TargetTile, CurrentPlayer.CurTile))) {
                                        primedAtk.setTargetMove(TargetTile);
                                    }

                                    if (sequence[2] && primedAtk.targetMove != null) {
                                        mousestep++;
                                    } else if (primedAtk.targetMove != null) {
                                        ExecuteAttack();
                                    }
                                }
                                //Character Movement
                                case 2 -> {

                                    if (primedAtk.canCharMove(TargetTile)) {
                                        primedAtk.setCharMove(TargetTile);
                                    }

                                    if (primedAtk.CharMove != primedAtk.user.CurTile) {
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
                            if (!sequence[0] && !sequence[1] && !sequence[2]) {
                                ExecuteAttack();
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

        crowdSounds = new ambientCrowd();
        impactSounds = new Impacts();
        otherSounds = new miscSounds();
        crowdSounds.play();
        Thread loop = new Thread(this::loop, "loop");
        loop.start();
        howToPlay = true;
    }

    static void ChangeHype(int change){
       crowd = crowd + change;
       UpdateCrowd();
    }

    static void UpdateCrowd(){
        int posCrowd = Math.abs(crowd);

        if(posCrowd >= MAX_HYPE){
            stage1 = true;
            stage2 = true;
            stage3 = true;
        }
        else if(posCrowd >= HYPE_STAGE_2){
            stage1 = true;
            stage2 = true;
            stage3 = false;
        }
        else if(posCrowd >= HYPE_STAGE_1){
            stage1 = true;
            stage2 = false;
            stage3 = false;
        }
        else{
            stage1 = false;
            stage2 = false;
            stage3 = false;
        }

        if(stage3){

            crowdSounds.change(3);
        }
        else if(stage2){

            crowdSounds.change(2);
        }
        else if(stage1){

            crowdSounds.change(1);
        }
        else{
            crowdSounds.change(0);
        }

        crowdSounds.play();


        String crowdFavourite = "";
        if(crowd < 0){
            crowdFavourite = "Red";
        }
        else if(crowd > 0){
            crowdFavourite = "Blue";
        }

            for(Character c: Characters){

                if(c.teamname.equals(crowdFavourite)){
                    if(stage1){
                        c.stamregen = c.DEFAULT_MAX_STAMREGEN + 2;
                        c.regen = c.DEFAULT_MAX_HEALTHREGEN + 1;
                    }
                    else{
                        c.stamregen = c.DEFAULT_MAX_STAMREGEN;
                        c.regen = c.DEFAULT_MAX_HEALTHREGEN;
                    }
                    if(stage2){
                        c.MaxMove = c.DEFAULT_MAX_STAMINA + 2;
                        c.MaxHealth = c.DEFAULT_MAX_HEALTH + 2;
                    }
                    else{
                        c.MaxMove = c.DEFAULT_MAX_STAMINA;
                        c.MaxHealth = c.DEFAULT_MAX_HEALTH;
                    }
                    if(stage3){
                        c.signature = true;
                    }
                    else{
                        c.signature = false;
                    }
                }
                else{
                    c.stamregen = c.DEFAULT_MAX_STAMREGEN;
                    c.regen = c.DEFAULT_MAX_HEALTHREGEN;
                    c.MaxMove = c.DEFAULT_MAX_STAMINA;
                    c.MaxHealth = c.DEFAULT_MAX_HEALTH;
                    c.signature = false;
                    c.changeHealth(0);
                }
            }

    }

    static Tile GetClosest(Tile dest,Tile source){
        //returns closest tile to source in direction of dest
        int dist = 100;
        Tile closest = dest;
        for(Tile t:Map.neighbourTiles(source)){
            if(Map.distance(t,dest) < dist){
                dist = Map.distance(t,dest);
                closest = t;
            }
        }

        return closest;
    }

    void ExecuteAttack(){
        boolean refhit=false;
        for(Character t: primedAtk.targets){
            if(t==ref){
                if(primedAtk.hype > 0){
                    ChangeHype(-refPenalty);
                }
                else{
                    ChangeHype(refPenalty);
                }
                refhit = true;
            };
        }
        primedAtk.Execute();
        if(!refhit) {
            ChangeHype(primedAtk.hype);
        }
        mousestep = 0;
        primedAtk = null;
        atkPrimed = false;
        if(ref.state==0) {
            ref.TilesinView();
        }
        context = null;
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
        crowd = 0;
        settingsMenu = false;
        crowdSounds.change(0);

    }

    void charMove(Tile t){
        //Moves the current player to their selected tile
        CurrentPlayer.selfMove = true;
        Path movepath = t.path;


        CurrentPlayer.MovePoints = CurrentPlayer.MovePoints - movepath.cost;
        CurrentPlayer.setTile(t, movepath.Tiles);
        CurrentPlayer.moving = true;
        context = new Button[5];

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
        }else {

            Path path = new Path(t);
            path.cost = t.parent.cost + distance(t, t.parent);
            Tile target = t.parent;
            while (target != c.CurTile) {
                path.prependStep(target);
                if(target!=null) {
                    target = target.parent;
                }

            }
            path.prependStep(c.CurTile);

            return path;
        }

    }

    static void CalculatePaths(){
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++){

                    TileGrid[r][c].path = createPath(TileGrid[r][c], CurrentPlayer);

            }
        }
    }

    static ArrayList<Tile> neighbourTiles(Tile t){

        //returns a list of tiles surrounding a given tile;

        ArrayList<Tile> neighbours = new ArrayList<>();
        if(t.x!=COLS-1) {
            neighbours.add(TileGrid[t.y][t.x + 1]);
        }

        if(t.x!=COLS-1 && t.y!=ROWS-1) {
            neighbours.add(TileGrid[t.y + 1][t.x + 1]);
        }

        if(t.y!=ROWS-1) {
            neighbours.add(TileGrid[t.y + 1][t.x]);
        }

        if(t.x!=0 && t.y!=ROWS-1) {
            neighbours.add(TileGrid[t.y + 1][t.x - 1]);
        }

        if(t.x!=0) {
            neighbours.add(TileGrid[t.y][t.x - 1]);
        }

        if(t.x!=0 && t.y!=0){
            neighbours.add(TileGrid[t.y - 1][t.x-1]);
        }

        if(t.y != 0){
            neighbours.add(TileGrid[t.y - 1][t.x]);
        }

        if(t.y!=0 && t.x!=COLS-1){
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
        button = getButton(x,y,button,settingsButton);
        button = getButton(x,y,button,SMenuButtons);
        button = getButton(x,y,button,closeHelp);
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

    static public void MuteAudio(){
        crowdSounds.setVolume(0);
        impactSounds.setVolume(0);
        otherSounds.setVolume(0);
        muted = true;
    }

    static public void UnMuteAudio(){
        crowdSounds.setVolume(0.1f);
        impactSounds.setVolume(0.2f);
        otherSounds.setVolume(0.2f);
        muted = false;
    }

    void update(){
        if(running) {
            repaint();

            if(CurrentPlayer == null){
                CurrentPlayer = Characters[0];
            }


            if(!ended) {
                if (CurrentPlayer != null && Characters[0] != null && ref != null) {
                    if (MoveDelay == 0) {
                        for (Character i : Characters) {
                            if (i != null) {
                                i.Update();
                            }
                        }
                        if (ref != null) {
                            ref.Update();
                        }
                        MoveDelay = 40000;
                    } else {
                        MoveDelay--;
                    }
                    if ((CurrentPlayer.state == 0 && CurrentPlayer.MovePoints == 0) || CurrentPlayer.state == 2 || CurrentPlayer.state == 1) {
                        if (CurrentPlayer != null && !CurrentPlayer.attacking) {
                            changeTurn();
                        }

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
        ref.resetTurn();
        if(pinning() && ref.state!=1){
            ref.pin();
            ref.TilesinView();
        }
        else if(!pinning() && ref.state!=1){
            ref.state=0;
            ref.changeHealth(0);
        }
        if(ref.state == 0) {
            ref.setTile(ref.findMovement());
            ref.orientation = ref.TowardMost();
            ref.orient(ref.orientation);
            ref.TilesinView();
            ref.moving = true;
        }

        if(CurrentPlayer!=null) {
            if(ref.state == 1){
                pinCount = 0;
            }
            if ((CurrentPlayer.state == 2 || CurrentPlayer.state == 3) && ref.state!=1 && !ended) {
                pinCount++;
                PinCountUpdate = true;

            }

            if (pinCount >= 3) {
                otherSounds.change(1);
                otherSounds.play();
                endGame();
                return;
            }
            if (!ended) {
                TurnCounter++;

                if (TurnCounter == Characters.length) {

                    TurnCounter = 0;
                    Characters[TurnCounter].resetTurn();
                    CurrentPlayer = Characters[TurnCounter];
                }
                else{
                    Characters[TurnCounter].resetTurn();
                    CurrentPlayer = Characters[TurnCounter];
                }



                if(ref.state == 0) {
                    ref.TilesinView();
                }
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
        context[i] = new Taunt(CurrentPlayer);



       return context;
    }

    void createKickout(){
        int amount = CurrentPlayer.MaxHealth - CurrentPlayer.Health;
        int size = 50;
        if(amount == 0){
            amount = 1;
        }
        if(amount >= 10){
            size = 25;
        }
        Kickoutbuttons = new Button[amount];

        int randomNum = ThreadLocalRandom.current().nextInt(0, (amount));


        for(int i = 0;i<amount;i++){
            if(i == randomNum){
                Kickoutbuttons[i] = new Button(new Kickout(CurrentPlayer), size,size);
            }
           else{
                Kickoutbuttons[i] = new Button(new KickoutFail(CurrentPlayer), size,size);
            }
            Kickoutbuttons[i].active = true;
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

    public static void ToggleSettings(){
        settingsMenu = !settingsMenu;
        for(Button b:SMenuButtons){
            if(settingsMenu){
                b.active =  true;
            }
            else{
                b.active = false;
            }
        }
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
        Color finish = new Color(212, 101, 11);
        Color view = new Color(18, 220, 193, 164);
        Color purple = new Color(54, 37, 83, 255);

        Tile[] ropeTiles = new Tile[40];
        Tile refPen = null;
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
                    if (c == 14) {
                        rotated = rotateClockwise(TileImage, 1);
                        TileImage = rotated;
                        rotropes = rotateClockwise(ropes,1);
                    }
                    if(r == 5){
                        rotated = rotateClockwise(TileImage,0.5);
                        TileImage = rotated;
                        rotropes = rotateClockwise(ropes,0.5);

                    }

                    if(r == 14){
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
                if(ref!=null && hoverC == ref){
                    if(ref.inView(TileGrid[r][c]) && TileGrid[r][c].type!=6){
                        g.setColor(view);
                        g.fillRect(x, y, width, height);
                    }
                }
                else if(CurrentPlayer!=null) {
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
                        if(TileGrid[r][c].Occupant() == ref){
                            refPen =  TileGrid[r][c];
                        }
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
            Characters[0] = SpawnJay(6, 8, "Texas Redd","Red", Color.red, 1);
            turnOrder[0] = new TurnOrder(Characters[0]);
            Characters[1] = SpawnJay(13, 11, "Tommy Bluford","Blue", Color.blue, 5);
            turnOrder[1] = new TurnOrder(Characters[1]);
            Characters[2] = SpawnJon(8,6,"Crimson Lightning","Red", Color.red,1);
            turnOrder[2] = new TurnOrder(Characters[2]);
            Characters[3] = SpawnJon(11, 13, "El Mono Azul", "Blue", Color.blue,5);
            turnOrder[3] = new TurnOrder(Characters[3]);
            Characters[4] = SpawnJak(7, 7, "Brock Redstone","Red", Color.red,1);
            turnOrder[4] = new TurnOrder(Characters[4]);
            Characters[5] = SpawnJak(12, 12, "Karl Kobalt","Blue", Color.blue,5);
            turnOrder[5] = new TurnOrder(Characters[5]);
            CurrentPlayer = Characters[0];
            settingsButton[0] = new Button(new Settings(CurrentPlayer),30,30,getWidth() - 50,20);
            settingsButton[0].active = true;
            SMenuButtons = new Button[]{new Button(new Settings(CurrentPlayer),50,200),new Button(new resetGame(CurrentPlayer),50,200),new Button(new HowToPlay(CurrentPlayer),50,200),new Button(new Mute(CurrentPlayer),50,200),new Button(new Quitgame(CurrentPlayer),50,200)};
            int smenux = (getWidth()/2) - (SMenuButtons[0].width/2);
            int smenuy = (getHeight()/2) - ((SMenuButtons.length * SMenuButtons[0].height + 40) / 2);
            for(Button b: SMenuButtons){
                b.setX(smenux);
                b.setY(smenuy);

                smenuy += SMenuButtons[0].height + 10;
            }
            ref = new Referee(TileGrid[13][6]);
            ref.orientation = 7;
            ref.orient(ref.orientation);
            ref.TilesinView();
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
            TextBox hypeHelp = new TextBox(getWidth()/2 - 200,50,400,110);
            hypeHelp.setHeader("Crowd status");
            hypeHelp.setText("Performing actions will generate a certain amount of hype which will get the crowd on your " +
                    "teams side. Getting the crowd on your side will earn your team different bonuses. Crowd bonuses are separated" +
                    " into three categories depending on how much hype your team generates. Hover over the crowd indicator for more information on bonuses.");
            hypeHelp.draw(g);

            TextBox actionHelp = new TextBox(10,(getHeight()/2) + 40,250,280);
            actionHelp.setHeader("Actions");
            actionHelp.setText("The actions menu is separated into 3 categories. Strikes, Slams and misc. consecutive strikes increase the damage of strike combo finishers. " +
                    "slam attacks will increase the stamina cost of subsequent slams in the same turn. misc. moves are contextual and will change depending on the context the menu is opened in. " +
                    "misc. actions include pinning, climbing and taunting. You can keep performing actions in a single turn until your stamina runs out. Most action will require you to designate " +
                    "target tiles. Actions are primed with left click, and Target tiles can be selected with the right click. Performing a left click with an action primed will cancel the action.");
            actionHelp.draw(g);

            TextBox infoBoxHelp = new TextBox(270, getHeight() - 205,450,80);
            infoBoxHelp.setHeader("Information Box");
            infoBoxHelp.setText("The information box will display information about actions, characters and the crowd score. Selecting a tile will display information about the character in the selected" +
                    "tile. hovering the mouse over an action or the crowd indicator will display the relevant information.");
            infoBoxHelp.draw(g);

            TextBox turnOrderHelp = new TextBox(getWidth() - 250,getHeight()/4,200,290);
            turnOrderHelp.setHeader("Turn Order");
            turnOrderHelp.setText("Players take turns to control a wrestler on their team. The medium characters go first, then the light characters, and then the heavy characters." +
                    " The Referee will move in between each character turn. The turn order indicator can be used to see which character will be able to act next. If a character is down, pinned or pinning" +
                    " another character they will miss a turn. The current wrestler can be identified with a green circle and arrow. At the start of each characters turn, they will recover some health and " +
                    "stamina based on their regeneration stats.");

            TextBox moveHelp = new TextBox(10,getHeight()/4 + 50,250,190);
            moveHelp.setHeader("Movement");
            moveHelp.setText("Characters can be moved using the right mouse button. Tiles are colour coded depending on how much stamina it would cost to move to that tile. " +
                    "Green tiles indicate the cost for movement to that tile is less than half of the characters remaining stamina. Yellow indicates the movement will cost over half of " +
                    "the character's remaining stamina. Red indicates the movement will cost all of the characters remaining stamina.");

            TextBox aimHelp = new TextBox(10,10,150,230);
            aimHelp.setHeader("Aim of the Game");
            aimHelp.setText("This game is for 2 local players. One player will control the blue team and the other will control the red team. To win the game, one wrestler must " +
                    "successfully pin an opposing team's wrestler for a three count. you must use your character's actions to weaken the enemy to achieve this.");

            TextBox refHelp = new TextBox(infoBoxHelp.x1,infoBoxHelp.y1 - 100,450,95);
            refHelp.setHeader("Referee");
            refHelp.setText("The referee will count pins and look out for any foul play. if the referee is downed during a pin, the pin count will stop and be reset when the referee has recovered. " +
                    "Characters outside of the referee's vision cone (visible when the referee is selected) will be able to perform illegal moves, which are more effective but greatly reduce" +
                    " your crowd score.");

            TextBox pinHelp = new TextBox(infoBoxHelp.x1, refHelp.y1 - 290,270,280);
            pinHelp.setHeader("Pinning");
            pinHelp.setText("When a character's health drops past the pain threshold (indicated by the red health pips) the character will be downed. While downed they may be pinned using the misc. action menu. " +
                    "while a pin is active, the pin count will increase on both the pinners turn and the character being pinned. Once the pin count reaches 3 the game ends. on the turn of a character" +
                    " being pinned, they will have the opportunity to kickout. The pinned player will need to pick a square from a selection. If they successfully select the kickout square, they will kickout. " +
                    "Otherwise, the pincount will increase and they will miss their turn. The number of squares increases with the amount of health missing from the pinned character.");

            helps = new TextBox[]{hypeHelp,actionHelp,aimHelp,refHelp,pinHelp,moveHelp,turnOrderHelp,infoBoxHelp};
            closeHelp = new Button[]{new Button(new closeHelp(CurrentPlayer),50,200,getWidth()/2 - 100,pinHelp.y1 - 100)};

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

        ref.draw((Graphics2D) g);

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
                HoverHype = false;

                int Xhbars = (Selected.CenterX + (TILE_SIZE / 2) - 5) - ((((Selected.Occupant().healthBar.length * 10) + (Selected.Occupant().healthBar.length - 1))/2) - 5)  ;
                int Yhbars = Selected.CenterY - TILE_SIZE;

                int Xsbars = (Selected.CenterX + (TILE_SIZE / 2) - 5)  - ((((Selected.Occupant().staminaBar.length * 10) + (Selected.Occupant().staminaBar.length - 1))/2) - 5)  ;
                int Ysbars = Selected.CenterY - (TILE_SIZE - 10);
                int c = 0;

                for(boolean i:Selected.Occupant().healthBar){
                    if(i && c>=Selected.Occupant().painThresh){
                        if(c>Selected.Occupant().DEFAULT_MAX_HEALTH - 1){
                            g.setColor(Color.yellow);
                        }
                        else {
                            g.setColor(Color.green);
                        }
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
                c = 0;
                for(boolean i:Selected.Occupant().staminaBar){
                    if(i){
                        if(c>Selected.Occupant().DEFAULT_MAX_STAMINA-1){
                            g.setColor(Color.yellow);
                        }
                        else {
                            g.setColor(Color.blue);
                        }
                    }
                    else{
                        g.setColor(Color.black);
                    }

                    g.fillRect(Xsbars,Ysbars,10,5);

                    Xsbars = Xsbars + 11;
                    c++;
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
            int menuy = getHeight() - (((menu[0].height) * menu.length) + 10);


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
                    if (context != null && context[0] !=null) {
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

        //HOVER ACTION

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

            if(Math.abs(hoverA.hype) > 0){
                if(hoverA.type.equals("illegal")){
                    g.drawString("Hype: " + Math.abs(hoverA.hype) * -1,statTextx,statTexty);
                }
                else {
                    g.drawString("Hype: " + Math.abs(hoverA.hype), statTextx, statTexty);
                }
                statTextx = statTextx + g.getFontMetrics().stringWidth("Hype: " + hoverA.hype + 5);
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
                    else if(str.equals("Requires") ||
                            str.equals("level") ||
                            str.equals("Hype.")){
                        g.setColor(finish);
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

        if(HoverHype){
            g.drawString("Hype bar",statTextx,statTexty);
            if(stage1){
                g.setColor(Color.yellow);
            }
            else{
                g.setColor(Color.white);
            }
            g.drawString("Level 1 (" + HYPE_STAGE_1 + "+)",statTextx + 150,statTexty);
            if(stage2){
                g.setColor(Color.yellow);
            }
            else{
                g.setColor(Color.white);
            }
            g.drawString("Level 2 (" + HYPE_STAGE_2 + "+)",statTextx + 275,statTexty);
            if(stage3){
                g.setColor(finish);
            }
            else{
                g.setColor(Color.white);
            }
            g.drawString("Level 3 (" + MAX_HYPE + ")",statTextx + 400,statTexty);
            statTexty = statTexty + 30;
            g.setColor(Color.white);
            g.drawString("Current favourite: ",statTextx,statTexty);
            if(crowd < 0){
                g.drawString("Red", statTextx + g.getFontMetrics().stringWidth( "Current favourite: "),statTexty);
            }
            else if(crowd > 0){
                g.drawString("Blue", statTextx + g.getFontMetrics().stringWidth( "Current favourite: "),statTexty);
            }

            if(stage1){
                g.setColor(Color.yellow);
            }
            else{
                g.setColor(Color.white);
            }
            g.drawString("Stamina regen +2", statTextx + 150,statTexty);
            g.drawString("Health regen +1",statTextx + 150,statTexty + 20);
            if(stage2){
                g.setColor(Color.yellow);
            }
            else{
                g.setColor(Color.white);
            }
            g.drawString("Max Health +2",statTextx + 275,statTexty + 20);
            g.drawString("Max Stamina +2",statTextx + 275,statTexty);
            if(stage3){
                g.setColor(finish);
            }
            else{
                g.setColor(Color.white);
            }
            g.drawString("Signature",statTextx + 400,statTexty);
            g.drawString("moves",statTextx + 400,statTexty + 15);
            g.drawString("unlocked",statTextx + 400,statTexty + 30);
            g.setColor(Color.white);

            statTexty = statTexty + 30;
            g.drawString("Crowd Score: ",statTextx,statTexty);
            if(crowd < 0){
                g.setColor(Color.red);
            }
            else if(crowd > 0){
                g.setColor(Color.blue);
            }
            g.drawString(String.valueOf(Math.abs(crowd)),statTextx + g.getFontMetrics().stringWidth( "Crowd Score: "),statTexty);
            g.setColor(Color.white);

        }

        //HOVER CHARACTER

        if(hoverC!=null){
            g.drawString(hoverC.name + " (" + hoverC.states[hoverC.state] + ")", statTextx,statTexty);
            statTexty = statTexty + 30;
            g.drawString("Health: " + hoverC.Health,statTextx,statTexty);
            statTexty = statTexty + 20;
            g.drawString("Max Health: " + hoverC.DEFAULT_MAX_HEALTH,statTextx,statTexty);
            if(hoverC.MaxHealth != hoverC.DEFAULT_MAX_HEALTH){
                g.setColor(Color.yellow);
                g.drawString("+" + (hoverC.MaxHealth - hoverC.DEFAULT_MAX_HEALTH),statTextx + g.getFontMetrics().stringWidth("Max Health: " + hoverC.MaxHealth),statTexty);
            }
            g.setColor(Color.white);
            statTexty = statTexty + 20;
            g.drawString("Stamina: " + hoverC.MovePoints,statTextx,statTexty);
            statTextx = statTextx + 125;
            statTexty = infoBoxy + 50;
            g.drawString("Max Stamina: " + hoverC.DEFAULT_MAX_STAMINA,statTextx,statTexty);
            if(hoverC.MaxMove != hoverC.DEFAULT_MAX_STAMINA){
                g.setColor(Color.yellow);
                g.drawString("+" + (hoverC.MaxMove - hoverC.DEFAULT_MAX_STAMINA),statTextx + g.getFontMetrics().stringWidth("Max Stamina: " + hoverC.DEFAULT_MAX_STAMINA),statTexty);
            }
            g.setColor(Color.white);
            statTexty = statTexty + 20;
            g.drawString("HP regeneration: " + hoverC.DEFAULT_MAX_HEALTHREGEN, statTextx,statTexty);
            if(hoverC.regen != hoverC.DEFAULT_MAX_HEALTHREGEN){
                g.setColor(Color.yellow);
                g.drawString("+" + (hoverC.regen - hoverC.DEFAULT_MAX_HEALTHREGEN),statTextx + g.getFontMetrics().stringWidth("HP regeneration: " + hoverC.DEFAULT_MAX_HEALTHREGEN),statTexty);
            }
            g.setColor(Color.white);
            statTexty = statTexty + 20;
            g.drawString("Stamina regeneration: " + hoverC.DEFAULT_MAX_STAMREGEN,statTextx,statTexty);
            if(hoverC.stamregen != hoverC.DEFAULT_MAX_STAMREGEN){
                g.setColor(Color.yellow);
                g.drawString("+" + (hoverC.stamregen - hoverC.DEFAULT_MAX_STAMREGEN),statTextx + g.getFontMetrics().stringWidth("Stamina regeneration: " + hoverC.DEFAULT_MAX_STAMREGEN),statTexty);
            }
            g.setColor(Color.white);
            statTexty = statTexty + 20;
            g.drawString("Moving: " + hoverC.moving,statTextx,statTexty);


            statTextx = statTextx + 200;
            statTexty = statTexty = infoBoxy;
            g.drawImage(hoverC.sprite.image, statTextx,statTexty,null);

        }

        //KICKOUT BUTTONS

        if(Kickoutbuttons!=null) {
            int kowidth = (Kickoutbuttons.length * Kickoutbuttons[0].width) + (30 * (Kickoutbuttons.length - 1));
            drawButtons(g,(getWidth()/2) - (kowidth/2) , getHeight() - 200, Kickoutbuttons);
        }

        //RESET BUTTON

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

        if(refPen!=null && atkPrimed){
            g.setColor(purple);
            int strwidth = g.getFontMetrics().stringWidth("-" + refPenalty + " Hype");
            g.setFont(new Font("sans-serif",Font.BOLD,15));
            g.drawString("-" + refPenalty + " Hype", refPen.CenterX + ((TILE_SIZE/2) - (strwidth/2)),refPen.CenterY +(TILE_SIZE));
        }




        Image baseHype = Sprite.HyperBarBase;
        BufferedImage fillHype = (BufferedImage) Sprite.HyperBarFill;
        Image HypeInd = Sprite.HyperBarInd;


        int HypeWidth = 10;
        int HypeHeight = 25;




        int IndY = HypeY - 10;
        int IndX;
        if(crowd > MAX_HYPE){
            IndX = ((getWidth()/2) - (HypeWidth/2)) + (MAX_HYPE * (HypeBarWidth/(MAX_HYPE*2)));
        }
        else if(crowd < (MAX_HYPE * -1)){
            IndX = ((getWidth()/2) - (HypeWidth/2)) + ((MAX_HYPE * -1) * (HypeBarWidth/(MAX_HYPE*2)));
        }
        else{
            IndX = ((getWidth()/2) - (HypeWidth/2)) + (crowd * (HypeBarWidth/(MAX_HYPE*2)));
        }



        g.drawImage(baseHype,HypeX, HypeY,HypeBarWidth,20,null);


        Rectangle rect;
        if(crowd ==0){
            rect = new Rectangle(0,0,0,0);
        }
        else if(crowd>0){
            rect = new Rectangle(getWidth()/2,HypeY,(IndX - (getWidth()/2)) + HypeWidth/2,20);
        }
        else{
            rect = new Rectangle((IndX) + HypeWidth/2 ,HypeY,(getWidth()/2) - (IndX + ( HypeWidth/2)),20);
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setClip(rect);
        g2d.drawImage(fillHype,HypeX,HypeY,HypeBarWidth,20,null);
        g2d.setClip(null);
        g.drawImage(HypeInd,IndX,IndY,HypeWidth,HypeHeight,null);

        g2d.drawImage(Sprite.Cog,settingsButton[0].x1,settingsButton[0].y1,settingsButton[0].width,settingsButton[0].height,null);

        if(settingsMenu){
            String title = "International Capstone Wrestling";
            g.setFont(new Font("sans-serif",Font.BOLD,25));
            int titlex = (getWidth()/2) - (g.getFontMetrics().stringWidth(title)/2);
            int titley = SMenuButtons[0].y1 - 30;
            g.drawString(title,titlex,titley);
            g.setFont(new Font("sans-serif",Font.BOLD,15));
            for(Button b:SMenuButtons){
                g.setColor(Color.black);
                g.fillRect(b.x1,b.y1,b.width,b.height);
                g.setColor(Color.white);
                int x = (b.x2 - (b.width/2)) - (g.getFontMetrics().stringWidth(b.action.name)/2);
                int y = (b.y2 - (b.height/2)) + (g.getFontMetrics().getHeight()/4);
                g.drawString(b.action.name, x,y);
            }
            String credit = "Created by Peter Long";
            g.drawString(credit,(getWidth()/2) - (g.getFontMetrics().stringWidth(credit)/2), SMenuButtons[4].y2 + 20);
        }

        if(howToPlay){
            for(TextBox t:helps){
                t.draw(g);
            }
            g.setColor(Color.black);
            g.fillRect(closeHelp[0].x1, closeHelp[0].y1,closeHelp[0].width,closeHelp[0].height);
            g.setColor(Color.white);
            g.setFont(new Font("sans-serif",Font.BOLD,15));
            int x = ( closeHelp[0].x2 - ( closeHelp[0].width/2)) - (g.getFontMetrics().stringWidth( closeHelp[0].action.name)/2);
            int y = ( closeHelp[0].y2 - ( closeHelp[0].height/2)) + (g.getFontMetrics().getHeight()/4);
            g.drawString( closeHelp[0].action.name, x,y);

            closeHelp[0].active = true;
        }
        else{
            if(closeHelp[0]!=null) {
                closeHelp[0].active = false;
            }
        }

        if(debug){
            g.setFont(new Font("sans-serif",Font.PLAIN,9));
            g.drawString("Current Player = " + CurrentPlayer,0,14);
            g.drawString("Pinning = " + pinning(),0,30);
            int y = 45;
            for(Character c: Characters){
                g.drawString(c.toString(),0,y);
                y = y + 15;
            }
            if(primedAtk !=null) {
                g.drawString("Primed Attack = " + primedAtk, 0, y);
                y += 15;
                g.drawString("Attack target = " + primedAtk.targets[0], 0, y);
            }

        }











    }

    private void DrawMenu(Graphics g, int menux, int menuy, Button[] buttons) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        Color TeamCol;
        Color finish = new Color(212, 101, 11);
        if(CurrentPlayer.teamname.equals("Blue")){
            TeamCol = Color.blue;
        }
        else{
            TeamCol = Color.red;
        }



        if(buttons.length > 4){
            menuy = getHeight() - ((buttons[0].height * buttons.length) + 10);
        }
        else{
            menuy = getHeight() - ((buttons[0].height * 4) + 10);
        }




        for(Button b: buttons){
            if(b!=null) {
                b.active = true;
                if(b.action.canAfford()){
                    if(b.action.type.equals("illegal")){
                        g.setColor(new Color(138,43,226));
                    }
                    else if(b.action.type.equals("Signature")){
                        g.setColor(finish);
                    }
                    else {
                        g.setColor(TeamCol);
                    }
                }
                else{
                    g.setColor(Color.gray);
                }

                b.setX(menux);
                b.setY(menuy);
                if (b.action.name.equals("Misc.")) {
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
