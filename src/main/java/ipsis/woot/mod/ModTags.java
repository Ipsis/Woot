package ipsis.woot.mod;

import ipsis.woot.Woot;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags {

    public static class Blocks
    {
        public static final Tag<Block> FACTORY_BLOCK = tag("factory_blocks");

        private static Tag<Block> tag(String name)
        {
            return new BlockTags.Wrapper(new ResourceLocation(Woot.MODID, name));
        }
    }

    public static class Items {
        public static final Tag<Item> FACTORY_BLOCK = tag("factory_blocks");

        private static Tag<Item> tag(String name)
        {
            return new ItemTags.Wrapper(new ResourceLocation(Woot.MODID, name));
        }
    }
}
