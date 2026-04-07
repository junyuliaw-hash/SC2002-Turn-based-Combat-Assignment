package entity.combatants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import control.BattleEngine;
import entity.statusEffect.StatusEffect;

public abstract class Combatant {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected List<StatusEffect> activeEffects = new ArrayList<>();
    
    public abstract void takeTurn(BattleEngine engine);
    
    public void applyDamage(int rawDmg){
        int finalDmg = Math.max(0, rawDmg - this.defense);
        this.hp = Math.max(0, this.hp - finalDmg);
    }

    public void heal(int amount){
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }

    public void addStatusEffect(StatusEffect effect){
        activeEffects.add(effect);
        effect.apply(this);
    }

    public void updateEffects(){
        Iterator<StatusEffect> it = activeEffects.iterator();
        while (it.hasNext()){
            StatusEffect effect = it.next();
            effect.decreaseDuration();
            if (effect.getDuration() <= 0){
                effect.remove(this);
                it.remove();
            }
        }
    }

    public boolean hasEffect(String effectName) {
        return activeEffects.stream().anyMatch(e -> e.getName().equals(effectName));
    }

    public boolean isAlive(){return hp > 0;}
    public String getName(){return name;}
    public int getHp(){return hp;}
    public int getMaxHp(){return maxHp;}
    public int getAttack(){return attack;}
    public int getDefense(){return defense;}
    public int getSpeed(){return speed;}
    public void setAttack(int attack){this.attack = attack;}
    public void setDefense(int defense){this.defense = defense;}
}
