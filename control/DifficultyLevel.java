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
        switch (this) {
            case EASY -> {
                enemies.add(new Goblin());
                enemies.add(new Goblin());
                enemies.add(new Goblin());
            }
            case MEDIUM -> {
                enemies.add(new Goblin());
                enemies.add(new Wolf());
            }
            case HARD -> {
                enemies.add(new Goblin());
                enemies.add(new Goblin());
            }
        }
        return enemies;
    }
    
    public List<Enemy> getBackupSpawn() {
        List<Enemy> enemies = new ArrayList<>();
        switch (this) {
            case MEDIUM -> {
                enemies.add(new Wolf());
                enemies.add(new Wolf());
            }
            case HARD -> {
                enemies.add(new Goblin());
                enemies.add(new Wolf());
                enemies.add(new Wolf());
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
