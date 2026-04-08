import control.BattleEngine;
import control.DifficultyLevel;
import entity.combatants.Player;
import entity.combatants.Warrior; // Or Wizard, if you prefer!
import boundary.CombatMenu;

public class Main {
    public static void main(String[] args) {
        
        // 1. Create the Player
        Player myPlayer = new Warrior();
        
        // 2. Select the Difficulty
        DifficultyLevel difficulty = DifficultyLevel.EASY;
        
        // 3. Create the UI (Menu)
        CombatMenu menu = new CombatMenu();
        
        // 4. Create the Engine using the 3 required ingredients
        BattleEngine gameEngine = new BattleEngine(myPlayer, difficulty, menu);
        
        // 5. Link the Engine back to the Menu
        menu.setEngine(gameEngine);
        
        // 6. Start the game!
        gameEngine.startGame();
    }
}
