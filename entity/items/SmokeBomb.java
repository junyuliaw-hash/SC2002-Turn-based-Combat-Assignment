package items;

import status.SmokeBombEffect;

public class SmokeBomb implements Item {
    private String name = "Smoke Bomb";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant player, Combatant target) {
        SmokeBombEffect stun = new SmokeBombEffect();
        stun.apply(target);
        System.out.println(player.getName() + " used Smoke Bomb! " + target.getName() + " is stunned for 2 turns.");
    }
}
