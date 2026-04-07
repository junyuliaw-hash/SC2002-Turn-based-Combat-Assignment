package actions;

public class Action { 
    public void execute(); 
} 

public class DefendAction implements Action {
    @Override
    public void execute(Combatant source, Combatant target, BattleContext context) {
        source.addStatusEffect(new DefendEffect());
    }

    @Override
    public String getName() { return "Defend"; }
}

public class UseItemAction implements Action {
    private Item item;

    public UseItemAction(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Combatant source, Combatant target, BattleContext context) {
        item.use(source, context);
    }

    @Override
    public String getName() { return "Use Item: " + item.getName(); }
}

public class SpecialSkillAction implements Action {
    private boolean isFreeUse;

    public SpecialSkillAction(boolean isFreeUse) {
        this.isFreeUse = isFreeUse;
    }

    @Override
    public void execute(Combatant source, Combatant target, BattleContext context) {
        if (source.getName().equals("Warrior")) {
            System.out.println("Warrior uses Shield Bash on " + target.getName() + "!");
            int damage = Math.max(0, source.getAttack() - target.getDefense());
            target.applyDamage(damage);
            target.addStatusEffect(new StunEffect());
        } else if (source.getName().equals("Wizard")) {
            System.out.println("Wizard uses Arcane Blast!");
            for (Combatant enemy : context.getEnemies()) {
                if (enemy.isAlive()) {
                    int damage = Math.max(0, source.getAttack() - enemy.getDefense());
                    enemy.applyDamage(damage);
                    if (!enemy.isAlive()) {
                        System.out.println("Arcane Blast defeated " + enemy.getName() + "! Wizard gains +10 Attack.");
                        source.getAttack() += 10; 
                    }
                }
            }
        }
        // *** 3-turn cooldown on the source Combatant
    }

    @Override
    public String getName() { return "Special Skill"; }
}
