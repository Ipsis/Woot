package ipsis.woot.manager;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SpawnerManager {

    public static Random random = new Random();
    static HashMap<String, SpawnerEntry> map = new HashMap<String, SpawnerEntry>();

    public static void addSpawnerDrops(String mob, EnchantKey enchantKey, List<EntityItem> dropList) {

        if (!map.containsKey(mob))
            map.put(mob, new SpawnerEntry());

        if (map.get(mob).isFull(enchantKey))
            return;

        List<ItemStack> itemStackList = new ArrayList<ItemStack>();
        for (EntityItem entityItem : dropList) {
            itemStackList.add(entityItem.getEntityItem().copy());
        }
        map.get(mob).addDrops(enchantKey, itemStackList);
    }

    public static boolean isFull(String mob, EnchantKey enchantKey) {

        if (!map.containsKey(mob))
            return false;

        return map.get(mob).isFull(enchantKey);
    }

    public static List<ItemStack> getSpawnerDrops(String mob, EnchantKey enchantKey) {

        if (!map.containsKey(mob))
            return new ArrayList<ItemStack>();

        return map.get(mob).getDrops(enchantKey);
    }

    public enum EnchantKey {
        NO_ENCHANT,
        LOOTING_I,
        LOOTING_II,
        LOOTING_III;
    }
}
