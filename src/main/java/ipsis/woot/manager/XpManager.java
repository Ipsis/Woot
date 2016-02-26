package ipsis.woot.manager;

import ipsis.oss.LogHelper;

import java.util.HashMap;

public class XpManager {

    HashMap<String, XpInfo> xpInfoHashMap = new HashMap<String, XpInfo>();

    public void addMapping(String mobName, int mobXp) {

        xpInfoHashMap.put(mobName, new XpInfo(mobXp));
    }

    public int getSpawnXp(String mobName) {

        int xp = 1;
        if (xpInfoHashMap.containsKey(mobName))
            xp = xpInfoHashMap.get(mobName).getSpawnXp();

        LogHelper.info("getSpawnXp: "  + mobName + " " + xp);
        return xp;
    }

    public int getDeathXp(String mobName) {

        int xp = 1;
        if (xpInfoHashMap.containsKey(mobName))
            xp = xpInfoHashMap.get(mobName).getDeathXp();

        LogHelper.info("getDeathXp: "  + mobName + " " + xp);
        return xp;
    }

    public boolean isKnown(String mobName) {

        return xpInfoHashMap.containsKey(mobName);
    }

    class XpInfo {

        int spawnXp;
        int deathXp;

        public XpInfo(int mobXp) {

            if (mobXp == 0) {
                this.spawnXp = 1;
                this.deathXp = 0;
            } else {
                this.spawnXp = mobXp;
                this.deathXp = mobXp;
            }
        }

        public int getSpawnXp() { return this.spawnXp; }
        public int getDeathXp() { return this.deathXp; }
    }
}
