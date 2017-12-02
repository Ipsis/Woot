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

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();
            sb.append(itemStack);
            sb.append(" chance:");
            sb.append(dropChance);
            sb.append("% ");

            for (Integer k : sizes.keySet()) {
                sb.append(k);
                sb.append(":");
                sb.append(sizes.get(k));
                sb.append("% ");
            }

            return sb.toString();
        }
    }

    // Empty list returned for custom-drops-only mobs
    List<LootItemStack> getDrops(WootMobName wootMobName, EnumEnchantKey key);
    List<String> getAllMobs();
}
