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

    public int performBasicAttack(Player target){
        if (target.hasEffect("Smoke Bomb Invulnerability")) {
            return 0;
        }
        return target.applyDamage(this.attack);
    }

    public void takeTurn(BattleEngine engine){
        if (this.hasEffect("Stun")){
            return;
        }
        Player target = engine.getPlayer();
        int damage = performBasicAttack(target);
        if (damage == 0 && target.hasEffect("Smoke Bomb Invulnerability")) {
            engine.getUI().displayMessage(getName() + " attacks, but the Smoke Bomb blocks the hit.");
        } else {
            engine.getUI().displayMessage(getName() + " attacks " + target.getName() + " for " + damage + " damage.");
        }
    }

    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}
}
