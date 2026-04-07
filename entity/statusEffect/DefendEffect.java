package status;

public class DefendEffect extends StatusEffect {
    private int defenseBoost = 10;
    public DefendEffect() { 
        super(2, "Defend"); 
    }

    @Override
    public void apply(Combatant target) {
        int currentDefense = target.getDefense();
        target.setDefense(currentDefense + defenseBoost);
    }

    @Override
    public void remove(Combatant target) {
        int currentDefense = target.getDefense();
        target.setDefense(currentDefense - defenseBoost);
    }
}