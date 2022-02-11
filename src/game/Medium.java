package game;

import java.awt.*;
import java.util.Arrays;

public class Medium extends Character{


    Medium(Tile t, String name, String team, Color colour, int or){
        super(t);

        DEFAULT_MAX_HEALTH = 8;
        DEFAULT_MAX_HEALTHREGEN = 2;
        DEFAULT_MAX_STAMINA = 8;
        DEFAULT_MAX_STAMREGEN = 5;

        MaxMove = DEFAULT_MAX_STAMINA;
        MovePoints = MaxMove;

        MaxHealth = DEFAULT_MAX_HEALTH;
        Health = MaxHealth;

        regen = DEFAULT_MAX_HEALTHREGEN;
        stamregen = DEFAULT_MAX_STAMREGEN;

        this.image = colour;
        this.orientation = or;
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
                    Sprite.BlueMIdle,
                    Sprite.BlueMDown,
                    Sprite.BlueMGrab,
                    Sprite.BlueMPunch,
                    Sprite.BlueMKick,
                    Sprite.BlueMDKick,
                    Sprite.BlueMTaunt,
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
                    Sprite.RedMTaunt,
                    Sprite.RedMPin
            };
        }
        strikes = new Action[4];

        strikes[0] = new punch(this);
        strikes[1] = new dropKick(this);
        strikes[2] = new Spinkick(this);
        strikes[3] = new KneeStrike(this);

        slams = new Action[2];
        slams[0] = new suplex(this);
        slams[1] = new Uranage(this);
        sprite.setImage(sprites[0]);

        dives = new Action[1];
        dives[0] = new ElbowDrop(this);

        springs = new Action[1];
        springs[0] = new SBforearm(this);


    }

    public void draw(Graphics2D g){
        this.sprite.draw(g);
    }
}
