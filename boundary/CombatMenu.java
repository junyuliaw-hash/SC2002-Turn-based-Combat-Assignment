package boundary;

import control.BattleEngine;
import entity.combatants.Enemy;
import entity.combatants.Player;
import entity.items.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class CombatMenu {
    private static final Scanner SHARED_SCANNER = new Scanner(System.in);
    private static final int UI_WIDTH = 72;
    private static final int BOX_INNER_WIDTH = UI_WIDTH - 4;
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String MAGENTA = "\u001B[35m";

    private final Scanner scanner;
    private BattleEngine engine;
    private List<String> pendingRoundSummary = new ArrayList<>();

    public CombatMenu() {
        this.scanner = SHARED_SCANNER;
    }

    public void setEngine(BattleEngine engine) {
        this.engine = engine;
    }

    public void displayLoadingScreen() {
        System.out.println(color(CYAN, repeat("=", UI_WIDTH)));
        System.out.println(color(BOLD + CYAN, "WELCOME TO TURN-BASED COMBAT ARENA"));
        System.out.println(color(CYAN, repeat("=", UI_WIDTH)));
        System.out.println();

        System.out.println(color(BOLD + BLUE, "AVAILABLE PLAYERS"));
        System.out.println(color(GREEN, "[1] Warrior"));
        System.out.println("    HP: 260 | ATK: 40 | DEF: 20 | SPD: 30");
        System.out.println(color(MAGENTA, "    Special Skill: Shield Bash"));
        System.out.println("    Deal BasicAttack damage to selected enemy.");
        System.out.println("    Target is stunned for the current turn and next turn.");
        System.out.println();
        System.out.println(color(GREEN, "[2] Wizard"));
        System.out.println("    HP: 200 | ATK: 50 | DEF: 10 | SPD: 20");
        System.out.println(color(MAGENTA, "    Special Skill: Arcane Blast"));
        System.out.println("    Deal BasicAttack damage to ALL enemies.");
        System.out.println("    Each enemy defeated adds +10 ATK (until end of level).");
        System.out.println();
        System.out.println(color(GREEN, "[3] Archer"));
        System.out.println("    HP: 220 | ATK: 45 | DEF: 15 | SPD: 35");
        System.out.println(color(MAGENTA, "    Special Skill: Poison Shot"));
        System.out.println("    Deal BasicAttack damage to selected enemy and applies Poison.");
        System.out.println();

        System.out.println(color(BOLD + RED, "AVAILABLE ENEMIES"));
        System.out.println(color(RED, "Goblin  | HP: 55 | ATK: 35 | DEF: 15 | SPD: 25"));
        System.out.println(color(RED, "Wolf    | HP: 40 | ATK: 45 | DEF: 5  | SPD: 35"));
        System.out.println();

        System.out.println(color(BOLD + YELLOW, "AVAILABLE ITEMS"));
        System.out.println(color(YELLOW, "[1] Potion      - Heal 100 HP (capped at max HP)"));
        System.out.println(color(YELLOW, "[2] Power Stone - Trigger special skill for free"));
        System.out.println(color(YELLOW, "[3] Smoke Bomb  - Enemy attacks deal 0 damage for 2 turns"));
        System.out.println(color(YELLOW, "[4] Poison      - Affects all enemies: 20 damage, then 10 damage"));
        System.out.println();

        System.out.println(color(BOLD + CYAN, "DIFFICULTY LEVELS"));
        System.out.println("[1] Easy   - Initial: 3 Goblins");
        System.out.println("[2] Medium - Initial: 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("[3] Hard   - Initial: 2 Goblins | Backup: 1 Goblin + 2 Wolves");
        System.out.println();
    }

    public void displayRoundStart(int roundNumber) {
        showStoryBeat("ROUND " + roundNumber + " BEGINS");
    }

    public void displayGameState(List<Player> players, List<Enemy> enemies) {
        List<String> lines = new ArrayList<>();
        if (!pendingRoundSummary.isEmpty()) {
            lines.addAll(pendingRoundSummary);
            lines.add("");
            pendingRoundSummary.clear();
        }
        lines.add("ROUND " + engine.getCurrentRound() + " BATTLE STATE");
        lines.add("");
        lines.add("PLAYER");

        for (Player player : players) {
            lines.add(String.format("%s | HP: %d/%d | ATK: %d | DEF: %d | SPD: %d",
                    player.getName(),
                    player.getHp(),
                    player.getMaxHp(),
                    player.getAttack(),
                    player.getDefense(),
                    player.getSpeed()));
            lines.add("Effects: " + formatEffects(player.getActiveEffectNames()));

            List<Item> inventory = player.getInventory();
            if (inventory.isEmpty()) {
                lines.add("Items: None");
            } else {
                StringBuilder itemsLine = new StringBuilder("Items: ");
                for (int i = 0; i < inventory.size(); i++) {
                    if (i > 0) {
                        itemsLine.append("  ");
                    }
                    itemsLine.append("[").append(i + 1).append("] ").append(inventory.get(i).getName());
                }
                lines.add(itemsLine.toString());
            }

            if (player.getSpecialSkill() != null) {
                int cooldown = player.getSpecialSkill().getCooldown();
                String cooldownText = cooldown == 0 ? "READY" : cooldown + " round(s)";
                lines.add("Special Skill [" + player.getSpecialSkill().getName() + "] - Cooldown: " + cooldownText);
            }
        }

        lines.add("");
        lines.add("ENEMIES");
        if (enemies.isEmpty()) {
            lines.add("No enemies present.");
        } else {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                String status = enemy.isAlive() ? "" : " [ELIMINATED]";
                lines.add(String.format("[%d] %s | HP: %d/%d | ATK: %d | DEF: %d | SPD: %d%s",
                        i + 1,
                        enemy.getName(),
                        enemy.getHp(),
                        enemy.getMaxHp(),
                        enemy.getAttack(),
                        enemy.getDefense(),
                        enemy.getSpeed(),
                        status));
                lines.add("Effects: " + formatEffects(enemy.getActiveEffectNames()));
            }
        }

        printBox(lines);
    }

    public void displayRoundSummary(Player player, List<Enemy> enemies) {
        long aliveEnemies = enemies.stream().filter(Enemy::isAlive).count();
        pendingRoundSummary = new ArrayList<>();
        pendingRoundSummary.add("ROUND " + engine.getCurrentRound() + " SUMMARY");
        pendingRoundSummary.add("Player HP: " + player.getHp() + "/" + player.getMaxHp());
        pendingRoundSummary.add("Enemies Remaining: " + aliveEnemies);
        pendingRoundSummary.add("Player Effects: " + formatEffects(player.getActiveEffectNames()));
    }

    public int promptPlayerSelection() {
        System.out.println(color(BOLD + BLUE, "Choose your player"));
        System.out.println(color(GREEN, "[1] Warrior"));
        System.out.println(color(GREEN, "[2] Wizard"));
        System.out.println(color(GREEN, "[3] Archer"));
        return readIntInRange("Enter choice: ", 1, 3);
    }

    public int[] promptItemSelection() {
        System.out.println();
        System.out.println(color(BOLD + YELLOW, "Choose Item 1"));
        System.out.println(color(YELLOW, "[1] Potion      - Heal 100 HP"));
        System.out.println(color(YELLOW, "[2] Power Stone - Trigger your special skill"));
        System.out.println(color(YELLOW, "[3] Smoke Bomb  - Ignore enemy damage for 2 turns"));
        System.out.println(color(YELLOW, "[4] Poison      - Poison all enemies: 20 damage, then 10 damage"));
        int item1 = readIntInRange("Enter choice: ", 1, 4);

        System.out.println(color(BOLD + YELLOW, "Choose Item 2"));
        System.out.println(color(YELLOW, "[1] Potion      - Heal 100 HP"));
        System.out.println(color(YELLOW, "[2] Power Stone - Trigger your special skill"));
        System.out.println(color(YELLOW, "[3] Smoke Bomb  - Ignore enemy damage for 2 turns"));
        System.out.println(color(YELLOW, "[4] Poison      - Poison all enemies: 20 damage, then 10 damage"));
        int item2 = readIntInRange("Enter choice: ", 1, 4);
        return new int[]{item1, item2};
    }

    public int promptDifficultySelection() {
        System.out.println();
        System.out.println(color(BOLD + CYAN, "Choose difficulty"));
        System.out.println("[1] Easy   - 3 Goblins");
        System.out.println("[2] Medium - 1 Goblin + 1 Wolf (Backup: 2 Wolves)");
        System.out.println("[3] Hard   - 2 Goblins (Backup: 1 Goblin + 2 Wolves)");
        return readIntInRange("Enter choice: ", 1, 3);
    }

    public int promptAction(Player player) {
        System.out.println(color(BOLD + BLUE, "Choose your action"));
        System.out.println(color(GREEN, "[1] Basic Attack"));
        System.out.println(color(GREEN, "[2] Defend (+10 DEF this round and next)"));

        if (player.getInventory().isEmpty()) {
            System.out.println(color(YELLOW, "[3] Use Item (no items remaining)"));
        } else {
            System.out.println(color(YELLOW, "[3] Use Item"));
        }

        if (player.getSpecialSkill() != null) {
            if (player.getSpecialSkill().isReady()) {
                System.out.printf("%s%n", color(MAGENTA, "[4] Special Skill: " + player.getSpecialSkill().getName() + " (READY)"));
            } else {
                System.out.printf("%s%n", color(MAGENTA,
                        "[4] Special Skill: " + player.getSpecialSkill().getName()
                                + " (Cooldown: " + player.getSpecialSkill().getCooldown() + " round(s))"));
            }
        }

        while (true) {
            int choice = readIntInRange("Enter action: ", 1, 4);

            if (choice == 3 && player.getInventory().isEmpty()) {
                System.out.println(color(RED, "You have no items. Choose another action."));
                continue;
            }
            if (choice == 4 && player.getSpecialSkill() != null && !player.getSpecialSkill().isReady()) {
                System.out.println(color(RED, "Special Skill is on cooldown. Choose another action."));
                continue;
            }
            return choice;
        }
    }

    public int promptItemChoice(Player player) {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println(color(RED, "Inventory is empty."));
            return -1;
        }
        System.out.println(color(BOLD + YELLOW, "Choose an item"));
        for (int i = 0; i < inventory.size(); i++) {
            System.out.printf("%s%n", color(YELLOW, "[" + (i + 1) + "] " + inventory.get(i).getName()));
        }
        return readIntInRange("Enter item number: ", 1, inventory.size()) - 1;
    }

    public Enemy promptTarget(List<Enemy> enemies) {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                alive.add(enemy);
            }
        }

        List<String> lines = new ArrayList<>();
        lines.add("CHOOSE A TARGET");
        for (int i = 0; i < alive.size(); i++) {
            Enemy enemy = alive.get(i);
            lines.add(String.format("[%d] %s (HP: %d/%d) | Effects: %s",
                    i + 1,
                    enemy.getName(),
                    enemy.getHp(),
                    enemy.getMaxHp(),
                    formatEffects(enemy.getActiveEffectNames())));
        }
        printBox(lines);

        int choice = readIntInRange("Enter target number: ", 1, alive.size());
        return alive.get(choice - 1);
    }

    public void displayVictoryScreen(int rounds, int hp, List<Item> items) {
        List<String> lines = new ArrayList<>();
        lines.add("VICTORY");
        lines.add("You defeated all your enemies.");
        lines.add("");
        lines.add("Remaining HP: " + hp);
        lines.add("Total Rounds: " + rounds);
        if (items == null || items.isEmpty()) {
            lines.add("Remaining Items: None");
        } else {
            StringBuilder itemsLine = new StringBuilder("Remaining Items: ");
            for (Item item : items) {
                if (itemsLine.length() > "Remaining Items: ".length()) {
                    itemsLine.append("  ");
                }
                itemsLine.append(item.getName());
            }
            lines.add(itemsLine.toString());
        }
        showStoryBeat(lines.toArray(new String[0]));
    }

    public void displayDefeatScreen(int rounds, int enemiesLeft) {
        showStoryBeat(
                "DEFEATED",
                "Don't give up, try again.",
                "",
                "Enemies Remaining: " + enemiesLeft,
                "Total Rounds Survived: " + rounds
        );
    }

    public int promptPostGameOptions() {
        System.out.println(color(BOLD + CYAN, "What would you like to do?"));
        System.out.println(color(CYAN, "[1] Replay with the same settings"));
        System.out.println(color(CYAN, "[2] Start a new game"));
        System.out.println(color(CYAN, "[3] Exit"));
        return readIntInRange("Enter choice: ", 1, 3);
    }

    public void displayBackupSpawn(List<Enemy> backupEnemies) {
        List<String> lines = new ArrayList<>();
        lines.add("BACKUP ENEMIES ARRIVE");
        lines.add("New enemies have entered the battle:");
        for (Enemy enemy : backupEnemies) {
            lines.add(String.format("%s (HP: %d | ATK: %d | DEF: %d | SPD: %d)",
                    enemy.getName(),
                    enemy.getMaxHp(),
                    enemy.getAttack(),
                    enemy.getDefense(),
                    enemy.getSpeed()));
        }
        showStoryBeat(lines.toArray(new String[0]));
    }

    public void displayMessage(String message) {
        showStoryBeat(message);
    }

    public void showStoryBeat(String... lines) {
        printBox(List.of(lines));
        waitForContinue();
    }

    private String formatEffects(List<String> effectNames) {
        if (effectNames.isEmpty()) {
            return "None";
        }

        StringJoiner joiner = new StringJoiner(", ");
        for (String effectName : effectNames) {
            joiner.add(effectName);
        }
        return joiner.toString();
    }

    private int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(color(BOLD + CYAN, prompt));
            try {
                String line = scanner.nextLine().trim();
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("%s%n", color(RED, "Please enter a number between " + min + " and " + max + "."));
            } catch (NumberFormatException e) {
                System.out.println(color(RED, "Invalid input. Please enter a number."));
            }
        }
    }

    private void printBox(List<String> lines) {
        System.out.println(color(CYAN, "+" + repeat("-", UI_WIDTH - 2) + "+"));
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (String wrapped : wrapLine(line)) {
                String padded = String.format("%-" + BOX_INNER_WIDTH + "s", wrapped);
                System.out.printf("%s %s %s%n",
                        color(CYAN, "|"),
                        color(colorForBoxLine(wrapped, i == 0), padded),
                        color(CYAN, "|"));
            }
        }
        System.out.println(color(CYAN, "+" + repeat("-", UI_WIDTH - 2) + "+"));
    }

    private List<String> wrapLine(String text) {
        List<String> wrapped = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            wrapped.add("");
            return wrapped;
        }

        String[] words = text.split(" ");
        StringBuilder current = new StringBuilder();
        for (String word : words) {
            if (current.length() == 0) {
                current.append(word);
                continue;
            }
            if (current.length() + 1 + word.length() <= BOX_INNER_WIDTH) {
                current.append(' ').append(word);
            } else {
                wrapped.add(current.toString());
                current = new StringBuilder(word);
            }
        }
        wrapped.add(current.toString());
        return wrapped;
    }

    private String repeat(String value, int count) {
        return value.repeat(count);
    }

    private void waitForContinue() {
        System.out.println(color(BOLD + CYAN, "Press Enter to continue..."));
        scanner.nextLine();
    }

    private String colorForBoxLine(String line, boolean isFirstLine) {
        if (isFirstLine) {
            return BOLD + YELLOW;
        }
        if (line.startsWith("PLAYER")) {
            return BOLD + GREEN;
        }
        if (line.startsWith("ENEMIES")) {
            return BOLD + RED;
        }
        if (line.startsWith("Effects: None") || line.startsWith("Player Effects: None")) {
            return RESET;
        }
        if (line.startsWith("Effects:")) {
            return MAGENTA;
        }
        if (line.contains("[ELIMINATED]")) {
            return RED;
        }
        if (line.startsWith("Special Skill")) {
            return MAGENTA;
        }
        if (line.startsWith("Items:")) {
            return YELLOW;
        }
        if (line.startsWith("Player Effects:")) {
            return MAGENTA;
        }
        if (line.startsWith("Player HP:") || line.startsWith("Remaining HP:")) {
            return GREEN;
        }
        if (line.startsWith("Enemies Remaining:")) {
            return RED;
        }
        return RESET;
    }

    private String color(String ansi, String text) {
        return ansi + text + RESET;
    }
}
