package ipsis.woot.plugins.bloodmagic;

import ipsis.woot.util.WootMobName;
import jline.internal.Nullable;

public interface IBloodMagicHandler {

    void keepAliveTankRitual();
    void keepAliveWillRitual();
    int getNumMobs();
    void clearMobs();
    int getAltarSacrificePercentage();
    @Nullable WootMobName getWootMobName();

}
