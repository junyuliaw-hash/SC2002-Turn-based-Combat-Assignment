package items;

public class Poison implements Item {
    private String name = "Poison";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant player, Combatant target) {
        target.addStatusEffect(new status.PoisonEffect());
    }

}
