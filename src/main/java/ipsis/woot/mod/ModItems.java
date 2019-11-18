package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.debug.DebugItem;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryUpgrade;
import ipsis.woot.factory.items.UpgradeItem;
import ipsis.woot.shards.MobShardItem;
import ipsis.woot.tools.InternItem;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder(Woot.MODID + ":" + DebugItem.REGNAME)
    public static DebugItem DEBUG_ITEM;

    @ObjectHolder(Woot.MODID + ":" + InternItem.REGNAME)
    public static InternItem INTERN_ITEM;

    /**
     * Upgrade items
     */
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.CAPACITY_1_REGNAME)
    public static UpgradeItem CAPACITY_1_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.CAPACITY_2_REGNAME)
    public static UpgradeItem CAPACITY_2_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.CAPACITY_3_REGNAME)
    public static UpgradeItem CAPACITY_3_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.EFFICIENCY_1_REGNAME)
    public static UpgradeItem EFFICIENCY_1_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.EFFICIENCY_2_REGNAME)
    public static UpgradeItem EFFICIENCY_2_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.EFFICIENCY_3_REGNAME)
    public static UpgradeItem EFFICIENCY_3_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.LOOTING_1_REGNAME)
    public static UpgradeItem LOOTING_1_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.LOOTING_2_REGNAME)
    public static UpgradeItem LOOTING_2_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.LOOTING_3_REGNAME)
    public static UpgradeItem LOOTING_3_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.MASS_1_REGNAME)
    public static UpgradeItem MASS_1_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.MASS_2_REGNAME)
    public static UpgradeItem MASS_2_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.MASS_3_REGNAME)
    public static UpgradeItem MASS_3_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.RATE_1_REGNAME)
    public static UpgradeItem RATE_1_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.RATE_2_REGNAME)
    public static UpgradeItem RATE_2_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.RATE_3_REGNAME)
    public static UpgradeItem RATE_3_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.XP_1_REGNAME)
    public static UpgradeItem XP_1_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.XP_2_REGNAME)
    public static UpgradeItem XP_2_ITEM;
    @ObjectHolder(Woot.MODID + ":" + UpgradeItem.XP_3_REGNAME)
    public static UpgradeItem XP_3_ITEM;

    /**
     * Shards
     */
    @ObjectHolder(Woot.MODID + ":" + MobShardItem.REGNAME)
    public static MobShardItem MOB_SHARD_ITEM;
}
