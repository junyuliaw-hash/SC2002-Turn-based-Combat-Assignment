package entity.actions;

import entity.combatants.Combatant;

public interface Action {
    void execute(Combatant executor, Combatant target);
}
