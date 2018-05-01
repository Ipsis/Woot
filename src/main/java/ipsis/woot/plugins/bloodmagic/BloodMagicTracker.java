package ipsis.woot.plugins.bloodmagic;

import ipsis.woot.util.WootMobName;

import javax.annotation.Nullable;

/**
 * This is used to pass information between the rituals and the factory
 */
public class BloodMagicTracker {

    private boolean kaTank = false;
    private int altarMobCount = 0;
    private WootMobName wootMobName = null;

    public void tickTank() {
        kaTank = true;
    }

    public void clearTank() {
        kaTank = false;
    }

    public boolean isTankAlive() {
        return kaTank;
    }

    public int getAltarMobCount() {
        return altarMobCount;
    }

    public void setAltarMobCount(int count) {
        altarMobCount = count;
    }

    public void clearAltarMobCount() {
        altarMobCount = 0;
    }

    public void setWootMobName(WootMobName wootMobName) {
        this.wootMobName = wootMobName;
    }

    public @Nullable WootMobName getWootMobName() {

        return wootMobName;
    }
}
