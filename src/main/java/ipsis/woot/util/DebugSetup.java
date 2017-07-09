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

    public void trace(EnumDebugType t, Object src, String f, Object object) {

        if (debugFlags.contains(t))
            LogHelper.info(src.getClass().toString() + ":" + f + ": " + object);
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
        ANVIL_CRAFTING
        ;

        public static final EnumSet<EnumDebugType> ALL_OPTS = EnumSet.allOf(EnumDebugType.class);
    }
}
