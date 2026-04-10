package entity.statusEffect;
import java.util.List;

import entity.combatants.*;

public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect() { 
        super(2, "Smoke Bomb Invulnerability"); 
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
            duration--;
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