package status;

import java.lang.annotation.Target;

public class PoisonEffect {
    private String name = "Poison Effect";
    private int damagePerTurn = 20;
    private int numTurns = 1;
    
    @Override
    public void apply(Combatant target) {
        target.applyDamage(damagePerTurn);
        System.out.println(target.getName() + " takes " + damagePerTurn + " poison damage.");
        target.addStatusEffect(this);
    }

    @Override
    public void remove(Combatant target) {
        System.out.println(target.getName() + " is no longer poisoned.");
        target.removeStatusEffect(this);
    }

    @Override
    public void decreaseDuration(Combatant target) {
        numTurns--;
        target.applyDamage(damagePerTurn);
        System.out.println(target.getName() + " takes " + damagePerTurn + " poison damage.");
        
}
