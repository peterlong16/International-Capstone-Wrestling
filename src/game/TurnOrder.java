package game;

import java.awt.*;

public class TurnOrder {
    Image img;
    Image inactive = Sprite.Inactive;
    Image win = Sprite.Win;
    Image pin = Sprite.Pin;
    Character c;
    int width,height;
    int lowx;
    int lowy;
    int highx;
    int highy;

    TurnOrder(Character c){
        this.c = c;
        this.width = 60;
        this.height = 60;
        if (c.teamname.equals("Red")){
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
            g.drawImage(pin,(x + (this.width/6)),y + (this.height/6), 30,30,null);
        }
        else if(c.Health < c.painThresh){
            g.drawImage(inactive,(x + (this.width/6)),y + (this.height/6), 30,30,null);
        }
        else if(c.state==2){
            g.drawImage(win,(x + (this.width/6)),y + (this.height/6), 30,30,null);
        }
    }

    void setBounds(int x,int y){
        this.lowx = x;
        this.lowy = y;

        this.highx = x + this.width;
        this.highy = y + this.height;
    }

}
