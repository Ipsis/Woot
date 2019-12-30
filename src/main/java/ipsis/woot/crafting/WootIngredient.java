package ipsis.woot.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class WootIngredient {

    public ItemStack itemStack = ItemStack.EMPTY;
    public ResourceLocation tag;

    public WootIngredient(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public WootIngredient(ResourceLocation tag) {
        this.tag = tag;
    }

    public boolean isSameIngredient(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty())
            return false;

        if (this.itemStack != null && !this.itemStack.isEmpty()) {
            return ItemStack.areItemsEqual(this.itemStack, itemStack);
        } else if (this.tag != null) {
            Tag<Item> itemTag = ItemTags.getCollection().get(tag);
            if (itemTag != null && itemTag.getAllElements().contains(itemStack.getItem()))
                return true;
            Tag<Block> blockTags = BlockTags.getCollection().get(tag);
            if (blockTags != null) {
                for (Block b : blockTags.getAllElements()) {
                    if (itemStack.getItem() == b.asItem())
                        return true;
                }
            }
        }

        return false;
    }
}
