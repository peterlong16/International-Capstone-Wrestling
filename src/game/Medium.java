package game;

import java.awt.*;
import java.util.Arrays;

public class Medium extends Character{


    Medium(Tile t, String name, String team, Color colour){
        super(t);
        this.image = colour;
        this.sprite = new Sprite(this,Constants.TILE_SIZE,Constants.TILE_SIZE);
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

        if(teamname.equals("blue")){
            sprites = new Image[]{
                    Sprite.BlueMIdle,
                    Sprite.BlueMDown,
                    Sprite.BlueMGrab,
                    Sprite.BlueMPunch,
                    Sprite.BlueMKick,
                    Sprite.BlueMDKick,
                    Sprite.BlueMPin
            };
        }
        else{
            sprites = new Image[]{
                    Sprite.RedMIdle,
                    Sprite.RedMDown,
                    Sprite.RedMGrab,
                    Sprite.RedMPunch,
                    Sprite.RedMKick,
                    Sprite.RedMDKick,
                    Sprite.RedMPin
            };
        }
        strikes = new Action[2];

        strikes[0] = new punch(this);
        strikes[1] = new dropKick(this);

        slams = new Action[1];
        slams[0] = new suplex(this);
        sprite.setImage(sprites[0]);


    }

    public void draw(Graphics2D g){
        this.sprite.draw(g);
    }
}
