package game;

import java.awt.*;

public class TurnOrder {
    Image img;
    Image inactive = Sprite.Inactive;
    Image win = Sprite.Win;
    Image pin = Sprite.Pin;
    Character c;
    int width,height;
    boolean Inactive = false;

    TurnOrder(Character c){
        this.c = c;
        this.width = 60;
        this.height = 60;
        if (c.teamname.equals("red")){
            img = Sprite.RedTurnOrder;
        }
        else{
            img = Sprite.BlueTurnOrder;
        }

    }

    void draw(Graphics g,int x,int y){
        g.drawImage(img,x,y,this.width,this.height,null);
        g.drawImage(c.sprites[0],x + (this.width/3),y + (this.height/4),40,40,null);
        if(c.state==3){
            g.drawImage(pin,(x + (this.width/4)),y + (this.height/4), 20,20,null);
        }
        else if(c.Health < c.painThresh){
            g.drawImage(inactive,(x + (this.width/4)),y + (this.height/4), 20,20,null);
        }
        else if(c.state==2){
            g.drawImage(win,(x + (this.width/4)),y + (this.height/4), 20,20,null);
        }
    }

}
