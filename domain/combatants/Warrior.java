package domain.combatants;
import java.util.List;

public class Warrior extends Player {
    public Warrior(){
        super("Warrior",260,40,20,30);
    }

    @Override
    public void takeTurn(){

    }

    @Override
    public void useSpecialSkill(List<Enemy> targets) {

    }
}
