package game;

import java.awt.*;
import java.util.Arrays;

public class Heavy extends Character {

    Heavy(Tile t, String name, String team, Color colour,int or){
        super(t);

        DEFAULT_MAX_HEALTH = 10;
        DEFAULT_MAX_HEALTHREGEN = 2;
        DEFAULT_MAX_STAMINA = 8;
        DEFAULT_MAX_STAMREGEN = 5;

        MaxMove = DEFAULT_MAX_STAMINA;
        MovePoints = MaxMove;

        MaxHealth = DEFAULT_MAX_HEALTH;
        Health = MaxHealth;

        regen = DEFAULT_MAX_HEALTHREGEN;
        stamregen = DEFAULT_MAX_STAMREGEN;


        this.orientation = or;
        this.image = colour;
        this.sprite = new Sprite(this,Constants.TILE_SIZE,Constants.TILE_SIZE);

        this.name = name;
        this.teamname = team;
        painThresh = 3;
        this.healthBar = new boolean[MaxHealth];
        Arrays.fill(healthBar, true);
        this.staminaBar = new boolean[MaxMove];
        Arrays.fill(staminaBar, true);

        if(teamname.equals("Blue")){
            sprites = new Image[]{
                    Sprite.BlueHIdle,
                    Sprite.BlueHDown,
                    Sprite.BlueHGrab,
                    Sprite.BlueHPunch,
                    Sprite.BlueHKick,
                    Sprite.BlueHHbutt,
                    Sprite.BlueHTaunt,
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
                    Sprite.RedHTaunt,
                    Sprite.RedHPin
            };
        }
        sprite.setImage(sprites[0]);

        strikes = new Action[3];
        strikes[0] = new GiantSlap(this);
        strikes[1] = new HeadButt(this);
        strikes[2] = new Bigboot(this);

        slams = new Action[3];
        slams[0] = new Powerbomb(this);
        slams[1] = new SpineBuster(this);
        slams[2] = new GorillaPress(this);

        dives = new Action[1];
        dives[0] = new AxeHandle(this);

        springs = new Action[1];
        springs[0] = new SBChokeslam(this);
    }

    public void draw(Graphics2D g){
        this.sprite.draw(g);
    }


}
