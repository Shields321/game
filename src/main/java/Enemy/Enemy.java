package Enemy;

public class Enemy {
    public int health,maxHealth;

    public Enemy(int hp, int maxHP){
        this.health = hp;
        this.maxHealth = maxHP;
    }
    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}
    public int getMaxHealth() {return health;}
    public void setMaxHealth(int health) {this.health = health;}
}
