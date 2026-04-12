package entity.combatants;

import entity.actions.ShieldBash;

public class Warrior extends Player {
    public Warrior() {
        super("Warrior", 260, 260, 40, 20, 30, new ShieldBash());
    }
}
