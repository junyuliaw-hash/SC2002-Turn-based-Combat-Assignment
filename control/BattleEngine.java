package control;

import entity.combatants.Combatant;
import entity.combatants.Player;
import entity.combatants.Enemy;
import control.DifficultyLevel;
import control.SpeedBasedStrategy;
import control.TurnOrderStrategy;
import boundary.CombatMenu;

import java.util.ArrayList;
import java.util.List;


public class BattleEngine {


    private final Player            player;
    private       List<Enemy>       enemies;
    private       TurnOrderStrategy turnStrategy;
    private       int               roundNumber;
    private final DifficultyLevel   difficultyLevel;
    private final CombatMenu        ui;

    private boolean backupSpawned = false;


    public BattleEngine(Player player,
                        DifficultyLevel difficultyLevel,
                        CombatMenu ui) {
        this.player          = player;
        this.difficultyLevel = difficultyLevel;
        this.ui              = ui;
        this.turnStrategy    = new SpeedBasedStrategy();
        this.roundNumber     = 0;
        this.enemies         = new ArrayList<>(difficultyLevel.getInitialSpawn());
    }

    public void startGame() {
        ui.displayGameState(List.of(player), enemies);

        while (!checkGameEndCondition()) {
            executeRound();

            if (allEnemiesDefeated() && !difficultyLevel.getBackupSpawn().isEmpty() && !backupSpawned) {
                spawnBackup();
            }
        }

        displayEndScreen();
    }

    public void executeRound() {
        roundNumber++;
        ui.displayMessage("=== Round " + roundNumber + " ===");

        applyStatusEffects();

        if (checkGameEndCondition()) return;

        List<Combatant> turnOrder = turnStrategy.determineTurnOrder(buildCombatantList());

        for (Combatant combatant : turnOrder) {

            if (!combatant.isAlive()) continue;          // already eliminated
            if (checkGameEndCondition())  break;          // battle over mid-round

            if (combatant.hasEffect("Stun")) {
                ui.displayMessage(combatant.getName() + " is stunned and skips their turn!");
                combatant.updateEffects();
                continue;
            }

            if (combatant instanceof Player p) {
                handlePlayerTurn(p);
            } else if (combatant instanceof Enemy e) {
                handleEnemyTurn(e);
            }

            if (checkGameEndCondition()) break;
        }

        tickCooldowns(turnOrder);

        ui.displayGameState(List.of(player), enemies);
    }


    public Player       getPlayer()       { return player; }
    public List<Enemy>  getEnemies()      { return new ArrayList<>(enemies); }
    public int          getCurrentRound() { return roundNumber; }
    public CombatMenu   getUI()           { return ui; }


    public boolean checkGameEndCondition() {
        return !player.isAlive() || allEnemiesDefeated();
    }

    private void spawnBackup() {
        backupSpawned = true;
        List<Enemy> backupWave = difficultyLevel.getBackupSpawn();
        enemies = new ArrayList<>(backupWave);
        ui.displayBackupSpawn(backupWave);
    }

    private void applyStatusEffects() {
        for (Combatant c : buildCombatantList()) {
            if (c.isAlive()) {
                c.updateEffects();
            }
        }
    }

    private void handlePlayerTurn(Player p) {
        ui.displayMessage(p.getName() + "'s turn — choose an action:");
        p.takeTurn(this);
    }

    private void handleEnemyTurn(Enemy e) {
        ui.displayMessage(e.getName() + " is taking their turn...");
        e.takeTurn(this);
    }

    private void tickCooldowns(List<Combatant> participated) {
        for (Combatant c : participated) {
            c.tickCooldown();
        }
    }

    private boolean allEnemiesDefeated() {
        return enemies.stream().noneMatch(Combatant::isAlive);
    }

    private List<Combatant> buildCombatantList() {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(enemies);
        return all;
    }

    private List<Enemy> getAliveEnemies() {
        return enemies.stream()
                      .filter(Combatant::isAlive)
                      .collect(java.util.stream.Collectors.toList());
    }

    private void displayEndScreen() {
        if (player.isAlive()) {
            ui.displayVictoryScreen(roundNumber, player.getHp(), player.getInventory());
        } else {
            long remaining = enemies.stream().filter(Combatant::isAlive).count();
            ui.displayDefeatScreen(roundNumber, (int) remaining);
        }
    }
}