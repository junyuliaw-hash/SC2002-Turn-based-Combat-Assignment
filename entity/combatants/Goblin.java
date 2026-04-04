package entity.combatants;

public class Goblin extends Enemy {
    private static int idCounterGoblin = 1;
    
    public Goblin() {
        this.name = "Goblin " + (idCounterGoblin++);
        this.maxHp = 55;
        this.hp = 55;
        this.attack = 35;
        this.defense = 15;
        this.speed = 25;
    }
}
