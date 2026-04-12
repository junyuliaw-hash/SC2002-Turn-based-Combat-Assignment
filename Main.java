import control.BattleEngine;
import control.DifficultyLevel;
import entity.combatants.*;
import entity.items.*;
import boundary.CombatMenu;

public class Main {
    public static void main(String[] args) {
        
        CombatMenu menu = new CombatMenu();
        boolean keepPlaying = true;
        boolean isReplay = false;

        int classChoice = 1;
        int[] itemChoices = new int[0];
        int difficultyChoice = 1;
        
        while (keepPlaying) {
            
            // check if is replay of fix setting (postGameAction == 1)
            if (!isReplay) {
                menu.displayLoadingScreen();
                classChoice = menu.promptPlayerSelection(); 
                itemChoices = menu.promptItemSelection();
                difficultyChoice = menu.promptDifficultySelection();
            }

            // reinitialize all states (player, items, difficulty)
            Player myPlayer;
            switch (classChoice) {
                case 1 -> myPlayer = new Warrior();
                case 2 -> myPlayer = new Wizard();
                case 3 -> myPlayer = new Archer();
                default -> myPlayer = new Warrior();
            }

            
            for (int choice : itemChoices) {
                switch (choice) {
                    case 1 -> myPlayer.addItem(new Potion());
                    case 2 -> myPlayer.addItem(new PowerStone());
                    case 3 -> myPlayer.addItem(new SmokeBomb());
                    case 4 -> myPlayer.addItem(new Poison());
                }
            }
            
            DifficultyLevel difficulty;
            switch (difficultyChoice) {
                case 1 -> difficulty = DifficultyLevel.EASY;
                case 2 -> difficulty = DifficultyLevel.MEDIUM;
                case 3 -> difficulty = DifficultyLevel.HARD;
                default -> difficulty = DifficultyLevel.EASY;
            }
            
            BattleEngine gameEngine = new BattleEngine(myPlayer, difficulty, menu);
            menu.setEngine(gameEngine);
            
            System.out.println("\n--- The Battle Begins! ---");
            gameEngine.startGame();
            
            // post game stuff
            int postGameAction = menu.promptPostGameOptions();
            
            if (postGameAction == 1) {
                isReplay = true; 
                System.out.println("\nRestarting with same settings...");
            } else if (postGameAction == 2) {
                isReplay = false;
            } else {
                keepPlaying = false;
                System.out.println("Thanks for playing!");
            }
        }
    }
}