package domain.combatants;

public abstract class Enemy extends Combatant {
    public Enemy(String name, int hp, int attack, int defense, int speed){
        super(name, hp, attack, defense, speed);
    }

    public void takeTurn(){}

    public void performBasicAttack(Combatant target){
        target.applyDamage(this.attack);
    }

    public boolean isStunned(){return true;}
}
