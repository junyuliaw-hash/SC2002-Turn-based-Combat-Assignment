package items;

public class Potion implements Item {
    private String name = "Potion";
    private int healAmount = 100;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant player, Combatant target) {
        player.heal(healAmount);
        System.out.println(player.getName() + " used Potion! Healed for " + healAmount + " HP.");
    }
}
