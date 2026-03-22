package domain.statusEffect;

import domain.combatants.Combatant;

public class DefendEffect extends StatusEffect {
    public DefendEffect() { 
        super(2, "Defend"); 
    }

    @Override
    public void apply(Combatant target) {
        int currentDefense = target.getDefense();
        target.setDefense(currentDefense + 10);
    }

    @Override
    public void remove(Combatant target) {
        int currentDefense = target.getDefense();
        target.setDefense(currentDefense - 10);
    }
}