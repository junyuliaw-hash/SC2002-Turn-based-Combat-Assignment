package entity.combatants;

import control.BattleEngine

public abstract class Enemy extends Combatant {
    public void performBasicAttack(Player target){
        if (target.hasEffect("Smoke Bomb Invulnerability")) {
            return;
        }
        target.applyDamage(this.attack);
    }

    public void takeTurn(BattleEngine engine){
        if (this.hasEffect("Stun")){
            return;
        }
        performBasicAttack(engine.getPlayer());
    }
}
