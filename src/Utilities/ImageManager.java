package Utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;

public class ImageManager {
    public static final String path = "src/Sprites/";
    public static final String ext = ".png";
    public static Map<String, Image> images = new HashMap();

    public ImageManager() {
    }

    public static Image getImage(String s) {
        return (Image)images.get(s);
    }

    public static Image loadImage(String fname) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File(path + fname + ".png"));
        images.put(fname, img);
        return img;
    }

    public static Image loadImage(String imName, String fname) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File(path + fname + ".png"));
        images.put(imName, img);
        return img;
    }

    public static void loadImages(String[] fNames) throws IOException {
        String[] var1 = fNames;
        int var2 = fNames.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            loadImage(s);
        }

    }

    public static void loadImages(Iterable<String> fNames) throws IOException {
        Iterator var1 = fNames.iterator();

        while(var1.hasNext()) {
            String s = (String)var1.next();
            loadImage(s);
        }

    }
}
