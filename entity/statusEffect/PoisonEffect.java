package status;

import java.lang.annotation.Target;
import entity.combatants.*;


public class PoisonEffect implements StatusEffect {
    private String name = "Poison Effect";
    private int damagePerTurn = 20;
    private int numTurns = 1;
    
    public void apply(Combatant target) {
        target.applyDamage(damagePerTurn);
        System.out.println(target.getName() + " takes " + damagePerTurn + " poison damage.");
        target.addStatusEffect(PoisonEffect.this);
    }

    public void remove(Combatant target) {
        System.out.println(target.getName() + " is no longer poisoned.");
        target.removeStatusEffect(this);
    }

    public void decreaseDuration(Combatant target) {
        numTurns--;
        target.applyDamage(damagePerTurn);
        System.out.println(target.getName() + " takes " + damagePerTurn + " poison damage.");
        
}
