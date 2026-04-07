package control;

import entity.combatants.Combatant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * SpeedBasedTurnOrder: Sorts combatants by Speed descending (higher speed acts first).
 * Ties are broken by insertion order (stable sort).
 *
 * OCP — swap this out for any other TurnOrderStrategy without touching BattleEngine.
 */
public class SpeedBasedStrategy implements TurnOrderStrategy {

    @Override
    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);
        // Stable sort: equal-speed combatants keep their original relative order.
        ordered.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return ordered;
    }
}
