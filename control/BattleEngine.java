package engine;

import model.combatant.Combatant;
import model.combatant.Player;
import model.combatant.Enemy;
import model.level.DifficultyLevel;
import model.level.LevelConfig;
import strategy.TurnOrderStrategy;
import strategy.SpeedBasedTurnOrder;
import ui.BattleUI;

import java.util.ArrayList;
import java.util.List;

/**
 * BattleEngine: Central control class responsible for managing the flow of battle.
 *
 * SRP  - Manages battle rounds, turn execution, and game-end detection only.
 * DIP  - Depends on abstractions: Combatant, TurnOrderStrategy, BattleUI.
 * OCP  - New actions / status effects added without modifying this class.
 */
public class BattleEngine {

    // ─── Fields ──────────────────────────────────────────────────────────────

    private final Player            player;
    private       List<Enemy>       enemies;
    private       TurnOrderStrategy turnStrategy;
    private       int               roundNumber;
    private final DifficultyLevel   difficultyLevel;
    private final BattleUI          ui;
    private final LevelConfig       levelConfig;

    /** True once the backup wave has already been spawned for this level. */
    private boolean backupSpawned = false;

    // ─── Constructor ─────────────────────────────────────────────────────────

    public BattleEngine(Player player,
                        DifficultyLevel difficultyLevel,
                        BattleUI ui) {
        this.player          = player;
        this.difficultyLevel = difficultyLevel;
        this.ui              = ui;
        this.levelConfig     = LevelConfig.forDifficulty(difficultyLevel);
        this.turnStrategy    = new SpeedBasedTurnOrder();
        this.roundNumber     = 0;
        this.enemies         = new ArrayList<>(levelConfig.createInitialEnemies());
    }

    // ─── Public API ──────────────────────────────────────────────────────────

    /**
     * Entry point: initialises the battle and loops until a winner is found.
     */
    public void startGame() {
        ui.displayBattleStart(player, enemies, difficultyLevel);

        while (!checkGameEndCondition()) {
            executeRound();

            // After each round check whether the initial wave was wiped out
            // and a backup wave should replace it.
            if (allEnemiesDefeated() && levelConfig.hasBackupWave() && !backupSpawned) {
                spawnBackup();
            }
        }

        displayEndScreen();
    }

    /**
     * Executes one complete round: applies existing status effects, then lets
     * every living combatant take a turn in speed order.
     */
    public void executeRound() {
        roundNumber++;
        ui.displayRoundHeader(roundNumber);

        // 1. Apply lingering status effects before any actions.
        applyStatusEffects();

        // Check if anyone died from status effects before acting.
        if (checkGameEndCondition()) return;

        // 2. Determine turn order for this round.
        List<Combatant> turnOrder = turnStrategy.determineTurnOrder(buildCombatantList());

        // 3. Each combatant takes one action.
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

            // Check end condition after every individual action.
            if (checkGameEndCondition()) break;
        }

        // 4. Tick down cooldowns for every living combatant that acted.
        tickCooldowns(turnOrder);

        // 5. End-of-round summary.
        ui.displayRoundSummary(roundNumber, player, enemies);
    }

    // ─── Getters (required by UML) ────────────────────────────────────────────

    public Player       getPlayer()       { return player; }
    public List<Enemy>  getEnemies()      { return new ArrayList<>(enemies); }
    public int          getCurrentRound() { return roundNumber; }

    // ─── Private helpers ─────────────────────────────────────────────────────

    /**
     * Checks whether the game has reached an end state.
     *
     * @return true if the player or all enemies are defeated.
     */
    private boolean checkGameEndCondition() {
        return !player.isAlive() || allEnemiesDefeated();
    }

    /**
     * Spawns the backup enemy wave simultaneously, replacing the defeated wave.
     * Only called once per level (guarded by backupSpawned flag).
     */
    private void spawnBackup() {
        backupSpawned = true;
        List<Enemy> backupWave = levelConfig.createBackupEnemies();
        enemies = new ArrayList<>(backupWave);
        ui.displayBackupSpawn(backupWave);
    }

    /**
     * Applies all active status effects to every living combatant
     * and then advances their duration by one tick.
     */
    private void applyStatusEffects() {
        for (Combatant c : buildCombatantList()) {
            if (c.isAlive()) {
                c.applyStatusEffects();   // each StatusEffect handles its own logic
                c.tickStatusEffects();    // removes expired effects
            }
        }
    }

    /**
     * Presents the player's action menu and delegates to the chosen action.
     */
    private void handlePlayerTurn(Player p) {
        ui.displayPlayerTurnPrompt(p, getAliveEnemies());
        // Action selection and execution delegated to UI + Action classes.
        // This keeps BattleEngine free of action-specific logic (OCP / SRP).
        p.takeTurn(ui, getAliveEnemies());
    }

    /**
     * Enemies always execute BasicAttack on their turn (per spec §4 vii).
     * Enemy AI strategy is injected so it can be swapped later (DIP / OCP).
     */
    private void handleEnemyTurn(Enemy e) {
        ui.displayEnemyTurn(e);
        e.takeTurn(player);   // Enemy delegates to its ActionStrategy
    }

    /**
     * Decrements the special-skill cooldown for every combatant that
     * participated in this round (alive or just defeated — spec says cooldown
     * decreases only when a turn by the combatant took place).
     */
    private void tickCooldowns(List<Combatant> participated) {
        for (Combatant c : participated) {
            c.tickCooldown();
        }
    }

    /** @return true when every enemy in the current wave is at 0 HP. */
    private boolean allEnemiesDefeated() {
        return enemies.stream().noneMatch(Combatant::isAlive);
    }

    /** @return a flat list of [player] + enemies for turn-ordering. */
    private List<Combatant> buildCombatantList() {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(enemies);
        return all;
    }

    /** @return only the enemies whose HP > 0. */
    private List<Enemy> getAliveEnemies() {
        return enemies.stream()
                      .filter(Combatant::isAlive)
                      .collect(java.util.stream.Collectors.toList());
    }

    /** Renders the victory or defeat screen with appropriate statistics. */
    private void displayEndScreen() {
        if (player.isAlive()) {
            ui.displayVictory(player, roundNumber);
        } else {
            long remaining = enemies.stream().filter(Combatant::isAlive).count();
            ui.displayDefeat(roundNumber, (int) remaining);
        }
    }
}
