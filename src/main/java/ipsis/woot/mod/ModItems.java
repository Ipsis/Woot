package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.modules.tools.items.DebugItem;
import ipsis.woot.shards.MobShardItem;
import ipsis.woot.modules.tools.items.InternItem;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    /**
     * Shards
     */
    @ObjectHolder(Woot.MODID + ":" + MobShardItem.REGNAME)
    public static MobShardItem MOB_SHARD_ITEM;
}
