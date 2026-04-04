package entity.combatants;

public class Wolf extends Enemy{
    private static int idCounterWolf = 1;
    
    public Wolf() {
        this.name = "Wolf " + (idCounterWolf++);
        this.maxHp = 40;
        this.hp = 40;
        this.attack = 45;
        this.defense = 5;
        this.speed = 35;
    }
}
