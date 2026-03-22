package domain.statusEffect;
package domain.combatants;

public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect() { 
        super(2, "Smoke Bomb Invulnerability"); 
    }

    @Override
    public void apply(Combatant target) {}

    @Override
    public void remove(Combatant target) {}
}