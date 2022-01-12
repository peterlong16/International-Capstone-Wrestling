package game;

import java.awt.*;
import java.util.Arrays;

public class Heavy extends Character {

    Heavy(Tile t, String name, String team, Color colour){
        super(t);
        this.image = colour;
        this.sprite = new Sprite(this,Constants.TILE_SIZE,Constants.TILE_SIZE);
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



        if(teamname.equals("blue")){
            sprites = new Image[]{
                    Sprite.BlueHIdle,
                    Sprite.BlueHDown,
                    Sprite.BlueHGrab,
                    Sprite.BlueHPunch,
                    Sprite.BlueHKick,
                    Sprite.BlueHHbutt,
                    Sprite.BlueHPin
            };
        }
        else{
            sprites = new Image[]{
                    Sprite.RedHIdle,
                    Sprite.RedHDown,
                    Sprite.RedHGrab,
                    Sprite.RedHPunch,
                    Sprite.RedHKick,
                    Sprite.RedHHbutt,
                    Sprite.RedHPin
            };
        }
        sprite.setImage(sprites[0]);

        strikes = new Action[2];



        strikes[1] = new superkick(this);

        slams = new Action[1];
        slams[0] = new SpineBuster(this);
    }

    public void draw(Graphics2D g){
        this.sprite.draw(g);
    }


}
