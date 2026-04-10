package entity.items;

import entity.combatants.*;
import entity.statusEffect.SmokeBombEffect;
import java.util.List;

public class SmokeBomb implements Item {
    private String name = "Smoke Bomb";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant player, List<Enemy> targets) {
        SmokeBombEffect smoke = new SmokeBombEffect();
        smoke.apply(player);
        System.out.println(player.getName() + " used Smoke Bomb! Enemy attacks deal 0 damage for 2 turns.");
    }

    @Override
    public void use(Combatant player, Combatant target) {
        SmokeBombEffect smoke = new SmokeBombEffect();
        smoke.apply(player);
        System.out.println(player.getName() + " used Smoke Bomb!");
    }
}
