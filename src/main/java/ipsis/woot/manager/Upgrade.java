package ipsis.woot.manager;

import net.minecraft.util.IStringSerializable;

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

    public enum Type implements IStringSerializable {
        RATE_I("rate_I"),
        RATE_II("rate_II"),
        RATE_III("rate_III"),
        LOOTING_I("looting_I"),
        LOOTING_II("looting_II"),
        LOOTING_III("looting_III"),
        XP_I("xp_I");

        public static final Type[] VALUES = { RATE_I, RATE_II, RATE_III, LOOTING_I, LOOTING_II, LOOTING_III, XP_I };

        String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.toString();
        }

        public int getMetadata() {
            return this.ordinal();
        }

        public static Type byMetadata(int metadata) {

            if (metadata < 0 || metadata > VALUES.length)
                return RATE_I;

            return VALUES[metadata];
        }

        public SpawnerManager.EnchantKey getEnchantKey() {
            if (this == LOOTING_I)
                return SpawnerManager.EnchantKey.LOOTING_I;
            else if (this == LOOTING_II)
                return SpawnerManager.EnchantKey.LOOTING_II;
            else if (this == LOOTING_III)
                return SpawnerManager.EnchantKey.LOOTING_III;

            return SpawnerManager.EnchantKey.NO_ENCHANT;
        }
    }

    public enum Group {
        RATE,
        LOOTING,
        XP;
    }
}
