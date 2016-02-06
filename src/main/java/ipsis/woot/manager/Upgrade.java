package ipsis.woot.manager;

public class Upgrade {

    Type type;
    Group group;
    float powerMult;
    int spawnTicks;

    public Upgrade(Type type, Group group, float powerMult, int spawnTicks) {
        this.type = type;
        this.group = group;
        this.powerMult = powerMult;
        this.spawnTicks = spawnTicks;
    }

    public boolean hasPowerMult() {
        return this.powerMult > 0;
    }

    public boolean hasSpawnTicks() {
        return this.spawnTicks > 0;
    }

    public float getPowerMult() { return this.powerMult; }
    public int getSpawnTicks() { return this.spawnTicks; }

    public Group getGroup() {
        return this.group;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        RATE_I,
        RATE_II,
        RATE_III,
        LOOTING_I,
        LOOTING_II,
        LOOTING_III,
        XP_I;
    }

    public enum Group {
        RATE,
        LOOTING,
        XP;
    }
}
