package entity.items;

import entity.combatants.Player;

public interface Item {
    String getName();
    void use(Player target);
}
