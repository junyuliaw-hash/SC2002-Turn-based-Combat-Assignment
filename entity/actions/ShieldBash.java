package entity.actions;

import boundary.CombatMenu;
import entity.combatants.Enemy;
import entity.combatants.Player;
import entity.statusEffect.StunEffect;
import java.util.List;

public class ShieldBash extends AbstractSkill {
    public ShieldBash() {
        this.name = "Shield Bash";
    }

    @Override
    public void execute(Player user, List<Enemy> targets) {
        if (targets.isEmpty()) return;
        Enemy target = new CombatMenu().promptTarget(targets); // Targets selected enemy
        int damage = target.applyDamage(user.getAttack());
        target.addStatusEffect(new StunEffect());
        System.out.println(user.getName() + " uses Shield Bash on " + target.getName() + " for " + damage + " damage! Target STUNNED.");
    }
}
