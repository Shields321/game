package player;

import usages.Prints;

public class Player {    
    private int UID;
    private int health;
    private int maxHealth;
    private int stamina;
    private int maxStamina;
    private int damage;

    public Player(int UID, int health,int maxHealth, int stamina, int maxStamina, int damage) {
        
        this.UID = UID;
        this.health = health;
        this.maxHealth = maxHealth;
        this.stamina = stamina;
        this.maxStamina = maxStamina;
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
    public int getMaxHealth() {return maxHealth;}
    public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}
    public int getStamina() {
        return stamina;
    }
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
    public int getMaxStamina() {return maxStamina;}
    public void setMaxStamina(int maxStamina) {this.maxStamina = maxStamina;}
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
