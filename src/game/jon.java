package game;

import java.awt.*;
import java.util.Arrays;

public class jon extends Character {

    jon(Tile t){
        super(t);
        this.image = Color.red;
        MovePoints = 6;
        MaxMove = 6;
        name = "red";
        MaxHealth = 10;
        Health = 10;
        teamname = "Red";
        regen = 1;
        stamregen = 3;
        painThresh = 4;
        this.healthBar = new boolean[MaxHealth];
        Arrays.fill(healthBar, true);
        this.staminaBar = new boolean[MaxMove];
        Arrays.fill(staminaBar, true);
    }


}
