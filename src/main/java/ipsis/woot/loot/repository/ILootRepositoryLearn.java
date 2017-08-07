package ipsis.woot.loot.repository;

import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.item.EntityItem;

import javax.annotation.Nonnull;
import java.util.List;

public interface ILootRepositoryLearn {

    boolean isEmpty(WootMobName wootMobName, EnumEnchantKey key);
    boolean isFull(WootMobName wootMobName, EnumEnchantKey key);
    void learn(WootMobName wootMobName, EnumEnchantKey key, @Nonnull List<EntityItem> drops, boolean updateSampleCount);
}
