package Game;

import player.Player;

public class Game_Main {
    private final Player player;

    public Game_Main(Player p) {
        this.player = p;
    }
    public void updateHp(){
        player.setHealth(player.getHealth()-5);
    }
    public void updateStaminaAfterForwardMovement(){
        player.setStamina(player.getStamina()-5);
    }
}
