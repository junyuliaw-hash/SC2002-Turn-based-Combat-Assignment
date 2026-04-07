package entity.actions;

import entity.combatants.Combatant;
import entity.statusEffect.DefendEffect;

public class Defend implements Action {
    @Override
    public void execute(Combatant executor, Combatant target) {
        System.out.println(executor.getName() + " takes a defensive stance!");
        executor.hasEffect(new DefendEffect()); 
    }
}
