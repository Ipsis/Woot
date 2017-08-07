package ipsis.woot.loot.repository;

import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;

public interface ILootRepositoryLookup {

    class LootItemStack {

        public ItemStack itemStack;
        public HashMap<Integer, Integer> sizes = new HashMap<>();
        public int dropChance = 0;

        public LootItemStack(ItemStack itemStack) {

            this.itemStack = itemStack;
        }
    }

    List<LootItemStack> getDrops(WootMobName wootMobName, EnumEnchantKey key);
    List<String> getAllMobs();
}
