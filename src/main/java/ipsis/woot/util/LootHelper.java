package ipsis.woot.util;

import ipsis.Woot;
import ipsis.woot.loot.repository.ILootRepositoryLookup;

import java.util.ArrayList;
import java.util.List;

public class LootHelper {

    /**
     * Handles the two repositories and makes sure that the custom drops takes priority
     */
    public static List<ILootRepositoryLookup.LootItemStack> getDrops(WootMobName wootMobName, EnumEnchantKey key) {

        List<ILootRepositoryLookup.LootItemStack> drops = new ArrayList<>();

        drops.addAll(Woot.lootRepository.getDrops(wootMobName, key));
        drops.addAll(Woot.customDropsRepository.getDrops(wootMobName, key));

        return drops;
    }
}
