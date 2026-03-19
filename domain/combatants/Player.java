package domain.combatants;
import java.util.List;
import java.util.ArrayList;

public abstract class Player extends Combatant {
    protected int specialSkillCooldown = 0;

    public Player(String name, int hp, int attack, int defense, int speed){
        super(name, hp, attack, defense, speed);
    }

    public abstract void useSpecialSkill(List<Enemy> targets);

    public void updateCooldown(){
        if (specialSkillCooldown > 0){
            specialSkillCooldown--;
        }
    }

    public int getSpecialSkillCooldown(){return specialSkillCooldown;}
    public int setSpecialSkillCooldown(int turns){specialSkillCooldown = turns;}
}
