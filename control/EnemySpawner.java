package control;

import entity.combatants.*;
import java.util.HashMap;
import java.util.Map;

public class EnemySpawner {
    private Map<Class<? extends Enemy>, Integer> registry = new HashMap<>();

    public Enemy spawn(Class<? extends Enemy> enemyClass) {
        try {
            Enemy enemy = enemyClass.getDeclaredConstructor().newInstance();
            
            int id = registry.getOrDefault(enemyClass, 1);
            
            enemy.setName(enemy.getName() + " " + id);
            registry.put(enemyClass, id + 1);
            
            return enemy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void reset() {
        registry.clear();
    }
}