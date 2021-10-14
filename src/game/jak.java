package game;

import java.awt.*;

public class jak extends Character {

    jak(Tile t){
        super(t);
        image = Color.blue;
        MovePoints = 4;
        MaxMove = 4;
        name = "Jak";
    }


}
