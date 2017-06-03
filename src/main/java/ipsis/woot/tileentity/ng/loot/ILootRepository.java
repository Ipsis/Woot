package ipsis.woot.tileentity.ng.loot;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.tileentity.ng.WootMobName;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface ILootRepository {

    boolean isEmpty(WootMobName wootMobName, EnumEnchantKey key);
    boolean isFull(WootMobName wootMobName, EnumEnchantKey key);
    @Nonnull List<ItemStack> getDrops(WootMobName wootMobName, EnumEnchantKey key);
    void insert(WootMobName wootMobName, EnumEnchantKey key, @Nonnull List<EntityItem> drops);
}
