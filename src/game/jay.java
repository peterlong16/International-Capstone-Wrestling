package game;

import java.awt.*;
import java.util.Arrays;

public class jay extends Character{
    jay(Tile t){
        super(t);
        this.image = Color.green;
        MovePoints = 6;
        MaxMove = 6;
        name = "Green";
        MaxHealth = 8;
        Health = 8;
        regen = 1;
        stamregen = 3;
        painThresh = 3;
        this.healthBar = new boolean[MaxHealth];
        Arrays.fill(healthBar, true);
        this.staminaBar = new boolean[MaxMove];
        Arrays.fill(staminaBar, true);
    }
}
