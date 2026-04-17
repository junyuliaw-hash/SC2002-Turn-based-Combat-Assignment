package entity.statusEffect;

import entity.combatants.*;
import java.util.List;

public class PoisonEffect extends StatusEffect {
    private int firstTickDamage = 20;
    private int secondTickDamage = 10;

    public PoisonEffect() {
        super(2, "Poison Effect");
    }

    @Override
    public void apply(Combatant target) {
        System.out.println("  " + target.getName() + " is poisoned. It will take 20 damage next round and 10 damage after.");
        target.addStatusEffect(this);
    }

    @Override
    public void remove(Combatant target) {
        System.out.println("  " + target.getName() + " is no longer poisoned.");
    }

    // Required by parent class
    @Override
    public void apply(List<Enemy> targets) {
        for (Enemy target : targets) {
            new PoisonEffect().apply(target);
        }
    }

    // Required by parent class
    @Override
    public void remove(List<Enemy> targets) {
        for (Enemy target : targets) {
            remove(target);
        }
    }

    public void decreaseDuration(Combatant target) {
        int damage = duration == 2 ? firstTickDamage : secondTickDamage;
        super.decreaseDuration();
        target.applyDamage(damage + target.getDefense());
        System.out.println("  " + target.getName() + " takes " + damage + " poison damage from the active effect.");
    }
}
