package boundary;

import control.BattleEngine;
import entity.combatants.Enemy;
import entity.combatants.Player;
import entity.items.Item;
import java.util.List;
import java.util.Scanner;


/**
 * Boundary class responsible for all CLI interactions.
 * Handles display of game state and collection of player input.
 * Separated from battle logic in accordance with SRP and layered architecture.
 */
public class CombatMenu {

    private Scanner scanner;
    private BattleEngine engine;

    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    // 1. Remove BattleEngine from the constructor
    public CombatMenu() {
        this.scanner = new Scanner(System.in);
    }

    // 2. Add this setter method so we can link it later
    public void setEngine(BattleEngine engine) {
        this.engine = engine;
    }

    // -----------------------------------------------------------------------
    // Loading / Setup Screens
    // -----------------------------------------------------------------------

    /**
     * Displays the loading screen with player options, attributes, items, and
     * difficulty selection at game start.
     */
    public void displayLoadingScreen() {
        System.out.println("============================================================");
        System.out.println("         WELCOME TO TURN-BASED COMBAT ARENA");
        System.out.println("============================================================");
        System.out.println();

        System.out.println("=== AVAILABLE PLAYERS ===");
        System.out.println("[1] Warrior");
        System.out.println("    HP: 260 | ATK: 40 | DEF: 20 | SPD: 30");
        System.out.println("    Special Skill: Shield Bash");
        System.out.println("      -> Deal BasicAttack damage to selected enemy.");
        System.out.println("         Target is stunned for the current turn and next turn.");
        System.out.println();
        System.out.println("[2] Wizard");
        System.out.println("    HP: 200 | ATK: 50 | DEF: 10 | SPD: 20");
        System.out.println("    Special Skill: Arcane Blast");
        System.out.println("      -> Deal BasicAttack damage to ALL enemies.");
        System.out.println("         Each enemy defeated adds +10 ATK (until end of level).");
        System.out.println();
        System.out.println("[3] Archer");
        System.out.println("    HP: 220 | ATK: 45 | DEF: 15 | SPD: 35");
        System.out.println("    Special Skill: Poison Shot");
        System.out.println("      -> Deal BasicAttack damage to selected enemy and applies Poison.");
        System.out.println();

        System.out.println("=== AVAILABLE ENEMIES ===");
        System.out.println("Goblin  | HP: 55  | ATK: 35 | DEF: 15 | SPD: 25");
        System.out.println("Wolf    | HP: 40  | ATK: 45 | DEF: 5  | SPD: 35");
        System.out.println();

        System.out.println("=== AVAILABLE ITEMS ===");
        System.out.println("  [1] Potion      - Heal 100 HP (capped at max HP)");
        System.out.println("  [2] Power Stone - Trigger special skill for free (no cooldown change)");
        System.out.println("  [3] Smoke Bomb  - Enemy attacks deal 0 damage this turn and next");
        System.out.println("  [4] Poison      - Target takes 20 damage per turn for 2 turns");
        System.out.println();

        System.out.println("=== DIFFICULTY LEVELS ===");
        System.out.println("  [1] Easy   - Initial: 3 Goblins");
        System.out.println("  [2] Medium - Initial: 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("  [3] Hard   - Initial: 2 Goblins         | Backup: 1 Goblin + 2 Wolves");
        System.out.println();
    }

    // -----------------------------------------------------------------------
    // During Gameplay: State Display
    // -----------------------------------------------------------------------

    /**
     * Displays the current game state — HP, status effects, inventory, round info.
     *
     * @param players list containing the active player
     * @param enemies list of all enemies (alive and dead)
     */
    public void displayGameState(List<Player> players, List<Enemy> enemies) {
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.printf("  ROUND %d%n", engine.getCurrentRound());
        System.out.println("------------------------------------------------------------");

        // Player status
        for (Player player : players) {
            System.out.printf("  [Player] %s | HP: %d/%d | ATK: %d | DEF: %d | SPD: %d%n",
                    player.getName(),
                    player.getHp(),
                    player.getMaxHp(),
                    player.getAttack(),
                    player.getDefense(),
                    player.getSpeed());

            // Inventory
            List<Item> inventory = player.getInventory();
            if (!inventory.isEmpty()) {
                System.out.print("    Items: ");
                for (int i = 0; i < inventory.size(); i++) {
                    System.out.printf("[%d] %s  ", i + 1, inventory.get(i).getName());
                }
                System.out.println();
            } else {
                System.out.println("    Items: None");
            }

            // Special skill cooldown
            if (player.getSpecialSkill() != null) {
                int cd = player.getSpecialSkill().getCooldown();
                String cdStr = (cd == 0) ? "READY" : cd + " round(s)";
                System.out.printf("    Special Skill [%s] - Cooldown: %s%n",
                        player.getSpecialSkill().getName(), cdStr);
            }
        }

        System.out.println();

        // Enemy status
        System.out.println("  [Enemies]");
        if (enemies.isEmpty()) {
            System.out.println("    No enemies present.");
        } else {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                String aliveStatus = e.isAlive() ? "" : " [ELIMINATED]";
                System.out.printf("    [%d] %s | HP: %d/%d | ATK: %d | DEF: %d | SPD: %d%s%n",
                        i + 1,
                        e.getName(),
                        e.getHp(),
                        e.getMaxHp(),
                        e.getAttack(),
                        e.getDefense(),
                        e.getSpeed(),
                        aliveStatus);
            }
        }

        System.out.println("------------------------------------------------------------");
    }

