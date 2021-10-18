package game;

import java.awt.*;
import java.util.Arrays;

public class jak extends Character {

    jak(Tile t){
        super(t);
        image = Color.blue;
        MovePoints = 5;
        MaxMove = 5;
        name = "Jak";
        MaxHealth = 8;
        Health = 4;
        this.healthBar = new boolean[MaxHealth];
        Arrays.fill(healthBar, true);
        this.staminaBar = new boolean[MaxMove];
        Arrays.fill(staminaBar, true);
    }


}
