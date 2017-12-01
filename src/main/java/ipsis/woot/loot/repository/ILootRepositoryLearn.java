package ipsis.woot.loot.repository;

import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.item.EntityItem;

import javax.annotation.Nonnull;
import java.util.List;

public interface ILootRepositoryLearn {

    // False returned for custom-drops-only mobs
    boolean isEmpty(WootMobName wootMobName, EnumEnchantKey key);

    // True returned for custom-drops-only mobs
    boolean isFull(WootMobName wootMobName, EnumEnchantKey key);

    // No effect for custom-drops-only mobs
    void learn(WootMobName wootMobName, EnumEnchantKey key, @Nonnull List<EntityItem> drops, boolean updateSampleCount);
}
