package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.shards.MobShardItem;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    /**
     * Shards
     */
    @ObjectHolder(Woot.MODID + ":" + MobShardItem.REGNAME)
    public static MobShardItem MOB_SHARD_ITEM;
}
