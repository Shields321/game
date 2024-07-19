package ReadFiles;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import player.Player;
import usages.Prints;

public class PlayerStats {
    private Player player;

    public Player readPlayerStats(String filename) throws IOException {
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            JsonObject jsonObject = gson.fromJson(br, JsonObject.class);
            player = gson.fromJson(jsonObject.getAsJsonObject("playerStats"), Player.class);
        }
        catch(Exception e){
            e.printStackTrace(); //only for debugging
            Prints.print("can't read player stats file");
        }
        return player;
    }
}
