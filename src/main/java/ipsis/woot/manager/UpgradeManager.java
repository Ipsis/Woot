package ipsis.woot.manager;

import ipsis.woot.block.BlockMobFactoryUpgrade;
import ipsis.woot.reference.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;

public class UpgradeManager {

    static HashMap<EnumSpawnerUpgrade, SpawnerUpgrade> upgradeMap = new HashMap<EnumSpawnerUpgrade, SpawnerUpgrade>();
    static {
        for (EnumSpawnerUpgrade u : EnumSpawnerUpgrade.values()) {
            upgradeMap.put(u, new SpawnerUpgrade(u));
        }
    }

    public static void loadConfig() {

        upgradeMap.get(EnumSpawnerUpgrade.RATE_I).setPowerMultiplier(Settings.rateIMulti).setSpawnRate(Settings.rateITicks);
        upgradeMap.get(EnumSpawnerUpgrade.RATE_II).setPowerMultiplier(Settings.rateIIMulti).setSpawnRate(Settings.rateIITicks);
        upgradeMap.get(EnumSpawnerUpgrade.RATE_III).setPowerMultiplier(Settings.rateIIIMulti).setSpawnRate(Settings.rateIIITicks);

        upgradeMap.get(EnumSpawnerUpgrade.LOOTING_I).setPowerMultiplier(Settings.lootingIMulti);
        upgradeMap.get(EnumSpawnerUpgrade.LOOTING_II).setPowerMultiplier(Settings.lootingIIMulti);
        upgradeMap.get(EnumSpawnerUpgrade.LOOTING_III).setPowerMultiplier(Settings.lootingIIIMulti);

        upgradeMap.get(EnumSpawnerUpgrade.XP_I).setPowerMultiplier(Settings.xpIMulti);
        upgradeMap.get(EnumSpawnerUpgrade.XP_II).setPowerMultiplier(Settings.xpIIMulti);
        upgradeMap.get(EnumSpawnerUpgrade.XP_III).setPowerMultiplier(Settings.xpIIIMulti);
    }

    public static EnumEnchantKey getEnchantKey(List<SpawnerUpgrade> upgradeList) {

        int tier = 0;
        EnumEnchantKey enchantKey = EnumEnchantKey.NO_ENCHANT;
        for (SpawnerUpgrade upgrade : upgradeList) {
            if (upgrade.isLooting() && upgrade.getUpgradeTier() > tier) {
                enchantKey = upgrade.getEnchantKey();
                tier = upgrade.getUpgradeTier();
            }
        }

        return enchantKey;
    }

    public static SpawnerUpgrade scanUpgradeTotem(World world, BlockPos blockPos, int maxTier) {

        if (!world.isBlockLoaded(blockPos) || maxTier <= 0)
            return null;

        IBlockState iBlockState;
        Block block;

        SpawnerUpgrade upgrade = null;
        for (int yOffset = 0; yOffset < maxTier; yOffset++) {

            iBlockState = world.getBlockState(blockPos.add(0, yOffset, 0));
            block = iBlockState.getBlock();

            if (!(block instanceof BlockMobFactoryUpgrade))
                break;

            EnumSpawnerUpgrade u = iBlockState.getValue(BlockMobFactoryUpgrade.VARIANT);
            SpawnerUpgrade tmpUpgrade  = upgradeMap.get(u);
            if (tmpUpgrade.getUpgradeTier() == yOffset + 1)
                upgrade = tmpUpgrade;
            else
                break;
        }

        return upgrade;
    }
}
