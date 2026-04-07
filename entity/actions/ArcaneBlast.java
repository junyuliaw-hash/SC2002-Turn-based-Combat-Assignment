package entity.actions;

import java.util.List;

import entity.combatants.Combatant;
import entity.combatants.Enemy;

public class ArcaneBlast extends SpecialSkill {
    public ArcaneBlast() {
        super("Arcane Blast");
    }

    @Override
    public void execute(Combatant player, List<Enemy> targets) {
        System.out.println(player.getName() + " releases an Arcane Blast!");
        int enemiesDefeated = 0;
 
        for (Enemy enemy : targets) {
            if (!enemy.isAlive()) continue;

            int damage = Math.max(0, player.getAttack() - enemy.getDefense()); 
            enemy.takeDamage(damage);
            System.out.println(enemy.getName() + " took " + damage + " damage.");

            if (!enemy.isAlive()) {
                enemiesDefeated++;
            }
        }
 
        if (enemiesDefeated > 0) {
            int buff = enemiesDefeated * 10;
            player.setAttack(player.getAttack() + buff);
            System.out.println("Wizard gained +" + buff + " Attack from defeated souls!");
        }
    }

	@Override
	public void execute(Combatant player, Enemy enemy) {
	}

}
