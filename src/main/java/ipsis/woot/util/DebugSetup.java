package ipsis.woot.util;

import ipsis.woot.oss.LogHelper;

import java.util.EnumSet;

public class DebugSetup {

    private EnumSet<EnumDebugType> debugFlags = EnumSet.noneOf(EnumDebugType.class);


    public void setDebug(EnumDebugType t) {

        debugFlags.add(t);
    }

    public void clearDebug(EnumDebugType t) {

        debugFlags.remove(t);
    }

    public void trace(EnumDebugType t, Object object) {

        if (debugFlags.contains(t))
            LogHelper.info(object);
    }

    @Override
    public String toString() {

        return " " + debugFlags.toString();
    }

    public enum EnumDebugType {
        POWER,
        LOOT_EVENTS,
        CONFIG_LOAD;

        public static final EnumSet<EnumDebugType> ALL_OPTS = EnumSet.allOf(EnumDebugType.class);
    }
}
