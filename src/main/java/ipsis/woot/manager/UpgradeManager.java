package ipsis.woot.manager;

import ipsis.woot.block.BlockMobFactoryUpgrade;
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

    public static SpawnerManager.EnchantKey getEnchantKey(List<SpawnerUpgrade> upgradeList) {

        // TODO walk for the highest looting
        return SpawnerManager.EnchantKey.NO_ENCHANT;
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
