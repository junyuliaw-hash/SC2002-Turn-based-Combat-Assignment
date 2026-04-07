package status;
import entity.combatants.*;
import java.util.List;

public class StunEffect extends StatusEffect {
    public StunEffect() {
        super(2, "Stun"); // duration is 2 turns
    }

    @Override
    public void apply(Combatant target) {
        target.addStatusEffect(this);
    }

    public void apply(List<Enemy> targets) {
        for (Enemy target : targets) {
            apply(target);
        }
    }

    @Override
    public void remove(Combatant target) {
        target.removeStatusEffect(this);
    }

    public void remove(List<Enemy> targets) {
        for (Enemy target : targets) {
            remove(target);
        }
    }

    @Override
    public void decreaseDuration() {
            duration--;
    }
}