package game;

import java.awt.*;

public class TextBox {
    int x1;
    int y1;
    int x2;
    int y2;

    int width;
    int height;

    String header;
    String text;

    TextBox(int x, int y, int width, int height){
        this.x1 = x;
        this.y1 = y;
        this.width = width;
        this.height = height;
        this.x2 = this.x1 + width;
        this.y2 = this.y1 + height;
    };

    void setText(String text){
        this.text = text;
    }

    void setHeader(String head){
        this.header = head;
    }

    public void draw(Graphics g){
        String[] sText = this.text.split(" ");
        Font headerFont = new Font("sans-serif",Font.BOLD,15);
        Font textFont = new Font("sans-serif",Font.PLAIN,12);
        g.setFont(headerFont);
        g.setColor(Color.white);
        g.fillRect(x1,y1,width,height);
        g.setColor(Color.black);
        g.drawString(header,x1 + 5,y1 + 15);

        int textX = x1 + 4;
        int textY = y1 + 30;
        int maxwidth = width - 10;


        int curWidth = 4;
        g.setFont(textFont);


        for(String str: sText){
            int strwidth = g.getFontMetrics().stringWidth(str);
            curWidth += strwidth + 4;

            if(curWidth >= maxwidth){
                curWidth = strwidth + 4;
                textX = x1 + 4;
                textY += 15;
            }

            g.drawString(str,textX,textY);
            textX += strwidth + 5;
        }


    }


}
