package game;

import java.awt.*;
import java.util.Arrays;

public class Light extends Character {

    Light(Tile t, String name, String team, Color colour, int or){
        super(t);
        this.orientation = or;
        this.image = colour;
        this.sprite = new Sprite(this,Constants.TILE_SIZE,Constants.TILE_SIZE);
        MovePoints = 8;
        MaxMove = 8;
        this.name = name;
        MaxHealth = 10;
        Health = 10;
        this.teamname = team;
        regen = 2;
        stamregen = 5;
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
                    Sprite.RedLPin
            };
        }
        sprite.setImage(sprites[0]);

        strikes = new Action[3];

        strikes[0] = new superkick(this);
        strikes[1] = new Enzugiri(this);
        strikes[2] = new Euppercut(this);

        slams = new Action[2];
        slams[0] = new rana(this);
        slams[1] = new Slingblade(this);

        dives = new Action[1];
        dives[0] = new FrogSplash(this);

        springs = new Action[1];
        springs[0] = new SBrana(this);
    }

    public void draw(Graphics2D g){
        this.sprite.draw(g);
    }

}
