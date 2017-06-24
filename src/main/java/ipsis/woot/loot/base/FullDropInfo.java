package ipsis.woot.loot.base;

import net.minecraft.item.ItemStack;

public class FullDropInfo {

    ItemStack itemStack;
    float dropChance;

    public FullDropInfo(ItemStack itemStack, float dropChance) {

        this.itemStack = itemStack;
        this.dropChance = dropChance;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public float getDropChance() {
        return this.dropChance;
    }
}
