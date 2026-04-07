package boundary;

import entity.combatants.Player;
import entity.combatants.Enemy;
import entity.items.Item;
import control.BattleEngine;

import java.util.List;
import java.util.Scanner;

public class CombatMenu {

    private Scanner scanner;
    private BattleEngine engine;

    public CombatMenu(BattleEngine engine) {
        this.scanner = new Scanner(System.in);
        this.engine = engine;
    }

    /**
     * Displays the current game state including player and enemy info.
     */
    public void displayGameState(List<Player> players, List<Enemy> enemies) {
        System.out.println("========== BATTLE STATUS ==========");
        System.out.println("-- Players --");
        for (Player player : players) {
            System.out.printf("  %s | HP: %d/%d%n",
                    player.getName(), player.getHp(), player.getMaxHp());
        }
        System.out.println("-- Enemies --");
        for (Enemy enemy : enemies) {
            System.out.printf("  %s | HP: %d/%d%n",
                    enemy.getName(), enemy.getHp(), enemy.getMaxHp());
        }
        System.out.println("====================================");
    }

    /**
     * Prompts the user to select a player from the list.
     */
    public Player promptPlayerSelection() {
        List<Player> players = engine.getPlayer() != null
                ? List.of(engine.getPlayer())
                : List.of();

        System.out.println("Select a player:");
        for (int i = 0; i < players.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, players.get(i).getName());
        }

        int choice = readIntInput(1, players.size());
        return players.get(choice - 1);
    }

    /**
     * Prompts the player to select an item from their inventory.
     */
    public Player promptItemSelection() {
        Player player = engine.getPlayer();
        List<Item> inventory = player.getInventory();

        if (inventory.isEmpty()) {
            System.out.println("No items in inventory.");
            return player;
        }

        System.out.println("Select an item to use:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, inventory.get(i).getName());
        }

        int choice = readIntInput(1, inventory.size());
        player.useItem(choice - 1, engine.getEnemies());
        return player;
    }

    /**
     * Prompts the user to select a difficulty level (returns level number).
     */
    public int promptDifficultySelection() {
        System.out.println("Select Difficulty:");
        System.out.println("  [1] Easy");
        System.out.println("  [2] Normal");
        System.out.println("  [3] Hard");

        return readIntInput(1, 3);
    }

    /**
     * Prompts the player to choose an action during their turn.
     * Returns the chosen action index:
     *   1 = Basic Attack
     *   2 = Defend
     *   3 = Use Item
     *   4 = Use Special Skill
     */
    public int promptAction(Player player) {
        System.out.println("\n" + player.getName() + "'s turn. Choose an action:");
        System.out.println("  [1] Basic Attack");
        System.out.println("  [2] Defend");
        System.out.println("  [3] Use Item");
        System.out.printf("  [4] Use Special Skill (%s) [%s]%n",
                player.getSpecialSkill().getName(),
                player.getSpecialSkill().isReady() ? "Ready" : "Cooldown: " + player.getSpecialSkill().getCooldown());

        return readIntInput(1, 4);
    }

    /**
     * Prompts the player to select a target enemy.
     */
    public Enemy promptTarget(List<Enemy> enemies) {
        System.out.println("Select a target:");
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            System.out.printf("  [%d] %s | HP: %d/%d%n",
                    i + 1, e.getName(), e.getHp(), e.getMaxHp());
        }

        int choice = readIntInput(1, enemies.size());
        return enemies.get(choice - 1);
    }

    /**
     * Displays the victory screen with end-of-game stats.
     */
    public void displayVictoryScreen(int rounds, int hp, List<Item> items) {
        System.out.println("\n========== VICTORY! ==========");
        System.out.printf("Rounds survived : %d%n", rounds);
        System.out.printf("Remaining HP    : %d%n", hp);
        System.out.print("Items remaining : ");
        if (items.isEmpty()) {
            System.out.println("None");
        } else {
            items.forEach(item -> System.out.print(item.getName() + " "));
            System.out.println();
        }
        System.out.println("==============================");
    }

    /**
     * Displays the defeat screen with end-of-game stats.
     */
    public void displayDefeatScreen(int rounds, int enemiesLeft) {
        System.out.println("\n========== DEFEAT ==========");
        System.out.printf("Rounds survived  : %d%n", rounds);
        System.out.printf("Enemies remaining: %d%n", enemiesLeft);
        System.out.println("============================");
    }

    // -------------------------
    // Private helper
    // -------------------------

    /**
     * Reads and validates an integer input within [min, max].
     */
    private int readIntInput(int min, int max) {
        int input = -1;
        while (input < min || input > max) {
            System.out.printf("Enter a number (%d-%d): ", min, max);
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return input;
    }
}