    // -----------------------------------------------------------------------
    // Player Selection Prompts
    // -----------------------------------------------------------------------

    /**
     * Prompts the user to select a player class (Warrior or Wizard).
     *
     * @return 1 for Warrior, 2 for Wizard
     */
    public int promptPlayerSelection() {
        System.out.println("Select your player:");
        System.out.println("  [1] Warrior");
        System.out.println("  [2] Wizard");
        System.out.println("  [3] Archer");
        return readIntInRange("Enter choice: ", 1, 3);
    }

    /**
     * Prompts the user to select two items (duplicates allowed).
     * Returns the item choices as an array of indices [firstItemIndex, secondItemIndex].
     *
     * @return int array of length 2 with item selections (1-based indices)
     */
    public int[] promptItemSelection() {
        System.out.println();
        System.out.println("Select Item 1 (you may pick the same item twice):");
        System.out.println("  [1] Potion");
        System.out.println("  [2] Power Stone");
        System.out.println("  [3] Smoke Bomb");
        System.out.println("  [4] Poison");
        int item1 = readIntInRange("Enter choice: ", 1, 4);

        System.out.println("Select Item 2:");
        System.out.println("  [1] Potion");
        System.out.println("  [2] Power Stone");
        System.out.println("  [3] Smoke Bomb");
        System.out.println("  [4] Poison");
        int item2 = readIntInRange("Enter choice: ", 1, 4);
        return new int[]{item1, item2};
    }

    /**
     * Prompts the user to select a difficulty level.
     *
     * @return integer level number: 1 (Easy), 2 (Medium), or 3 (Hard)
     */
    public int promptDifficultySelection() {
        System.out.println();
        System.out.println("Select difficulty level:");
        System.out.println("  [1] Easy   - 3 Goblins");
        System.out.println("  [2] Medium - 1 Goblin + 1 Wolf (Backup: 2 Wolves)");
        System.out.println("  [3] Hard   - 2 Goblins (Backup: 1 Goblin + 2 Wolves)");
        return readIntInRange("Enter choice: ", 1, 3);
    }

    // -----------------------------------------------------------------------
    // In-Turn Action / Target Prompts
    // -----------------------------------------------------------------------

    /**
     * Prompts the active player to choose an action for their turn.
     * Shows only available actions (e.g. Item action hidden if inventory is empty,
     * Special Skill greyed out if on cooldown).
     *
     * @param player the player whose turn it is
     * @return integer action code:
     *         1 = BasicAttack, 2 = Defend, 3 = Item, 4 = SpecialSkill
     */
    public int promptAction(Player player) {
        System.out.println();
        System.out.printf("  %s's Turn — Choose an action:%n", player.getName());
        System.out.println("  [1] Basic Attack");
        System.out.println("  [2] Defend  (+10 DEF this round and next)");

        // Show Item option only if inventory is non-empty
        if (!player.getInventory().isEmpty()) {
            System.out.println("  [3] Use Item");
        } else {
            System.out.println("  [3] Use Item  (no items remaining)");
        }

        // Show Special Skill with cooldown info
        if (player.getSpecialSkill() != null) {
            if (player.getSpecialSkill().isReady()) {
                System.out.printf("  [4] Special Skill: %s  (READY)%n",
                        player.getSpecialSkill().getName());
            } else {
                System.out.printf("  [4] Special Skill: %s  (Cooldown: %d round(s))%n",
                        player.getSpecialSkill().getName(),
                        player.getSpecialSkill().getCooldown());
            }
        }

        // Validate input considering availability
        while (true) {
            int choice = readIntInRange("Enter action: ", 1, 4);

            if (choice == 3 && player.getInventory().isEmpty()) {
                System.out.println("  You have no items. Please choose another action.");
                continue;
            }
            if (choice == 4 && player.getSpecialSkill() != null && !player.getSpecialSkill().isReady()) {
                System.out.println("  Special Skill is on cooldown. Please choose another action.");
                continue;
            }
            return choice;
        }
    }

