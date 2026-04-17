package entity.items;
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
        List<Enemy> aliveTargets = targets.stream().filter(Enemy::isAlive).toList();
        if (aliveTargets.isEmpty()) {
            System.out.println(player.getName() + " used Poison, but there are no enemies to affect.");
            return;
        }
        new PoisonEffect().apply(aliveTargets);
        System.out.println(player.getName() + " used Poison! All enemies are affected.");
    }

    @Override
    public void use(Combatant player, Combatant target) {
        ((Combatant) target).addStatusEffect(new PoisonEffect());
    }

}
