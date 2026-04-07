package control;

import entity.combatants.Combatant;
import entity.combatants.Player;
import entity.combatants.Enemy;
import control.DifficultyLevel;
import control.TurnManager;
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
    private final CombatMenu         ui;
    private final LevelConfig       levelConfig;

    private boolean backupSpawned = false;


    public BattleEngine(Player player,
                        DifficultyLevel difficultyLevel,
                        BattleUI ui) {
        this.player          = player;
        this.difficultyLevel = difficultyLevel;
        this.ui              = ui;
        this.levelConfig     = LevelConfig.forDifficulty(difficultyLevel);
        this.turnStrategy    = new SpeedBasedStrategy();
        this.roundNumber     = 0;
        this.enemies         = new ArrayList<>(levelConfig.createInitialEnemies());
    }

    public void startGame() {
        ui.displayBattleStart(player, enemies, difficultyLevel);

        while (!checkGameEndCondition()) {
            executeRound();

            if (allEnemiesDefeated() && levelConfig.hasBackupWave() && !backupSpawned) {
                spawnBackup();
            }
        }

        displayEndScreen();
    }

    public void executeRound() {
        roundNumber++;
        ui.displayRoundHeader(roundNumber);

        applyStatusEffects();

        if (checkGameEndCondition()) return;

        List<Combatant> turnOrder = turnStrategy.determineTurnOrder(buildCombatantList());

        for (Combatant combatant : turnOrder) {

            if (!combatant.isAlive()) continue;          // already eliminated
            if (checkGameEndCondition())  break;          // battle over mid-round

            if (combatant.isStunned()) {
                ui.displayStunned(combatant);
                combatant.decrementStun();
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

        ui.displayRoundSummary(roundNumber, player, enemies);
    }


    public Player       getPlayer()       { return player; }
    public List<Enemy>  getEnemies()      { return new ArrayList<>(enemies); }
    public int          getCurrentRound() { return roundNumber; }


    private boolean checkGameEndCondition() {
        return !player.isAlive() || allEnemiesDefeated();
    }

    private void spawnBackup() {
        backupSpawned = true;
        List<Enemy> backupWave = levelConfig.createBackupEnemies();
        enemies = new ArrayList<>(backupWave);
        ui.displayBackupSpawn(backupWave);
    }

    private void applyStatusEffects() {
        for (Combatant c : buildCombatantList()) {
            if (c.isAlive()) {
                c.applyStatusEffects();
                c.tickStatusEffects();
            }
        }
    }
    private void handlePlayerTurn(Player p) {
        ui.displayPlayerTurnPrompt(p, getAliveEnemies());
        p.takeTurn(ui, getAliveEnemies());
    }

    private void handleEnemyTurn(Enemy e) {
        ui.displayEnemyTurn(e);
        e.takeTurn(player);
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
            ui.displayVictoryScreen(player, roundNumber);
        } else {
            long remaining = enemies.stream().filter(Combatant::isAlive).count();
            ui.displayDefeatScreen(roundNumber, (int) remaining);
        }
    }
}
