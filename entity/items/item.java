package entity.items;

import domain.combatants.Player;

public interface Item {
    String getName();
    void use(Player target);
}
