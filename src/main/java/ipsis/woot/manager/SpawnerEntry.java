package ipsis.woot.manager;

import ipsis.oss.LogHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpawnerEntry {

    static final int SAMPLE_SIZE = 100;
    int getSampleSize() { return SAMPLE_SIZE; }

    HashMap<SpawnerManager.EnchantKey, List<SpawnerDrops>> dropMap;

    public SpawnerEntry() {

        dropMap = new HashMap<SpawnerManager.EnchantKey, List<SpawnerDrops>>();
        for (SpawnerManager.EnchantKey enchantKey : SpawnerManager.EnchantKey.values())
            dropMap.put(enchantKey, new ArrayList<SpawnerDrops>());
    }

    public boolean isFull(SpawnerManager.EnchantKey enchantKey) {

        return dropMap.get(enchantKey).size() == getSampleSize();
    }

    public boolean addDrops(SpawnerManager.EnchantKey enchantKey, List<ItemStack> drops) {

        if (!isFull(enchantKey)) {
            dropMap.get(enchantKey).add(new SpawnerDrops(drops));

            StringBuilder sb = new StringBuilder();
            for (ItemStack itemStack : drops)
                sb.append(" ").append(itemStack.getDisplayName());
            LogHelper.info("addDrops: " + enchantKey + " " + sb.toString());

            return true;
        }

        return false;
    }

    public List<ItemStack> getDrops(SpawnerManager.EnchantKey enchantKey) {

        int pos = SpawnerManager.random.nextInt(dropMap.get(enchantKey).size());
        return new ArrayList<ItemStack>(dropMap.get(enchantKey).get(pos).drops);
    }

    class SpawnerDrops {
        List<ItemStack> drops = new ArrayList<ItemStack>();

        SpawnerDrops() { }
        SpawnerDrops(List<ItemStack> drops) {
            this.drops.addAll(drops);
        }
    }
}
