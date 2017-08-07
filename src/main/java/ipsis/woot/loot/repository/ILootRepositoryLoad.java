package ipsis.woot.loot.repository;

import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;

public interface ILootRepositoryLoad {

    void loadMobSample(WootMobName wootMobName, int l0, int l1, int l2, int l3);
    void loadItem(ItemStack itemStack);
    void loadMobDrop(WootMobName wootMobName, EnumEnchantKey key, ItemStack itemStack, int stackSize, int drops);
}
