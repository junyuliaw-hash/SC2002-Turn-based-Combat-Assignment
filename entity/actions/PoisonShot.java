package entity.actions;

import boundary.CombatMenu;
import entity.combatants.Enemy;
import entity.combatants.Player;
import entity.statusEffect.PoisonEffect;
import java.util.List;

public class PoisonShot extends AbstractSkill {
    public PoisonShot() {
        this.name = "Poison Shot";
    }

    @Override
    public void execute(Player user, List<Enemy> targets) {
        if (targets.isEmpty()) return;
        Enemy target = new CombatMenu().promptTarget(targets); // Target selected enemy
        int damage = target.applyDamage(user.getAttack());
        
        PoisonEffect poison = new PoisonEffect();
        poison.apply(target);
        System.out.println(user.getName() + " uses Poison Shot on " + target.getName() + " for " + damage + " damage.");
    }
}
