package entity.items;
import entity.combatants.*;
import java.util.List;

public class Potion implements Item {
    private String name = "Potion";
    private int healAmount = 100;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant player, List<Enemy> targets) {
        player.heal(healAmount);
        System.out.println(player.getName() + " used Potion! Healed for " + healAmount + " HP.");
    }

    @Override
    public void use(Combatant player, Combatant target) {
        ((Combatant) target).heal(healAmount);
        System.out.println(player.getName() + " used Potion on " + target.getName() + "! Healed for " + healAmount + " HP.");

    }
}
