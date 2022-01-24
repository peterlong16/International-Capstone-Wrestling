package game;

import java.awt.*;
import java.util.Arrays;

public class Heavy extends Character {

    Heavy(Tile t, String name, String team, Color colour,int or){
        super(t);
        this.orientation = or;
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

        strikes = new Action[3];
        strikes[0] = new GiantSlap(this);
        strikes[1] = new HeadButt(this);
        strikes[2] = new Bigboot(this);

        slams = new Action[2];
        slams[0] = new Powerbomb(this);
        slams[1] = new SpineBuster(this);

        dives = new Action[1];
        dives[0] = new AxeHandle(this);

        springs = new Action[1];
        springs[0] = new SBChokeslam(this);
    }

    public void draw(Graphics2D g){
        this.sprite.draw(g);
    }


}
