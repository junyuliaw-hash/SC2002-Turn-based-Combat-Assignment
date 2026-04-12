package control;

import entity.combatants.Enemy; 
import entity.combatants.Goblin; 
import entity.combatants.Wolf; 
import java.util.ArrayList;
import java.util.List;

public enum DifficultyLevel {
	EASY(1, "Easy"),
    MEDIUM(2, "Medium"),
    HARD(3, "Hard");
	
	private final int levelNo;
    private final String difficultyName;

    DifficultyLevel(int levelNo, String difficultyName) {
        this.levelNo = levelNo;
        this.difficultyName = difficultyName;
    }
    
    public List<Enemy> getInitialSpawn() {
        List<Enemy> enemies = new ArrayList<>();
        EnemySpawner spawner = new EnemySpawner();
        switch (this) {
            case EASY -> {
                enemies.add(spawner.spawn(Goblin.class));
                enemies.add(spawner.spawn(Goblin.class));
                enemies.add(spawner.spawn(Goblin.class));
            }
            case MEDIUM -> {
                enemies.add(spawner.spawn(Goblin.class));
                enemies.add(spawner.spawn(Wolf.class));
            }
            case HARD -> {
                enemies.add(spawner.spawn(Goblin.class));
                enemies.add(spawner.spawn(Goblin.class));
            }
        }
        return enemies;
    }
    
    public List<Enemy> getBackupSpawn() {
        List<Enemy> enemies = new ArrayList<>();
        EnemySpawner spawner = new EnemySpawner();
        switch (this) {
            case MEDIUM -> {
                enemies.add(spawner.spawn(Wolf.class));
                enemies.add(spawner.spawn(Wolf.class));
            }
            case HARD -> {
                enemies.add(spawner.spawn(Goblin.class));
                enemies.add(spawner.spawn(Wolf.class));
                enemies.add(spawner.spawn(Wolf.class));
            }
            default -> {} // Easy has no backup spawn // 
        }
        return enemies;
        }
    

    public String getDifficultyName() { 
    	return difficultyName; 
    }
    
    public int getLevelNo() { 
    	return levelNo; 
    }
}
