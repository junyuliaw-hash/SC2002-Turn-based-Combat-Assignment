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

    public Player(String n, int mHp, int hp, int a, int d, int s, SpecialSkill ss){
        this.name = n;
        this.maxHp = mHp;
        this.hp = hp;
        this.attack = a;
        this.defense = d;
        this.speed = s;
        this.specialSkill = ss;
    }

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
        target.applyDamage(attack);
    }

    public void performDefend(){
        DefendEffect effect = new DefendEffect();
        effect.apply(this);
        this.addStatusEffect(effect);
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
        } else {
            System.out.println("Inventory full!");
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
        if (specialSkill != null) {
            specialSkill.updateCooldown();
        }
    }

    public List<Item> getInventory(){return inventory;}
    public SpecialSkill getSpecialSkill(){return specialSkill;}
}
