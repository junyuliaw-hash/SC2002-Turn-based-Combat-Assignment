package entity.combatants;

import boundary.CombatMenu;
import control.BattleEngine;
import entity.items.Item;
import entity.actions.SpecialSkill;
import entity.statusEffect.DefendEffect;

import java.util.List;
import java.util.ArrayList;

public abstract class Player extends Combatant {
    protected List<Item> inventory = new ArrayList<>();
    protected SpecialSkill specialSkill;

    public void takeTurn(BattleEngine engine){
        CombatMenu ui = engine.getUI();
        List<Enemy> enemies = engine.getEnemies();
        tickCooldown();
        int actionChoice = ui.promptAction(this);

        switch (actionChoice) {
            case 1:
                Enemy target = ui.promptTarget(enemies);
                if (target != null){
                    performBasicAttack(target);
                }
                break;
                
            case 2: // Defend
                performDefend();
                break;
                
            case 3: // Use Item
                int itemIndex = ui.promptItemChoice(this);
                useItem(itemIndex, enemies);
                break;
                
            case 4: // Special Skill
                if (specialSkill != null && specialSkill.isReady()){
                    List<Enemy> aliveEnemies = enemies.stream()
                        .filter(Combatant::isAlive)
                        .collect(java.util.stream.Collectors.toList());
                    useSpecialSkill(aliveEnemies);
                }
                break;
        }
    }

    public void performBasicAttack(Enemy target){
        int damage = Math.max(0, this.attack - target.getDefense());
        target.applyDamage(damage);
    }

    public void performDefend(){
        this.addStatusEffect(new DefendEffect());
    }

    public void useItem(int itemIndex, List<Enemy> enemies){
        if (itemIndex >= 0 && itemIndex < inventory.size()){
            Item item = inventory.remove(itemIndex);
            item.use(this, enemies);
        }
    }

    public void addItem(Item item){
        if (inventory.size() < 2){
            inventory.add(item);
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

    private void tickCooldown(){
        specialSkill.updateCooldown();
    }

    public List<Item> getInventory(){return inventory;}
    public SpecialSkill getSpecialSkill(){return specialSkill;}
}
