package domain.items;

import domain.combatants.Player;

public class PowerStone implements Item {

    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public void use(Player target) {
        System.out.println("Power Stone used! Triggering special skill for free.");
        
        // Forces the player to use their special skill [cite: 82]
        target.useSpecialSkill(); 
    }
}
