package entity.items;

import entity.combatants.Player;

public class SmokeBomb implements Item {

    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public void use(Player target) {
        // Tells the Player to activate the Smoke Bomb status
        target.applySmokeBombEffect(); 
        
        System.out.println("Smoke Bomb thrown! Enemy attacks deal 0 damage this turn and the next turn.");
    }
}
