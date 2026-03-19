package domain.combatants;

public abstract class Player extends Combatant {
    protected int specialSkillCooldown = 0;
    protected Item[] inventory = new Item[2];

    public Player(String name, int hp, int attack, int defense, int speed){
        super(name, hp, attack, defense, speed);
    }

    public void updateCooldown(){
        if (specialSkillCooldown > 0){
            specialSkillCooldown--;
        }
    }

    public int getSpecialSkillCooldown(){return specialSkillCooldown;}
    public void setSpecialSkillCooldown(int turns){specialSkillCooldown = turns;}
}
