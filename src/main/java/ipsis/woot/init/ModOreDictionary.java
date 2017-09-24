package ipsis.woot.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDictionary {

    public static final String ORE_DICT_SKULL = "itemSkull";

    public static void init() {

        /**
         * Register the skulls under "itemSkull" which matches up with EnderIO and will pickup
         * the Enderman skulls
         */
        ItemStack skull = new ItemStack(Items.SKULL, 1, OreDictionary.WILDCARD_VALUE);
        OreDictionary.registerOre(ORE_DICT_SKULL, skull);
    }
}
