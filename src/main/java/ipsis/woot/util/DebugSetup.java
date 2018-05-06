package ipsis.woot.util;

import ipsis.woot.oss.LogHelper;

import java.util.EnumSet;

public class DebugSetup {

    private EnumSet<EnumDebugType> debugFlags = EnumSet.noneOf(EnumDebugType.class);
    //private EnumSet<EnumDebugType> debugFlags = EnumSet.of(EnumDebugType.CONFIG_LOAD);

    public void setDebug(EnumDebugType t) {

        debugFlags.add(t);
    }

    public void clearDebug(EnumDebugType t) {

        debugFlags.remove(t);
    }

    public void trace(EnumDebugType t, String f, Object object) {

        if (debugFlags.contains(t))
            LogHelper.info(f + ": " + object);
    }

    public boolean areTracing(EnumDebugType t) {

        return debugFlags.contains(t);
    }

    @Override
    public String toString() {

        return " " + debugFlags.toString();
    }

    public enum EnumDebugType {
        POWER,
        LOOT_EVENTS,
        CONFIG_LOAD, // User cannot set this but I can during development!
        CONFIG_ACCESS,
        ANVIL_CRAFTING,
        FARM_SCAN,
        FARM_CLIENT_SYNC,
        FARM_BUILD,
        MULTIBLOCK,
        POWER_CALC,
        GEN_XP,
        GEN_ITEMS,
        GEN_BM_LE,
        GEN_BM_CRYSTAL,
        GEN_HEADS,
        GEN_EC,
        SPAWN,
        LEARN,
        TARTARUS,
        DECAP
        ;

        public static final EnumSet<EnumDebugType> ALL_OPTS = EnumSet.allOf(EnumDebugType.class);
    }
}
