package game;

import java.awt.*;
import java.util.Arrays;

public class Light extends Character {

    Light(Tile t, String name, String team, Color colour, int or){
        super(t);
        DEFAULT_MAX_HEALTH = 10;
        DEFAULT_MAX_HEALTHREGEN = 2;
        DEFAULT_MAX_STAMINA = 10;
        DEFAULT_MAX_STAMREGEN = 4;

        MaxMove = DEFAULT_MAX_STAMINA;
        MovePoints = MaxMove;

        MaxHealth = DEFAULT_MAX_HEALTH;
        Health = MaxHealth;

        regen = DEFAULT_MAX_HEALTHREGEN;
        stamregen = DEFAULT_MAX_STAMREGEN;

        signature = true;
        this.orientation = or;
        this.image = colour;
        this.sprite = new Sprite(this,Constants.TILE_SIZE,Constants.TILE_SIZE);
        this.name = name;
        this.teamname = team;
        painThresh = 4;
        this.healthBar = new boolean[MaxHealth];
        Arrays.fill(healthBar, true);
        this.staminaBar = new boolean[MaxMove];
        Arrays.fill(staminaBar, true);



        if(teamname.equals("Blue")){
            sprites = new Image[]{
                    Sprite.BlueLIdle,
                    Sprite.BlueLDown,
                    Sprite.BlueLGrab,
                    Sprite.BlueLPunch,
                    Sprite.BlueLKick,
                    Sprite.BlueLTaunt,
                    Sprite.BlueLPin
            };
        }
        else{
            sprites = new Image[]{
                    Sprite.RedLIdle,
                    Sprite.RedLDown,
                    Sprite.RedLGrab,
                    Sprite.RedLPunch,
                    Sprite.RedLKick,
                    Sprite.RedLTaunt,
                    Sprite.RedLPin
            };
        }
        sprite.setImage(sprites[0]);

        strikes = new Action[4];

        strikes[0] = new superkick(this);
        strikes[1] = new Enzugiri(this);
        strikes[2] = new Euppercut(this);
        strikes[3] = new eyeRake(this);

        slams = new Action[2];
        slams[0] = new rana(this);
        slams[1] = new Slingblade(this);

        dives = new Action[2];
        dives[0] = new FrogSplash(this);
        dives[1] = new PhoenixSplash(this);

        springs = new Action[1];
        springs[0] = new SBrana(this);
    }

    public void draw(Graphics2D g){
        this.sprite.draw(g);
    }

}
