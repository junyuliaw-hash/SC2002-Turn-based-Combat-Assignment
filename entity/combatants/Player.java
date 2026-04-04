package entity.combatants;

import java.util.List;
import java.util.ArrayList;

import control.BattleEngine;
import entity.items.Item;
import entity.actions.SpecialSkill;
import entity.statusEffect.DefendEffect;

public abstract class Player extends Combatant {
    protected List<Item> inventory = new ArrayList<>();
    protected SpecialSkill specialSkill;

    public void addItem(Item item){
        if (inventory.size() < 2){
            inventory.add(item);
        }
    }

    public void performBasicAttack(Enemy target){
        int damage = Math.max(0, this.attack - target.getDefense());
        target.applyDamage(damage);
        System.out.println(this.name + " attacks " + target.getName() + " for " + damage + " damage!");
    }

    public void performDefend(){
        System.out.println(this.name + " defends!");
        this.addStatusEffect(new DefendEffect());
    }

    public void useItem(int itemIndex, List<Enemy> enemies){
        if (itemIndex >= 0 && itemIndex < inventory.size()){
            Item item = inventory.remove(itemIndex);
            item.use(this, enemies);
        }
    }

    public void useSpecialSkill(List<Enemy> targets){
        if (specialSkill.isReady()){
            specialSkill.execute(this, targets);
            specialSkill.resetCooldown();
        }else{
            System.out.println("Skill is on cooldown!");
        }
    }

    public List<Item> getInventory(){return inventory;}
    public SpecialSkill getSpecialSkill(){return specialSkill;}
}
