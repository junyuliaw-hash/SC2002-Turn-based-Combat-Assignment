package status;
import java.util.List;

import entity.combatants.*;


public class ArcaneBlastEffect extends StatusEffect {
    private int attackBoost = 10;
    public ArcaneBlastEffect() { 
        super(-1, "Arcane Blast"); // -1 or a large number for "until end of level"
    }

    @Override
    public void apply(Combatant target) {
        int currentAttack = target.getAttack();
        target.setAttack(currentAttack + attackBoost);
        target.addStatusEffect(this);
    }

    @Override
    public void remove(Combatant target) {
        target.removeStatusEffect(this);
        int currentAttack = target.getAttack();
        target.setAttack(currentAttack - attackBoost);}

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
