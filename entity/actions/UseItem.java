package entity.actions;

import java.util.List;

import entity.combatants.Combatant; 
import entity.combatants.Enemy; 
import entity.items.Item; 

public class UseItem implements Action {
    private Item item;
    private List<Combatant> allEnemies;

    public UseItem(Item item, List<Combatant> allEnemies) {
        this.item = item;
        this.allEnemies = allEnemies;
    }

    @Override
    public void execute(Combatant player, Enemy enemy) {
        if (item != null) {
            item.use(player, allEnemies); 
        }
    }
}
