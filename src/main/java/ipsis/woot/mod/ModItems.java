package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.debug.DebugItem;
import ipsis.woot.modules.factory.items.UpgradeItem;
import ipsis.woot.shards.MobShardItem;
import ipsis.woot.tools.InternItem;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder(Woot.MODID + ":" + DebugItem.REGNAME)
    public static DebugItem DEBUG_ITEM;

    @ObjectHolder(Woot.MODID + ":" + InternItem.REGNAME)
    public static InternItem INTERN_ITEM;

    /**
     * Shards
     */
    @ObjectHolder(Woot.MODID + ":" + MobShardItem.REGNAME)
    public static MobShardItem MOB_SHARD_ITEM;
}
