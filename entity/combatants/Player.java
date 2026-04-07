package entity.combatants;

import java.util.List;

import boundary.CombatMenu;
import control.BattleEngine;

import java.util.ArrayList;

import entity.items.Item;
import entity.actions.BasicAttack;
import entity.actions.Defend;
import entity.actions.SpecialSkill;
import entity.actions.UseItem;
import entity.statusEffect.DefendEffect;

public abstract class Player extends Combatant {
    protected List<Item> inventory = new ArrayList<>();
    protected SpecialSkill specialSkill;

    public void takeTurn(BattleEngine engine){
        CombatMenu ui = engine.getUI();
        List<Enemy> enemies = engine.getEnemies();
        
        int actionChoice = ui.promptAction(this);
        
        switch (actionChoice) {
            case 1:
                Enemy target = ui.promptTarget(enemies);
                if (target != null){
                    new BasicAttack().execute(this, target);
                }
                break;
                
            case 2: // Defend
                new Defend().execute(this, null);
                break;
                
            case 3: // Use Item
                if (!inventory.isEmpty()){
                    int itemIndex = ui.promptItemChoice(this);

                }
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

    public List<Item> getInventory(){return inventory;}
    public SpecialSkill getSpecialSkill(){return specialSkill;}
}
