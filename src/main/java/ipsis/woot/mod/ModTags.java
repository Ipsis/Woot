package ipsis.woot.mod;

import ipsis.woot.Woot;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;

public class ModTags {

    public static class Blocks
    {
        public static final ITag.INamedTag<Block> FACTORY_BLOCK = BlockTags.makeWrapperTag(Woot.MODID + ":" + "factory_blocks");
    }

    public static class Items {
        public static final ITag.INamedTag<Item> FACTORY_BLOCK = ItemTags.makeWrapperTag(Woot.MODID + ":" + "factory_blocks");
    }
}
