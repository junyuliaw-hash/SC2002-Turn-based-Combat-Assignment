package control;

import java.util.List;
import java.util.ArrayList;
import entity.combatants.Combatant;

public class TurnManager {

    private List<Combatant> turnOrder; 
    private BattleEngine engine;
    private Combatant currentActor;

    public TurnManager(BattleEngine engine) {
        this.engine = engine;
        this.turnOrder = new ArrayList<>();
    }

    public void setTurnOrder(List<Combatant> turnOrder) {
        this.turnOrder = turnOrder;
    }

    public void processRound() {
        for (Combatant combatant : turnOrder) {
            if (!isBattleActive()) {
                break; 
            }
            
            if (combatant.getHp() > 0) { 
                this.currentActor = combatant;
                executeTurn();
            }
        }
    }

    private void executeTurn() {
    }

    private boolean isBattleActive() {
        return !engine.checkGameEndCondition(); 
    }
}