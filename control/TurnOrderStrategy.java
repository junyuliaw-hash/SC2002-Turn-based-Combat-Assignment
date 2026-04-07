package strategy;

import model.combatant.Combatant;
import java.util.List;

/**
 * TurnOrderStrategy: Strategy interface for determining the order in which
 * combatants act each round.
 *
 * OCP / DIP — BattleEngine depends on this abstraction; new strategies
 * (e.g. random order, initiative roll) can be added without touching the engine.
 */
public interface TurnOrderStrategy {

    /**
     * Given the full list of combatants at the start of a round, returns them
     * in the order they should act.
     *
     * @param combatants all participants (alive and dead — callers must filter)
     * @return ordered list for this round
     */
    List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
