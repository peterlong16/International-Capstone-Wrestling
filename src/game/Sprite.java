package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.ImageObserver;
import java.io.IOException;
import Utilities.ImageManager;


public class Sprite {
    public static Image BlueMIdle;
    public static Image BlueMGrab;
    public static Image BlueMPunch;
    public static Image BlueMKick;
    public static Image BlueMDKick;
    public static Image BlueMDown;
    public static Image BlueMTaunt;
    public static Image BlueMPin;

    public static Image RedMIdle;
    public static Image RedMGrab;
    public static Image RedMPunch;
    public static Image RedMKick;
    public static Image RedMDKick;
    public static Image RedMDown;
    public static Image RedMTaunt;
    public static Image RedMPin;

    public static Image BlueHIdle;
    public static Image BlueHGrab;
    public static Image BlueHPunch;
    public static Image BlueHKick;
    public static Image BlueHHbutt;
    public static Image BlueHDown;
    public static Image BlueHTaunt;
    public static Image BlueHPin;

    public static Image RedHIdle;
    public static Image RedHGrab;
    public static Image RedHPunch;
    public static Image RedHKick;
    public static Image RedHHbutt;
    public static Image RedHDown;
    public static Image RedHTaunt;
    public static Image RedHPin;

    public static Image BlueLIdle;
    public static Image BlueLGrab;
    public static Image BlueLPunch;
    public static Image BlueLKick;
    public static Image BlueLDown;
    public static Image BlueLTaunt;
    public static Image BlueLPin;

    public static Image RedLIdle;
    public static Image RedLGrab;
    public static Image RedLPunch;
    public static Image RedLKick;
    public static Image RedLDown;
    public static Image RedLTaunt;
    public static Image RedLPin;

    public static Image RedTurnOrder;
    public static Image BlueTurnOrder;
    public static Image Inactive;
    public static Image TurnInd;
    public static Image Win;
    public static Image Pin;
    public static Image KickoutGuess;
    public static Image KickoutPass;
    public static Image KickoutFail;

    public static Image HyperBarBase;
    public static Image HyperBarFill;
    public static Image HyperBarInd;




    public Image image;
    public int width;
    public int height;
    Character character;

    public Sprite(Character c, int width, int height) {
        this.character = c;
        this.width = width;
        this.height = height;
    }

    public void setImage(Image img){
        this.image = img;
    }

    public double getRadius() {
        return (this.width + this.height) / 4.0D;
    }


    public void draw(Graphics2D g) {
        g.drawImage(this.image, (int)character.x, (int)character.y,this.width,this.height, null);

    }

    static {
        try {
            BlueMIdle = ImageManager.loadImage("bluemedidle");
            BlueMGrab = ImageManager.loadImage("bluemedgrab");
            BlueMPunch = ImageManager.loadImage("bluemedpunch");
            BlueMKick = ImageManager.loadImage("bluemedkick");
            BlueMDKick = ImageManager.loadImage("bluedropkick");
            BlueMTaunt = ImageManager.loadImage("bluemedtaunt");
            BlueMDown = ImageManager.loadImage("bluemedlaying");
            BlueMPin = ImageManager.loadImage("bluemedpin");

            RedMIdle = ImageManager.loadImage("redmedidle");
            RedMGrab = ImageManager.loadImage("redmedgrab");
            RedMPunch = ImageManager.loadImage("redmedpunch");
            RedMKick = ImageManager.loadImage("redmedkick");
            RedMDKick = ImageManager.loadImage("reddropkick");
            RedMTaunt = ImageManager.loadImage("redmedtaunt");
            RedMDown = ImageManager.loadImage("redmedlaying");
            RedMPin = ImageManager.loadImage("redmedpin");

            BlueHIdle = ImageManager.loadImage("bluehevIdle");
            BlueHGrab = ImageManager.loadImage("bluehevgrab");
            BlueHPunch = ImageManager.loadImage("bluehevpunch");
            BlueHKick = ImageManager.loadImage("bluehevkick");
            BlueHHbutt = ImageManager.loadImage("bluehevheadbutt");
            BlueHTaunt = ImageManager.loadImage("bluehevtaunt");
            BlueHDown = ImageManager.loadImage("bluehevlaying");
            BlueHPin = ImageManager.loadImage("bluehevpin");

            RedHIdle = ImageManager.loadImage("redhevIdle");
            RedHGrab = ImageManager.loadImage("redhevgrab");
            RedHPunch = ImageManager.loadImage("redhevpunch");
            RedHKick = ImageManager.loadImage("redhevkick");
            RedHHbutt = ImageManager.loadImage("redhevheadbutt");
            RedHTaunt = ImageManager.loadImage("redhevtaunt");
            RedHDown = ImageManager.loadImage("redhevlaying");
            RedHPin = ImageManager.loadImage("redhevpin");

            BlueLIdle = ImageManager.loadImage("bluelight");
            BlueLGrab = ImageManager.loadImage("bluelightslam");
            BlueLPunch = ImageManager.loadImage("bluelightupper");
            BlueLKick = ImageManager.loadImage("bluelightkick");
            BlueLTaunt = ImageManager.loadImage("bluelighttaunt");
            BlueLDown = ImageManager.loadImage("bluelightlaying");
            BlueLPin = ImageManager.loadImage("bluelightpin");

            RedLIdle = ImageManager.loadImage("redlight");
            RedLGrab = ImageManager.loadImage("redlightslam");
            RedLPunch = ImageManager.loadImage("redlightupper");
            RedLKick = ImageManager.loadImage("redlightkick");
            RedLTaunt = ImageManager.loadImage("redlighttaunt");
            RedLDown = ImageManager.loadImage("redlightlaying");
            RedLPin = ImageManager.loadImage("redlightpin");

            RedTurnOrder = ImageManager.loadImage("redturnorder");
            BlueTurnOrder = ImageManager.loadImage("blueturnorder");
            Inactive = ImageManager.loadImage("inactive");
            TurnInd = ImageManager.loadImage("turnmarker");
            Win = ImageManager.loadImage("win");
            Pin = ImageManager.loadImage("pinned");
            KickoutGuess = ImageManager.loadImage("kickoutguess");
            KickoutPass = ImageManager.loadImage("kickoutpass");
            KickoutFail = ImageManager.loadImage("kickoutfail");

            HyperBarBase = ImageManager.loadImage("HypeBarBase");
            HyperBarFill = ImageManager.loadImage("HypeBarFill");
            HyperBarInd = ImageManager.loadImage("HypeBarInd");




        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }
}
