package ipsis.woot.init;

import ipsis.woot.item.ItemShard;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDictionary {

    public static final String ORE_DICT_SKULL = "itemSkull";

    public static void preInit() {

        /**
         * Register the skulls under "itemSkull" which matches up with EnderIO and will pickup
         * the Enderman skulls
         */
        ItemStack skull = new ItemStack(Items.SKULL, 1, OreDictionary.WILDCARD_VALUE);
        OreDictionary.registerOre(ORE_DICT_SKULL, skull);

        /**
         * Register the shards under "nuggetX"
         */
        ItemStack itemStack = new ItemStack(ModItems.itemShard);
        itemStack.setItemDamage(ItemShard.EnumShardType.QUARTZ.getMeta());
        OreDictionary.registerOre("nuggetQuartz", itemStack);
        itemStack.setItemDamage(ItemShard.EnumShardType.DIAMOND.getMeta());
        OreDictionary.registerOre("nuggetDiamond", itemStack);
        itemStack.setItemDamage(ItemShard.EnumShardType.EMERALD.getMeta());
        OreDictionary.registerOre("nuggetEmerald", itemStack);
    }
}
