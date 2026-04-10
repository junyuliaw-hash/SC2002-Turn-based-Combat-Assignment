package entity.combatants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import control.BattleEngine;
import entity.statusEffect.*; // Import the status package to access PoisonEffect

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
        if (effect != null && !activeEffects.contains(effect)) {
            activeEffects.add(effect);
        }
    }

    public void removeStatusEffect(StatusEffect effect) {
        activeEffects.remove(effect);
    }

    /**
     * Updates effects every turn. 
     * Modified to trigger damage-over-time for PoisonEffect specifically.
     */
    public void updateEffects(){
        Iterator<StatusEffect> it = activeEffects.iterator();
        while (it.hasNext()){
            StatusEffect effect = it.next();
            
            // Logic Change: If the effect is Poison, use its specific damage-per-turn method
            if (effect instanceof PoisonEffect) {
                ((PoisonEffect) effect).decreaseDuration(this);
            } else {
                effect.decreaseDuration();
            }

            // Standard check: remove effect if duration is over
            if (effect.getDuration() <= 0){
                effect.remove(this);
            }
        }
    }

    public boolean hasEffect(String effectName) {
        return activeEffects.stream().anyMatch(e -> e.getName().equalsIgnoreCase(effectName));
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
    public void setHp(int hp){this.hp = hp;}
}