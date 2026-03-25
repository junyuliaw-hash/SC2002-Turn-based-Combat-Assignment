package entity.statusEffect;

import entity.combatants.Combatant;

public class StunEffect extends StatusEffect {
    public StunEffect() {
        super(2, "Stun"); // duration is 2 turns
    }

    @Override
    public void apply(Combatant target) {}

    @Override
    public void remove(Combatant target) {}
}