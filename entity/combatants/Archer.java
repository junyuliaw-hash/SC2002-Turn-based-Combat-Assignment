package entity.combatants;

import entity.actions.PoisonShot;

public class Archer extends Player {
    public Archer() {
        super("Archer", 220, 220, 45, 15, 35, new PoisonShot());
    }
}