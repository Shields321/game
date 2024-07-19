package src.main.java;

import usages.Prints;
import player.Player;
import Gui.Gui_Main;
import ReadFiles.PlayerStats;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws Exception {
        Player player = initializePlayer();        
        Gui_Main.setPlayerHolder(player);
        Gui_Main gui = new Gui_Main();
        gui.launch();
    }
    public static Player initializePlayer() throws IOException {
        PlayerStats playerStats = new PlayerStats();
        return playerStats.readPlayerStats("config.json");
    }
}