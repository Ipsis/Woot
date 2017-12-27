package ipsis.woot.loot.customdrops;

import ipsis.woot.loot.repository.ILootRepositoryLookup;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomDropsRepository implements ILootRepositoryLookup {

    private List<CustomDrop> drops = new ArrayList<>();

    @Override
    public List<LootItemStack> getDrops(WootMobName wootMobName, EnumEnchantKey key) {

        List<LootItemStack> lootDrops = new ArrayList<>();

        for (CustomDrop customDrop : drops) {
            if (!customDrop.wootMobName.equals(wootMobName))
                continue;

            LootItemStack lootItemStack = new LootItemStack(customDrop.itemStack.copy());

            lootItemStack.sizes.put(customDrop.sizeMap.get(key), customDrop.chanceMap.get(key));
            lootItemStack.dropChance = customDrop.chanceMap.get(key);
            lootDrops.add(lootItemStack);
        }

        return lootDrops;
    }

    @Override
    public List<String> getAllMobs() {

        List<String> mobs = new ArrayList<>();
        for (CustomDrop customDrop : drops)
            mobs.add(customDrop.wootMobName.getName());
        return mobs;
    }

    public void addDrop(WootMobName wootMobName, ItemStack itemStack, List<Integer> chances, List<Integer> sizes) {

        if (chances.size() != 4 || sizes.size() != 4) {
            LogHelper.error("Chances and size arrays must have four entries each - " + wootMobName + "/" + itemStack);
            return;
        }

        CustomDrop customDrop = new CustomDrop(wootMobName, itemStack, chances, sizes);
        drops.add(customDrop);

        LogHelper.info("Added custom drop " + itemStack.getDisplayName() + " for " + wootMobName);
    }

    private class CustomDrop {

        WootMobName wootMobName;
        ItemStack itemStack;
        HashMap<EnumEnchantKey, Integer> chanceMap = new HashMap<>();
        HashMap<EnumEnchantKey, Integer> sizeMap = new HashMap<>();

        public CustomDrop(WootMobName wootMobName, ItemStack itemStack, List<Integer> chances, List<Integer> sizes) {

            this.wootMobName = wootMobName;
            this.itemStack = itemStack.copy();
            chanceMap.put(EnumEnchantKey.NO_ENCHANT, chances.get(0));
            chanceMap.put(EnumEnchantKey.LOOTING_I, chances.get(1));
            chanceMap.put(EnumEnchantKey.LOOTING_II, chances.get(2));
            chanceMap.put(EnumEnchantKey.LOOTING_III, chances.get(3));
            sizeMap.put(EnumEnchantKey.NO_ENCHANT, sizes.get(0));
            sizeMap.put(EnumEnchantKey.LOOTING_I, sizes.get(1));
            sizeMap.put(EnumEnchantKey.LOOTING_II, sizes.get(2));
            sizeMap.put(EnumEnchantKey.LOOTING_III, sizes.get(3));
        }
    }
}
