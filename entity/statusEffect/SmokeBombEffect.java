package status;

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
        target.removeStatusEffect(this);
    }

    @Override
    public void decreaseDuration() {
            duration--;
    }
}