package entity.items;
import entity.combatants.*;
import java.util.List;

public class PowerStone implements Item {
    private String name = "Power Stone";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant player, List<Enemy> targets) {
        ((Player) player).refreshCooldown();
        ((Player) player).useSpecialSkill(targets);
        System.out.println(player.getName() + " used Power Stone! Unleashed a powerful attack!");         
    }

    @Override
    public void use(Combatant player, Combatant target) {
        ((Player) player).refreshCooldown();
        ((Player) player).useSpecialSkill(List.of((Enemy) target));
        System.out.println(player.getName() + " used Power Stone on " + target.getName() + "! Unleashed a powerful attack!");         
    }
    
}
