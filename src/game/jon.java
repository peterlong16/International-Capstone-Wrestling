package game;

import java.awt.*;
import java.util.Arrays;

public class jon extends Character {

    jon(Tile t){
        super(t);
        image = Color.red;
        MovePoints = 4;
        MaxMove = 4;
        name = "jon";
        MaxHealth = 10;
        Health = 2;
        this.healthBar = new boolean[MaxHealth];
        Arrays.fill(healthBar, true);
        this.staminaBar = new boolean[MaxMove];
        Arrays.fill(staminaBar, true);
    }


}
