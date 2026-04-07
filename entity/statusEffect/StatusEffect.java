package status;

public abstract class StatusEffect {
    protected int duration;
    protected String effectName;

    public StatusEffect(int duration, String effectName) {
        this.duration = duration;
        this.effectName = effectName;
    }
    public abstract void apply(Combatant target);
    public abstract void remove(Combatant target);
    public void decreaseDuration() {
        if (duration > 0) {
            duration--;
        }
    }
    public int getDuration() {
        return duration;
    }
    public String getName() {
        return effectName;
    }
}
