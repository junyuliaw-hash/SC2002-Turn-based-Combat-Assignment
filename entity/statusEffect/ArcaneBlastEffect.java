package entity.statusEffect;

import entity.combatants.Combatant;

public class ArcaneBlastEffect extends StatusEffect {
    public ArcaneBlastEffect() { 
        super(-1, "Arcane Blast"); // -1 or a large number for "until end of level"
    }

    @Override
    public void apply(Combatant target) {
        int currentAttack = target.getAttack();
        target.setAttack(currentAttack + 10);
    }

    @Override
    public void remove(Combatant target) {}
}