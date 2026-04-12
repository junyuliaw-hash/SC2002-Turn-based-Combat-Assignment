package entity.combatants;

public class Wolf extends Enemy{
    private static int idCounterWolf = 1;
    
    public Wolf() {
        super("Wolf", 40, 40, 45, 5, 35);
    }
}
