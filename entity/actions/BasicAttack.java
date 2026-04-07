package entity.actions;

import entity.combatants.Combatant; 
import entity.combatants.Enemy; 

public class BasicAttack implements Action {
    @Override
    public void execute(Combatant executor, Combatant target) {
        int damage = Math.max(0, executor.getAttack() - target.getDefense());
        
        if (target.activeEffects.stream().anyMatch(e -> e instanceof SmokeBombEffect)) {
            damage = 0;
            System.out.println(target.getName() + " is hidden in smoke! 0 damage taken.");
        } else {
            target.takeDamage(damage);
            System.out.println(executor.getName() + " attacks " + target.getName() + " for " + damage + " damage!");
        }
    }
}
