package entity.statusEffect;
import java.util.List;

import entity.combatants.*;

public class SmokeBombEffect extends StatusEffect {
    private boolean justApplied = true;

    public SmokeBombEffect() { 
        super(1, "Smoke Bomb Invulnerability"); 
    }

    @Override
    public void apply(Combatant target) {
        target.addStatusEffect(this);
    }

    @Override
    public void remove(Combatant target) {
    }

    @Override
    public void decreaseDuration() {
        if (justApplied) {
            justApplied = false;
            return;
        }
        super.decreaseDuration();
    }

    @Override
    public void apply(List<Enemy> targets) {
        for (Enemy target : targets) {
            apply(target);
        }
    }

    @Override
    public void remove(List<Enemy> targets) {
        for (Enemy target : targets) {
            remove(target);
        }
    }
}
