package ipsis.woot.tileentity.ng.loot;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.tileentity.ng.WootMobName;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class LootRepository implements ILootRepository {


    public boolean isEmpty(WootMobName wootMobName, EnumEnchantKey key) {

        return Woot.LOOT_TABLE_MANAGER.isEmpty(wootMobName.getName(), key);
    }

    public boolean isFull(WootMobName wootMobName, EnumEnchantKey key) {

        return Woot.LOOT_TABLE_MANAGER.isFull(wootMobName.getName(), key);
    }

    @Nonnull
    public List<ItemStack> getDrops(WootMobName wootMobName, EnumEnchantKey key) {

        return Woot.LOOT_TABLE_MANAGER.getDrops(wootMobName.getName(), key);
    }

    public void insert(WootMobName wootMobName, EnumEnchantKey key, @Nonnull List<EntityItem> drops) {

        Woot.LOOT_TABLE_MANAGER.update(wootMobName.getName(), key, drops, false);
    }
}
