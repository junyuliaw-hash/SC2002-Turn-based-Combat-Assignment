package entity.actions;

import domain.combatants.Enemy;
import domain.combatants.Player;
import java.util.List;

public interface SpecialSkill {
    void execute(Player user, List<Enemy> targets);
    void updateCooldown();
    boolean isReady();
    int getCooldown();
    void resetCooldown();
    String getName();
}
