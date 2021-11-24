package game;

import java.awt.*;
import java.util.Arrays;

public class Medium extends Character{
    Medium(Tile t, String name, String team, Color colour){
        super(t);
        this.image = colour;
        MovePoints = 50;
        MaxMove = 8;
        this.name = name;
        this.teamname = team;
        MaxHealth = 8;
        Health = 8;
        regen = 2;
        stamregen = 3;
        painThresh = 3;
        this.healthBar = new boolean[MaxHealth];
        Arrays.fill(healthBar, true);
        this.staminaBar = new boolean[MaxMove];
        Arrays.fill(staminaBar, true);
        strikes = new Action[3];

        strikes[0] = new punch(this);
        strikes[1] = new superkick(this);
        strikes[2] = new dropKick(this);

        slams = new Action[2];
        slams[0] = new suplex(this);
        slams[1] = new rana(this);
    }
}
