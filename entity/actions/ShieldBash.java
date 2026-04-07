package entity.actions;

import java.util.List;

import entity.combatants.Combatant;
import entity.combatants.Enemy;
import entity.statusEffect.StunEffect;

public class ShieldBash extends SpecialSkill {
    public ShieldBash() {
        super("Shield Bash");
    }

    @Override
    public void execute(Combatant player, List<Enemy> targets) {
        if (targets.isEmpty()) return;
        
        Enemy target = targets.get(0);  
        
        int damage = Math.max(0, player.getAttack() - target.getDefense());
        target.takeDamage(damage);
        
        target.hasEffect(new StunEffect(2)); 
        
        System.out.println(player.getName() + " uses Shield Bash on " + target.getName() + "!");
        System.out.println(target.getName() + " took " + damage + " damage and is STUNNED.");
    }

	@Override
	public void execute(Combatant player, Enemy enemy) {
	}
}
