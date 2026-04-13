package entity.combatants;

import control.BattleEngine;

public class Enemy extends Combatant {
    public Enemy(String n, int mHp, int hp, int a, int d, int s){
        this.name = n;
        this.maxHp = mHp;
        this.hp = hp;
        this.attack = a;
        this.defense = d;
        this.speed = s;
    }

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

    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}
}
