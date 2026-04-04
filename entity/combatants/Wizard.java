package entity.combatants;

import control.BattleEngine;
import entity.actions.ArcaneBlast;

public class Wizard extends Player {
    public Wizard() {
        this.name = "Wizard";
        this.maxHp = 200;
        this.hp = 200;
        this.attack = 50;
        this.defense = 10;
        this.speed = 20;
        this.specialSkill = new ArcaneBlast();
    }

    @Override
    public void takeTurn(BattleEngine engine) {
        // Player turn logic is driven by CombatMenu interactively
    }
}