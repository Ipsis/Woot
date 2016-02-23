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

        upgradeMap.get(EnumSpawnerUpgrade.RATE_I).setRfCostPerTick(Settings.rateIRfTick).setSpawnRate(Settings.rateITicks);
        upgradeMap.get(EnumSpawnerUpgrade.RATE_II).setRfCostPerTick(Settings.rateIIRfTick).setSpawnRate(Settings.rateIITicks);
        upgradeMap.get(EnumSpawnerUpgrade.RATE_III).setRfCostPerTick(Settings.rateIIIRfTick).setSpawnRate(Settings.rateIIITicks);

        upgradeMap.get(EnumSpawnerUpgrade.LOOTING_I).setRfCostPerTick(Settings.lootingIRfTick);
        upgradeMap.get(EnumSpawnerUpgrade.LOOTING_II).setRfCostPerTick(Settings.lootingIIRfTick);
        upgradeMap.get(EnumSpawnerUpgrade.LOOTING_III).setRfCostPerTick(Settings.lootingIIIRfTick);

        upgradeMap.get(EnumSpawnerUpgrade.XP_I).setRfCostPerTick(Settings.xpIRfTick).setXpBoost(Settings.xpIBoost);
        upgradeMap.get(EnumSpawnerUpgrade.XP_II).setRfCostPerTick(Settings.xpIIRfTick).setXpBoost(Settings.xpIIBoost);
        upgradeMap.get(EnumSpawnerUpgrade.XP_III).setRfCostPerTick(Settings.xpIIIRfTick).setXpBoost(Settings.xpIIIBoost);

        upgradeMap.get(EnumSpawnerUpgrade.MASS_I).setRfCostPerTick(Settings.massIRfTick).setMass(Settings.massIMobs);
        upgradeMap.get(EnumSpawnerUpgrade.MASS_II).setRfCostPerTick(Settings.massIIRfTick).setMass(Settings.massIIMobs);
        upgradeMap.get(EnumSpawnerUpgrade.MASS_III).setRfCostPerTick(Settings.massIIIRfTick).setMass(Settings.massIIIMobs);

        upgradeMap.get(EnumSpawnerUpgrade.DECAPITATE_I).setRfCostPerTick(Settings.decapitateIRfTick).setDecapitateChance(Settings.decapitateIChance);
        upgradeMap.get(EnumSpawnerUpgrade.DECAPITATE_II).setRfCostPerTick(Settings.decapitateIIRfTick).setDecapitateChance(Settings.decapitateIIChance);
        upgradeMap.get(EnumSpawnerUpgrade.DECAPITATE_III).setRfCostPerTick(Settings.decapitateIIIRfTick).setDecapitateChance(Settings.decapitateIIIChance);
    }

    public static EnumEnchantKey getLootingEnchant(List<SpawnerUpgrade> upgradeList) {

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

    public static SpawnerUpgrade getMassUpgrade(List<SpawnerUpgrade> upgradeList) {

        SpawnerUpgrade massUpgrade = null;
        int tier = 0;
        for (SpawnerUpgrade upgrade : upgradeList) {
            if (upgrade.isMass() && upgrade.getUpgradeTier() > tier) {
                tier = upgrade.getUpgradeTier();
                massUpgrade = upgrade;
            }
        }

        return massUpgrade;
    }

    public static SpawnerUpgrade getRateUpgrade(List<SpawnerUpgrade> upgradeList) {

        SpawnerUpgrade u = null;
        int tier = 0;
        for (SpawnerUpgrade upgrade : upgradeList) {
            if (upgrade.isRate() && upgrade.getUpgradeTier() > tier) {
                tier = upgrade.getUpgradeTier();
                u = upgrade;
            }
        }

        return u;
    }

    public static SpawnerUpgrade getLootingUpgrade(List<SpawnerUpgrade> upgradeList) {

        SpawnerUpgrade u = null;
        int tier = 0;
        for (SpawnerUpgrade upgrade : upgradeList) {
            if (upgrade.isRate() && upgrade.getUpgradeTier() > tier) {
                tier = upgrade.getUpgradeTier();
                u = upgrade;
            }
        }

        return u;
    }

    public static SpawnerUpgrade getDecapitateUpgrade(List<SpawnerUpgrade> upgradeList) {

        SpawnerUpgrade u = null;
        int tier = 0;
        for (SpawnerUpgrade upgrade : upgradeList) {
            if (upgrade.isDecapitate() && upgrade.getUpgradeTier() > tier) {
                tier = upgrade.getUpgradeTier();
                u = upgrade;
            }
        }

        return u;
    }

    public static SpawnerUpgrade getXpUpgrade(List<SpawnerUpgrade> upgradeList) {

        SpawnerUpgrade xpUpgrade = null;
        int tier = 0;
        for (SpawnerUpgrade upgrade : upgradeList) {
            if (upgrade.isXp() && upgrade.getUpgradeTier() > tier) {
                tier = upgrade.getUpgradeTier();
                xpUpgrade = upgrade;
            }
        }

        return xpUpgrade;
    }

    public static List<SpawnerUpgrade> scanUpgradeTotem(World world, BlockPos blockPos, int maxTier, List<SpawnerUpgrade> upgradeList, List<BlockPos> blockPosList) {

        if (!world.isBlockLoaded(blockPos) || maxTier <= 0)
            return upgradeList;

        IBlockState iBlockState;
        Block block;

        for (int yOffset = 0; yOffset < maxTier; yOffset++) {

            iBlockState = world.getBlockState(blockPos.add(0, yOffset, 0));
            block = iBlockState.getBlock();

            if (!(block instanceof BlockMobFactoryUpgrade))
                break;

            EnumSpawnerUpgrade u = iBlockState.getValue(BlockMobFactoryUpgrade.VARIANT);
            SpawnerUpgrade tmpUpgrade  = upgradeMap.get(u);
            if (tmpUpgrade.getUpgradeTier() == yOffset + 1) {
                upgradeList.add(tmpUpgrade);
                blockPosList.add(new BlockPos(blockPos.add(0, yOffset, 0)));
            } else {
                break;
            }
        }

        return upgradeList;
    }

    public static SpawnerUpgrade getSpawnerUpgrade(EnumSpawnerUpgrade u) {

        return upgradeMap.get(u);
    }
}
