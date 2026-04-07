package entity.actions;

import entity.combatants.Combatant;
import entity.combatants.Enemy;
import entity.statusEffect.DefendEffect;

public class Defend implements Action {
    @Override
    public void execute(Combatant player, Enemy enemy) {
        System.out.println(player.getName() + " takes a defensive stance!");
        player.hasEffect(new DefendEffect()); 
    }
}
