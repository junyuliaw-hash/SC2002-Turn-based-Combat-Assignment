package entity.combatants;

import entity.actions.ArcaneBlast;

public class Wizard extends Player {
    public Wizard() {
        super("Wizard", 200, 200, 50, 10, 20, new ArcaneBlast());
    }
}