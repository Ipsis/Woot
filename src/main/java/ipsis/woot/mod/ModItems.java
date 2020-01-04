package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.items.MobShardItem;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    /**
     * Shards
     */
    @ObjectHolder(Woot.MODID + ":" + MobShardItem.REGNAME)
    public static MobShardItem MOB_SHARD_ITEM;
}
