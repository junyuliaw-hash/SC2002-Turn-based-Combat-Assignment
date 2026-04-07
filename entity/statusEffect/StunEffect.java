package status;

public class StunEffect extends StatusEffect {
    public StunEffect() {
        super(2, "Stun"); // duration is 2 turns
    }

    @Override
    public void apply(Combatant target) {
        target.addStatusEffect(this);
    }

    @Override
    public void remove(Combatant target) {
        target.removeStatusEffect(this);
    }

    @Override
    public void decreaseDuration() {
            duration--;
    }
}