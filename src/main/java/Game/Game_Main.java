package Game;

import player.Player;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Game_Main {
    private final Player player;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> staminaRecoveryTask;

    public Game_Main(Player p) {
        this.player = p;
        startStaminaRecovery();
    }
    public void updateHp(){
        player.setHealth(player.getHealth()-player.getDamage());
    }
    public void updateStaminaAfterForwardMovement(){
        if (player.getStamina() >= 0) {
            player.setStamina(player.getStamina() - 5);
        }
    }

    public void gainStaminaAfterTime(){
        if (player.getStamina() < player.getMaxStamina()){
            player.setStamina(player.getStamina()+5);
        }
    }
    private void startStaminaRecovery() {
        resetStaminaRecoveryTask();
    }

    public void resetStaminaRecoveryTask() {
        if (staminaRecoveryTask != null) {
            staminaRecoveryTask.cancel(false);
        }
        staminaRecoveryTask = scheduler.scheduleAtFixedRate(this::gainStaminaAfterTime, 5, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
