package ipsis.woot.manager;

import ipsis.Woot;
import ipsis.woot.reference.Settings;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * NB
 * We store the items and equipment dropped from the fake fill.
 * However, a mob will only have enchanted equipment a certain percentage of time.
 * Plus, it will only drop that equipment a certain percentage of time.
 * Therefore even if it spawns with one, doesn't say it turns into a drop.
 * eg. from a 100 spawns, 4 spawned with enchants, but only 1 dropped it.
 */
public class SpawnerEntry {

    HashMap<EnumEnchantKey, List<SpawnerDrops>> dropMap;

    public SpawnerEntry() {

        dropMap = new HashMap<EnumEnchantKey, List<SpawnerDrops>>();
        for (EnumEnchantKey enchantKey : EnumEnchantKey.values())
            dropMap.put(enchantKey, new ArrayList<SpawnerDrops>());
    }

    public boolean isFull(EnumEnchantKey enchantKey) {

        return dropMap.get(enchantKey).size() == Settings.sampleSize;
    }

    public boolean addDrops(EnumEnchantKey enchantKey, List<ItemStack> drops) {

        if (!isFull(enchantKey)) {
            dropMap.get(enchantKey).add(new SpawnerDrops(drops));
            return true;
        }

        return false;
    }

    /**
     * Returns an itemstack list that can be modified
     */
    public List<ItemStack> getDrops(EnumEnchantKey enchantKey) {

        ArrayList<ItemStack> dropList = new ArrayList<ItemStack>();

        int pos = Woot.RANDOM.nextInt(dropMap.get(enchantKey).size());
        for (ItemStack i : dropMap.get(enchantKey).get(pos).drops)
            dropList.add(ItemStack.copyItemStack(i));

        return dropList;
    }

    class SpawnerDrops {
        List<ItemStack> drops = new ArrayList<ItemStack>();

        SpawnerDrops() { }
        SpawnerDrops(List<ItemStack> dropList) {
            this.drops.addAll(dropList);
        }
    }
}
