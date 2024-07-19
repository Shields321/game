package player;

import usages.Prints;

public class Player {    
    private int UID;
    private int health;
    private int stamina;
    private int damage;

    public Player(int UID, int health, int stamina, int damage) {
        
        this.UID = UID;
        this.health = health;
        this.stamina = stamina;
        this.damage = damage;
    }
    //create getters and setters    
    public int getUID() {
        return UID;
    }
    public void setUID(int UID) {
        this.UID = UID;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getStamina() {
        return stamina;
    }
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
