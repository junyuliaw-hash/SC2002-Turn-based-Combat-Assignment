package entity.actions;

import java.util.List;

import entity.combatants.Combatant; 
import entity.combatants.Enemy;

public abstract class SpecialSkill implements Action {
	protected String name;
    protected int currentCooldown = 0;
    protected int maxCooldown = 3; 

	public SpecialSkill(String name) {
        this.name = name;
    }
    
    public abstract void execute(Combatant player, List<Enemy> targets);

    public void updateCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    public boolean isReady() {
        return currentCooldown == 0;
    }

    public int getCooldown() {
        return currentCooldown;
    }

    public void resetCooldown() {
        this.currentCooldown = maxCooldown; 
    }

    public String getName() {
        return name;
    }
}
