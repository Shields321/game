package Gui;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Texture {


    public ArrayList<Texture> textures;
    public int[] pixels;
    private String loc;
    public final int SIZE;

    public Texture(String location, int size) {
        loc = location;
        SIZE = size;
        pixels = new int[SIZE*SIZE];

        load();
    }
    private void load() {
        try{
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Texture bluestone = new Texture("G:\\Git_Code\\game\\src\\main\\java\\Gui\\Textures\\bluestone.png", 64);
    public static Texture stone = new Texture("G:\\Git_Code\\game\\src\\main\\java\\Gui\\Textures\\graystone.png", 64);
    public static Texture brick = new Texture("G:\\Git_Code\\game\\src\\main\\java\\Gui\\Textures\\redbrick.png", 64);
    public static Texture wood = new Texture("G:\\Git_Code\\game\\src\\main\\java\\Gui\\Textures\\wood.png", 64);
}
