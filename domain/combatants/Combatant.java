package domain.combatants;

public abstract class Combatant {
    protected String name;
    protected int hp, maxHp, attack, defense, speed;

    public Combatant(String name, int hp, int attack, int defense, int speed){
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public abstract void takeTurn();
    
    public void applyDamage(int rawDmg){
        int finalDmg = Math.max(0, rawDmg - this.defense);
        this.hp = Math.max(0, this.hp - finalDmg);
    }
    public void heal(int amount){
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }

    public int getHp(){return this.hp;}
    public int getSpeed(){return this.speed;}
    public String getName(){return name;}

    public boolean isAlive(){
        return this.hp > 0;
    }
}
