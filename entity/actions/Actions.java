package entity.actions;

import entity.combatants.Combatant;
import entity.combatants.Enemy;

public interface Action {
    void execute(Combatant executor, Combatant target);
}
