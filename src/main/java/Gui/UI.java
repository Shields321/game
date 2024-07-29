package Gui;

import Entities.Player;

import java.awt.*;

public class UI {
    private final Player player;
    private final int width, height;
    private final int[] pixels;

    public UI(Player p, int[] pixels, int width, int height){
        this.player = p;
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public int[] displayHealth(){
        double fillPercent = (double) player.getHealth() / player.getMaxHealth();
        int barWidth = player.getMaxHealth()/2;
        int barHeight = 40;
        int filledWidth = (int) (barWidth * fillPercent);
        for (int y = 0; y < barHeight; y++){
            for (int x = 0; x < filledWidth; x++){
                int pixelIndex = y*width + x;
                if (pixelIndex < pixels.length){
                    pixels[pixelIndex] = Color.RED.getRGB();
                }
            }
        }
        return pixels;
    }
    public int[] displayMaxHealth(){
        int barWidth = player.getMaxHealth()/2;
        int barHeight = 40;
        for (int y = 0; y < barHeight; y++){
            for (int x = 0; x < barWidth; x++){
                int pixelIndex = y*width + x;
                if (pixelIndex < pixels.length){
                    pixels[pixelIndex] = Color.BLACK.getRGB();
                }
            }
        }
        return pixels;
    }
    public int[] displayStamina(){
        double fillPercent = (double) player.getStamina() / player.getMaxStamina();
        int barWidth = player.getMaxStamina()/2;
        int barHeight = 50;
        int filledWidth = (int) (barWidth * fillPercent);
        for (int y = 42; y < barHeight; y++){
            for (int x = 0; x < filledWidth; x++){
                int pixelIndex = y*width + x;
                if (pixelIndex < pixels.length){
                    pixels[pixelIndex] = Color.GREEN.getRGB();
                }
            }
        }
        return pixels;
    }
    public int[] displayMaxStamina(){
        int barWidth = player.getMaxStamina()/2;
        int barHeight = 50;
        for (int y = 42; y < barHeight; y++){
            for (int x = 0; x < barWidth; x++){
                int pixelIndex = y*width + x;
                if (pixelIndex < pixels.length){
                    pixels[pixelIndex] = Color.BLACK.getRGB();
                }
            }
        }
        return pixels;
    }
}
