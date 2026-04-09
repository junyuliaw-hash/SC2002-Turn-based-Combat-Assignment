package entity.items;
import entity.combatants.*;
import java.util.List;

public interface Item {
        String getName();
        void use(Combatant player, Combatant target);
        void use(Combatant player, List<Enemy> targets);
}
// sync
