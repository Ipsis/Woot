package ipsis.woot.loot;

import ipsis.woot.loot.base.LootPool;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.loot.base.LootTable;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.FMLLogHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LootRepository implements ILootRepository {

    private HashMap<String, LootTable> lootTableHashMap = new HashMap<>();
    private HashMap<String, LootTable> staticLootTableHashMap = new HashMap<>();

    /**
     * ILootRepository
     */
    @Override
    public boolean isEmpty(WootMobName wootMobName, EnumEnchantKey key) {

        LootTable e = lootTableHashMap.get(wootMobName.getName());
        if (e == null)
            return true;

        return false;
    }

    @Override
    public boolean isFull(WootMobName wootMobName, EnumEnchantKey key) {

        LootTable e = lootTableHashMap.get(wootMobName.getName());
        if (e == null)
            return false;

        return e.isFull(key);
    }

    @Nonnull
    public List<ItemStack> getDrops(WootMobName wootMobName, EnumEnchantKey key, int numMobs) {

        List<ItemStack> drops = new ArrayList<>();
        LootTable e = lootTableHashMap.get(wootMobName.getName());
        if (e != null) {
            for (int c = 0; c <= numMobs; c++) {
                List<ItemStack> tmpDrops = new ArrayList<>();
                e.getDrops(key, tmpDrops);
                drops.addAll(tmpDrops);
            }
        }

        e = staticLootTableHashMap.get(wootMobName.getName());
        if (e != null) {
            for (int c = 0; c <= numMobs; c++) {
                List<ItemStack> tmpDrops = new ArrayList<>();
                e.getDrops(key, tmpDrops);
                drops.addAll(tmpDrops);
            }
        }

        return drops;
    }

    @Override
    public void insert(WootMobName wootMobName, EnumEnchantKey key, @Nonnull List<EntityItem> drops, boolean updateSampleCount) {

        LootTable e = lootTableHashMap.get(wootMobName.getName());
        if (e == null) {
            e = new LootTable(wootMobName.getName());
            lootTableHashMap.put(wootMobName.getName(), e);
        }

        e.update(key, drops, updateSampleCount);
    }

    @Override
    public void insertStatic(WootMobName wootMobName, EnumEnchantKey key, ItemStack itemStack, int dropChance) {

        LootTable e = staticLootTableHashMap.get(wootMobName.getName());
        if (e == null) {
            e = new LootTable(wootMobName.getName());
            staticLootTableHashMap.put(wootMobName.getName(), e);
        }

        e.insertStatic(key, itemStack, dropChance);
    }

    @Override
    public void flushAll() {

        lootTableHashMap = new HashMap<>();
    }

    @Override
    public void flushMob(WootMobName wootMobName) {

        lootTableHashMap.remove(wootMobName.getName());
    }

    @Nonnull
    @Override
    public List<String> getAllMobs() {

        List<String> mobs = new ArrayList<>();
        mobs.addAll(lootTableHashMap.keySet());
        return mobs;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDropInfo(WootMobName wootMobName, EnumEnchantKey key) {

        List<ItemStack> drops = new ArrayList<>();

        LootTable e = lootTableHashMap.get(wootMobName.getName());
        if (e != null)
            drops.addAll(e.getDropInfo(key));

        return drops;
    }

    @Override
    public void loadFromFile(File file) {

        LogHelper.info("LootRepository: Read loot from " + FMLLogHelper.stripString(file.toString()));
        lootTableHashMap = null;
        try {
            lootTableHashMap = LootRepositorySerialization.readFromFile(file);
        } catch (FileNotFoundException e) {
            /**
             * If it is not there we start empty
             */
            lootTableHashMap = new HashMap<>();
        }

        /**
         * Catch the case where the file exists but it is empty
         */
        if (lootTableHashMap == null)
            lootTableHashMap = new HashMap<>();
    }

    @Override
    public void saveToFile(File file) {

        LogHelper.info("LootRepository: Write loot to " + FMLLogHelper.stripString(file.toString()));
        LootRepositorySerialization.writeToFile(lootTableHashMap, file);
    }
}
