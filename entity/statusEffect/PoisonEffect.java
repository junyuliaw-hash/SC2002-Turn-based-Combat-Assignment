package entity.statusEffect;

import entity.combatants.*;
import java.util.List;

public class PoisonEffect extends StatusEffect {
    
    private int damagePerTurn = 20;

    // Call the parent constructor: duration of 1 turn (or more if you prefer), name "Poison"
    public PoisonEffect() {
        super(2, "Poison Effect");
    }

    @Override
    public void apply(Combatant target) {
        target.applyDamage(damagePerTurn + target.getDefense());
        System.out.println("  " + target.getName() + " takes " + damagePerTurn + " poison damage upon application.");
        target.addStatusEffect(this); // Changed from PoisonEffect.this
    }

    @Override
    public void remove(Combatant target) {
        System.out.println(target.getName() + " is no longer poisoned.");
    }

    // Required by parent class
    @Override
    public void apply(List<Enemy> targets) {
        for (Enemy target : targets) {
            apply(target);
        }
    }

    // Required by parent class
    @Override
    public void remove(List<Enemy> targets) {
        for (Enemy target : targets) {
            remove(target);
        }
    }

    // Custom method called in Combatant.java
    public void decreaseDuration(Combatant target) {
        super.decreaseDuration(); // reduces the turn count
        target.applyDamage(damagePerTurn + target.getDefense());
        System.out.println("  " + target.getName() + " takes " + damagePerTurn + " poison damage from the active effect.");
    }
}