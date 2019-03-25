package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.debug.ItemDebug;
import ipsis.woot.factory.ItemUpgrade;
import ipsis.woot.tools.ItemHammer;
import ipsis.woot.tools.ItemIntern;
import ipsis.woot.tools.ItemMobShard;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder((Woot.MODID + ":" + ItemDebug.BASENAME))
    public static final ItemDebug debugItem = null;

    @ObjectHolder((Woot.MODID + ":" + ItemHammer.BASENAME))
    public static final ItemHammer hammerItem = null;

    @ObjectHolder((Woot.MODID + ":" + ItemIntern.BASENAME))
    public static final ItemIntern internItem = null;

    @ObjectHolder((Woot.MODID + ":" + ItemMobShard.BASENAME))
    public static final ItemMobShard mobShardItem = null;

    /**
     * Upgrades
     */
    @ObjectHolder((Woot.MODID + ":" + "upgrade_looting_1"))
    public static final ItemUpgrade upgradeLooting1Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_looting_2"))
    public static final ItemUpgrade upgradeLooting2Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_looting_3"))
    public static final ItemUpgrade upgradeLooting3Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_mass_1"))
    public static final ItemUpgrade upgradeMass1Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_mass_2"))
    public static final ItemUpgrade upgradeMass2Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_mass_3"))
    public static final ItemUpgrade upgradeMass3Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_rate_1"))
    public static final ItemUpgrade upgradeRate1Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_rate_2"))
    public static final ItemUpgrade upgradeRate2Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_rate_3"))
    public static final ItemUpgrade upgradeRate3Item = null;

}
