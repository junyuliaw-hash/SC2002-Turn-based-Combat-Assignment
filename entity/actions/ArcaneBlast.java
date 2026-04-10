package entity.actions;

import entity.combatants.Enemy;
import entity.combatants.Player;
import entity.statusEffect.ArcaneBlastEffect;
import java.util.List;
import java.util.ArrayList;

public class ArcaneBlast extends AbstractSkill {
    public ArcaneBlast() {
        this.name = "Arcane Blast";
    }

    @Override
    public void execute(Player user, List<Enemy> targets) {
        System.out.println(user.getName() + " uses Arcane Blast!");
        int kills = 0;
        List<Enemy> targetsCopy = new ArrayList<>(targets);
        for (Enemy target : targetsCopy) {
            int damage = Math.max(0, user.getAttack() - target.getDefense());
            target.applyDamage(damage);
            System.out.println("Dealt " + damage + " damage to " + target.getName() + ".");
            if (!target.isAlive()) {
                kills++;
            }
        }
        if (kills > 0) {
            int bonus = kills * 10;
            new ArcaneBlastEffect().apply(user);;
            System.out.println("Arcane Blast defeated " + kills + " enemies! Attack increased by " + bonus + ".");
        }
    }
}