    /**
     * Prompts the player to select an item from their inventory to use.
     *
     * @param player the player using the item
     * @return 0-based index of the selected item in the player's inventory
     */
    public int promptItemChoice(Player player) {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("  Inventory is empty!");
            return -1;
        }
        System.out.println("  Select an item to use:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.printf("    [%d] %s%n", i + 1, inventory.get(i).getName());
        }
        return readIntInRange("Enter item number: ", 1, inventory.size()) - 1;
    }

    /**
     * Prompts the player to select a target enemy from the list of alive enemies.
     *
     * @param enemies list of all enemies (alive and dead)
     * @return the selected alive Enemy
     */
    public Enemy promptTarget(List<Enemy> enemies) {
        // Collect only alive enemies
        List<Enemy> alive = new java.util.ArrayList<>();
        for (Enemy e : enemies) {
            if (e.isAlive()) alive.add(e);
        }

        System.out.println("  Select a target:");
        for (int i = 0; i < alive.size(); i++) {
            System.out.printf("    [%d] %s (HP: %d/%d)%n",
                    i + 1,
                    alive.get(i).getName(),
                    alive.get(i).getHp(),
                    alive.get(i).getMaxHp());
        }

        int choice = readIntInRange("Enter target number: ", 1, alive.size());
        return alive.get(choice - 1);
    }

    // -----------------------------------------------------------------------
    // Game Completion Screens
    // -----------------------------------------------------------------------

    /**
     * Displays the victory screen with statistics.
     *
     * @param rounds    total number of rounds completed
     * @param hp        player's remaining HP
     * @param items     player's remaining items in inventory
     */
    public void displayVictoryScreen(int rounds, int hp, List<Item> items) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("       *** VICTORY! ***");
        System.out.println("  Congratulations, you have defeated all your enemies!");
        System.out.println("------------------------------------------------------------");
        System.out.printf("  Remaining HP   : %d%n", hp);
        System.out.printf("  Total Rounds   : %d%n", rounds);
        System.out.print("  Remaining Items: ");
        if (items == null || items.isEmpty()) {
            System.out.println("None");
        } else {
            for (Item item : items) {
                System.out.print(item.getName() + "  ");
            }
            System.out.println();
        }
        System.out.println("============================================================");
        System.out.println();
    }

    /**
     * Displays the defeat screen with statistics.
     *
     * @param rounds       total rounds the player survived
     * @param enemiesLeft  number of enemies still alive at defeat
     */
    public void displayDefeatScreen(int rounds, int enemiesLeft) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("       *** DEFEATED ***");
        System.out.println("  Don't give up, try again!");
        System.out.println("------------------------------------------------------------");
        System.out.printf("  Enemies Remaining  : %d%n", enemiesLeft);
        System.out.printf("  Total Rounds Survived: %d%n", rounds);
        System.out.println("============================================================");
        System.out.println();
    }

    // -----------------------------------------------------------------------
    // Post-Game Options
    // -----------------------------------------------------------------------

    /**
     * Prompts the player to replay, start a new game, or exit after game ends.
     *
     * @return 1 = replay same settings, 2 = new game (return to home), 3 = exit
     */
    public int promptPostGameOptions() {
        System.out.println("  What would you like to do?");
        System.out.println("  [1] Replay with the same settings");
        System.out.println("  [2] Start a new game");
        System.out.println("  [3] Exit");
        return readIntInRange("Enter choice: ", 1, 3);
    }

    // -----------------------------------------------------------------------
    // Utility / Informational Displays
    // -----------------------------------------------------------------------

    /**
     * Displays a message indicating a backup enemy spawn has occurred.
     *
     * @param backupEnemies list of newly spawned enemies
     */
    public void displayBackupSpawn(List<Enemy> backupEnemies) {
        System.out.println();
        System.out.println("  *** BACKUP SPAWN TRIGGERED! ***");
        System.out.println("  New enemies have entered the battle:");
        for (Enemy e : backupEnemies) {
            System.out.printf("    -> %s (HP: %d | ATK: %d | DEF: %d | SPD: %d)%n",
                    e.getName(), e.getMaxHp(), e.getAttack(), e.getDefense(), e.getSpeed());
        }
        System.out.println();
    }

    /**
     * Displays a general informational message to the player.
     *
     * @param message the message to display
     */
    public void displayMessage(String message) {
        System.out.println("  " + message);
    }

    // -----------------------------------------------------------------------
    // Internal Helper
    // -----------------------------------------------------------------------

    /**
     * Reads an integer from stdin within [min, max] inclusive.
     * Re-prompts on invalid input.
     */
    private int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = scanner.nextLine().trim();
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }
}
