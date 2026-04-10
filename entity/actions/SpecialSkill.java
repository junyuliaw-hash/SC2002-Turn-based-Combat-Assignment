package entity.actions;

import entity.combatants.Enemy;
import entity.combatants.Player;
import java.util.List;

public interface SpecialSkill {
    void execute(Player user, List<Enemy> targets);
    void updateCooldown();
    boolean isReady();
    int getCooldown();
    void resetCooldown();
    void refreshCooldown();
    String getName();
}
