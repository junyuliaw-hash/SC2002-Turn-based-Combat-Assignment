package items;
import java.util.List;

import entity.combatants.*;
import entity.statusEffect.PoisonEffect;

public class Poison implements Item {
    private String name = "Poison";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant player, List<Enemy> targets) {
        for (Enemy target : targets) {
            ((Combatant) target).addStatusEffect(new PoisonEffect());
        }
    }

    @Override
    public void use(Combatant player, Combatant target) {
        ((Combatant) target).addStatusEffect(new PoisonEffect());
    }

}
