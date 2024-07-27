package main.java;

import usages.Prints;
import player.Player;
import Enemy.Enemy;
import Gui.Gui_Main;
import ReadFiles.PlayerStats;
import java.io.IOException;

public class Main {
    public static String configPath = "config.json";
    public static void main(String[] args) throws Exception {

        Player player = initializePlayer();
        Enemy enemy = initializeEnemy();
        Gui_Main.setPlayerHolder(player);
        Gui_Main gui = new Gui_Main();
        gui.launch(player);
    }
    public static Player initializePlayer() throws IOException {
        PlayerStats playerStats = new PlayerStats();
        return playerStats.readPlayerStats(configPath);
    }
    public static Enemy initializeEnemy() throws IOException {
        PlayerStats playerStats = new PlayerStats();
        return playerStats.readEnemyStats(configPath);
    }
}