package entity.actions;

public abstract class AbstractSkill implements SpecialSkill {
    protected String name;
    protected int currentCooldown = 0;
    protected int maxCooldown = 3;

    @Override
    public void updateCooldown() {
        if (currentCooldown > 0) currentCooldown--;
    }

    @Override
    public boolean isReady() {
        return currentCooldown == 0;
    }

    @Override
    public int getCooldown() {
        return currentCooldown;
    }

    @Override
    public void resetCooldown() {
        currentCooldown = maxCooldown;
    }

    @Override
    public String getName() {
        return name;
    }
}
