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
    public static final String path = "/Sprites/";
    public static final String ext = ".png";
    public static Map<String, Image> images = new HashMap();

    public ImageManager() {
    }

    public static Image getImage(String s) {
        return (Image)images.get(s);
    }

    public static Image loadImage(String fname) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(ImageManager.class.getResource(path + fname + ext));
        images.put(fname, img);
        return img;
    }

}
