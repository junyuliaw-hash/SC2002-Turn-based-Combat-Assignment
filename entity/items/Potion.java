package entity.items;

import entity.combatants.Player;

public class Potion implements Item {

    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public void use(Player target) {
        int healAmount = 100;
        int currentHp = target.getHp();
        int maxHp = target.getMaxHp();
        
        // Clamps the HP so it doesn't go over the maximum [cite: 82]
        int newHp = Math.min(currentHp + healAmount, maxHp);
        target.setHp(newHp);
        
        System.out.println("Potion used! Healed for " + healAmount + " HP.");
        System.out.println("Current HP is now: " + target.getHp() + "/" + maxHp);
    }
}
