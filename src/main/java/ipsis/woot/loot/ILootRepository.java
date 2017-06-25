package ipsis.woot.loot;

import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;

public interface ILootRepository {

    boolean isEmpty(WootMobName wootMobName, EnumEnchantKey key);
    boolean isFull(WootMobName wootMobName, EnumEnchantKey key);
    @Nonnull List<ItemStack> getDrops(WootMobName wootMobName, EnumEnchantKey key, int numMobs);
    void insert(WootMobName wootMobName, EnumEnchantKey key, @Nonnull List<EntityItem> drops, boolean updateSampleCount);
    void insertStatic(WootMobName wootMobName, EnumEnchantKey key, ItemStack itemStack, int dropChance);
    void flushAll();
    void flushMob(WootMobName wootMobName);
    @Nonnull List<String> getAllMobs();

    void loadFromFile(File file);
    void saveToFile(File file);
}
