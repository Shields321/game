package Gui;

import Game.Game_Main;
import Gui.Screen;
import Entities.Player;
import Game.Maps;
import Meshes.MeshLoader;
import Meshes.MeshLoader;
import usages.Prints;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Gui_Main extends JFrame implements Runnable {
    private Player player;
    private static Player playerHolder;
    private Camera camera;
    private Screen screen;
    private ArrayList<Texture> textures;
    private Game_Main game;
    private static Maps maps = new Maps();


    private static final long serialVersionUID = 1L;
    public int mapWidth = 15;
    public int mapHeight = 15;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;//define map in main file that way multiple maps can be created
    public static int[][] map = maps.getMaps(1);
    public static void setPlayerHolder(Player player) {
        playerHolder = player;
    }

    public void init(Player player) throws Exception {
        this.player = playerHolder;
        this.game = new Game_Main(player);
        textures = new ArrayList<Texture>();
        textures.add(Texture.wood);
        textures.add(Texture.brick);
        textures.add(Texture.bluestone);
        textures.add(Texture.stone);
        textures.add(Texture.sparkle);

        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66,game,player);
        addKeyListener(camera);
        addMouseMotionListener(camera);
        screen = new Screen(map, mapWidth, mapHeight, textures, 1920, 1080,player);

    }
    public void launch(Player player) throws Exception{
        this.init(player);
        thread = new Thread(this);
        image = new BufferedImage(1920,1080,BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        setSize(1920,1080);
        setResizable(false);
        setTitle("Buggy Dark Souls");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setVisible(true);
        start();
    }
    private synchronized void start() {
        running = true;
        thread.start();
    }
    private synchronized void stop() {
        running = false;
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; //times per sec
        double delta = 0;
        requestFocus();
        while(running){
            long now = System.nanoTime();
            delta = delta + ((now - lastTime) / ns);
            lastTime = now;
            while(delta >= 1){//only update every 60 seconds
                screen.update(camera, pixels);
                //Prints.println(player.getHealth());
                camera.update(map);
                delta--;
            }
            render();
        }
    }
}