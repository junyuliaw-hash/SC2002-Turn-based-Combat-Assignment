package items;

public class PowerStone implements Item {
    private String name = "Power Stone";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant player, Combatant target) {
    player.useSpecialAttack(target);
    System.out.println(player.getName() + " used Power Stone! Unleashed a powerful attack on " + target.getName() + "!");         
    }
    
}
