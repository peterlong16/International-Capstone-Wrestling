package game;

import java.awt.*;
import java.util.Arrays;

public class jak extends Character {

    jak(Tile t, String name, String team,Color colour){
        super(t);
        this.image = colour;
        MovePoints = 6;
        MaxMove = 6;
        this.name = name;
        this.teamname = team;
        MaxHealth = 8;
        Health = 8;
        regen = 1;
        stamregen = 3;
        painThresh = 3;
        this.healthBar = new boolean[MaxHealth];
        Arrays.fill(healthBar, true);
        this.staminaBar = new boolean[MaxMove];
        Arrays.fill(staminaBar, true);

        strikes = new Action[2];

        strikes[0] = new punch(this);
        strikes[1] = new superkick(this);

        slams = new Action[1];
        slams[0] = new SpineBuster(this);
    }


}
