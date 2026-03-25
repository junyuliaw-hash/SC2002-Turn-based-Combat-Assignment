package entity.combatants;

import java.util.ArrayList;
import java.util.List;

import entity.statusEffect.StatusEffect;

public abstract class Combatant {
    protected String name;
    protected int hp, maxHp, attack, defense, speed;
    protected List<StatusEffect> activeEffects;

    public Combatant(String name, int hp, int attack, int defense, int speed){
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.activeEffects = new ArrayList<>();
    }

    public abstract void takeTurn();
    
    public void applyDamage(int rawDmg){
        int finalDmg = Math.max(0, rawDmg - this.defense);
        this.hp = Math.max(0, this.hp - finalDmg);
    }

    public void heal(int amount){
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }

    public void addStatusEffect(StatusEffect effect){
        this.activeEffects.add(effect);
        effect.apply(this);
    }

    public boolean hasStatusEffect(String effectName){
        for (StatusEffect effect:activeEffects){
            if (effect.getEffectName().equals(effectName)){
                return true;
            }
        }
        return false;
    }

    public int getHp(){return this.hp;}
    public int getMaxHp(){return this.maxHp;}
    public int getAttack(){return this.attack;}
    public int getDefense(){return this.defense;}
    public int getSpeed(){return this.speed;}
    public String getName(){return name;}

    public void setHp(int hp){this.hp = hp;}
    public void setAttack(int attack){this.attack = attack;}
    public void setDefense(int defense){this.defense = defense;}
    public void setSpeed(int speed){this.speed = speed;}

    public boolean isAlive(){
        return this.hp > 0;
    }
}
