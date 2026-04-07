package entity.actions;

import entity.combatants.Combatant; 
import entity.combatants.Enemy; 

public class BasicAttack implements Action {
    @Override
    public void execute(Combatant player, Enemy enemy) {
        int damage = Math.max(0, player.getAttack() - enemy.getDefense());
        
        if (player.activeEffects.stream().anyMatch(e -> e instanceof SmokeBombEffect)) {
            damage = 0;
            System.out.println(player.getName() + " is hidden in smoke! 0 damage taken.");
        } else {
            enemy.takeDamage(damage);
            System.out.println(player.getName() + " attacks " + player.getName() + " for " + damage + " damage!");
        }
    }
}
