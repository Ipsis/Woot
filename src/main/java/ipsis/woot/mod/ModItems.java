package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.debug.ItemDebug;
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

}
