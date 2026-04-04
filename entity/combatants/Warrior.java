package entity.combatants;

import control.BattleEngine;
import entity.actions.ShieldBash;

public class Warrior extends Player {
    public Warrior() {
        this.name = "Warrior";
        this.maxHp = 260;
        this.hp = 260;
        this.attack = 40;
        this.defense = 20;
        this.speed = 30;
        this.specialSkill = new ShieldBash();
    }

    @Override
    public void takeTurn(BattleEngine engine) {
        // Player turn logic is driven by CombatMenu interactively
    }
}
