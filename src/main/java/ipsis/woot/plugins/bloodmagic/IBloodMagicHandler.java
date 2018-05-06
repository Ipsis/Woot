package ipsis.woot.plugins.bloodmagic;

import ipsis.woot.util.WootMobName;
import jline.internal.Nullable;

public interface IBloodMagicHandler {

    void keepAliveTankRitual();
    int getAltarSacrificePercentage();
    int getAltarSacrificeNumMobs();
    int getCrystalMobHealthPercentage();
    int getCrystalNumMobs();
    void clearAltarSacrificeNumMobs();
    void clearCrystalNumMobs();
    @Nullable WootMobName getWootMobName();

    /**
     * TODO
     *
     * Need a different mob count and clear for the altar and the crystal
     * They need to clear independently of each other
     */

}
