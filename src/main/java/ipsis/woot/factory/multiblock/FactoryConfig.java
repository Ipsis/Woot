package ipsis.woot.factory.multiblock;

import ipsis.woot.Woot;
import ipsis.woot.config.ConfigRegistry;
import ipsis.woot.config.PolicyConfig;
import ipsis.woot.factory.FactoryTier;
import ipsis.woot.factory.ItemUpgrade;
import ipsis.woot.factory.TileEntityController;
import ipsis.woot.factory.TileEntityTotem;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.factory.layout.PatternBlock;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.MobHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactoryConfig {

    private BlockPos cellPos;
    private BlockPos exportPos;
    private BlockPos importPos;
    private BlockPos controllerPos;
    private List<BlockPos> upgradePos = new ArrayList<>();
    private FactoryTier factoryTier = FactoryTier.TIER_1;
    private List<FactoryConfigMob> mobs = new ArrayList<>();
    private FactoryConfigUpgrade factoryConfig = new FactoryConfigUpgrade();

    public BlockPos getCellPos() { return cellPos; }
    public BlockPos getExportPos() { return exportPos; }
    public BlockPos getImportPos() { return importPos; }
    public BlockPos getControllerPos() { return controllerPos; }
    public FactoryTier getFactoryTier() { return factoryTier; }
    public List<FakeMobKey> getValidMobs() {
        List<FakeMobKey> keys = new ArrayList<>();
        for (FactoryConfigMob mob : mobs)
            if (mob.state == FactoryConfigMob.MobState.VALID)
                keys.add(mob.fakeMobKey);
        return keys;
    }

    private boolean hasUpgrade(FactoryConfigUpgrade.Upgrade upgrade) {
        return factoryConfig.upgrades.containsKey(upgrade);
    }

    private int getUpgradeParam(FactoryConfigUpgrade.Upgrade upgrade) {
        return factoryConfig.upgrades.getOrDefault(upgrade, 0);
    }

    private int getUpgradeTier(FactoryConfigUpgrade.Upgrade upgrade) {
        if (hasUpgrade(upgrade))
            return factoryConfig.upgrades.get(upgrade).intValue();

        return 0;
    }

    public int getNumMobs(@Nonnull FakeMobKey fakeMobKey) {
        return ConfigRegistry.CONFIG_REGISTRY.getMass(fakeMobKey,
                hasUpgrade(FactoryConfigUpgrade.Upgrade.MASS) ? getUpgradeParam(FactoryConfigUpgrade.Upgrade.MASS) : 0);
    }

    public int getLooting() {
        if (hasUpgrade(FactoryConfigUpgrade.Upgrade.LOOTING))
            return getUpgradeParam(FactoryConfigUpgrade.Upgrade.LOOTING);
        return 0;
    }

    public class UpgradeInfo {
        private boolean valid = false;
        private int tier = 0;
        private int param = 0;

        public boolean isValid() { return valid; }
        public int getTier() { return tier; }
        public int getParam() { return param; }

        public UpgradeInfo(int tier, int param) {
            this.tier = tier;
            this.param = param;
            this.valid = true;
        }

        public UpgradeInfo() {
            this.valid = false;
        }
    }

    public UpgradeInfo getUpgradeInfo(FactoryConfigUpgrade.Upgrade upgrade) {
        if (!hasUpgrade(upgrade))
            return new UpgradeInfo();
        return new UpgradeInfo(getUpgradeTier(upgrade), getUpgradeParam(upgrade));
    }

    public static class FactoryConfigMob {
        enum MobState {
            VALID, BLACKLISTED, WRONG_TIER
        }

        public FakeMobKey fakeMobKey;
        public MobState state;
        public FactoryConfigMob(FakeMobKey fakeMobKey, MobState state) {
            this.fakeMobKey = fakeMobKey;
            this.state = state;
        }

        @Override
        public String toString() {
            return fakeMobKey + "(" + state + ")";
        }
    }

    public static class FactoryConfigUpgrade {
        public enum Upgrade {
            LOOTING, MASS, RATE;
        }

        private HashMap<Upgrade, Integer> upgrades = new HashMap<>();
        public void addUpgrade(ItemUpgrade.UpgradeType upgradeType) {
            int tier = 1;
            if (ItemUpgrade.UpgradeType.TIER_1.contains(upgradeType))
                tier = 1;
            else if (ItemUpgrade.UpgradeType.TIER_2.contains(upgradeType))
                tier = 2;
            else if (ItemUpgrade.UpgradeType.TIER_3.contains(upgradeType))
                tier = 3;

            if (ItemUpgrade.UpgradeType.LOOTING.contains(upgradeType))
                addTierUpgrade(Upgrade.LOOTING, tier);
            else if (ItemUpgrade.UpgradeType.MASS.contains(upgradeType))
                addTierUpgrade(Upgrade.MASS, tier);
            else if (ItemUpgrade.UpgradeType.RATE.contains(upgradeType))
                addTierUpgrade(Upgrade.RATE, tier);
        }

        private void addTierUpgrade(Upgrade upgrade, int tier) {
            int currTier = upgrades.getOrDefault(upgrade, 0);
            if (currTier < tier)
                upgrades.put(upgrade, tier);
        }
    }

    @Override
    public String toString() {
        String tmp = factoryTier + " ";
        for (FactoryConfigMob mob : mobs)
            tmp += mob + " ";
        return tmp;
    }

    public static FactoryConfig createFromLayout(World world, @Nonnull FactoryLayout layout) {

        if (world == null || !layout.isFormed() || layout.getAbsolutePattern() == null)
            return null;

        FactoryConfig factoryConfig = new FactoryConfig();
        for (PatternBlock patternBlock : layout.getAbsolutePattern().getBlocks()) {
            if (patternBlock.getFactoryBlock() == FactoryBlock.IMPORT)
                factoryConfig.importPos = patternBlock.getBlockPos();
            else if (patternBlock.getFactoryBlock() == FactoryBlock.EXPORT)
                factoryConfig.exportPos = patternBlock.getBlockPos();
            else if (patternBlock.getFactoryBlock() == FactoryBlock.CONTROLLER)
                factoryConfig.controllerPos = patternBlock.getBlockPos();
            else if (patternBlock.getFactoryBlock().isCell())
                factoryConfig.cellPos = patternBlock.getBlockPos();
            else if (patternBlock.getFactoryBlock() == FactoryBlock.UPGRADE)
                factoryConfig.upgradePos.add(patternBlock.getBlockPos());
        }

        factoryConfig.factoryTier = layout.getAbsolutePattern().getFactoryTier();

        TileEntity te = world.getTileEntity(factoryConfig.controllerPos);
        if (te instanceof TileEntityController) {
            TileEntityController controller = (TileEntityController)te;
            for (FakeMobKey key : controller.getFakeMobKeyList())
                if (PolicyConfig.canCapture(key.getResourceLocation())) {
                    if (MobHelper.canGenerateFromTier(world, key, factoryConfig.getFactoryTier()))
                        factoryConfig.mobs.add(new FactoryConfigMob(key, FactoryConfigMob.MobState.VALID));
                    else
                        factoryConfig.mobs.add(new FactoryConfigMob(key, FactoryConfigMob.MobState.WRONG_TIER));
                } else {
                    factoryConfig.mobs.add(new FactoryConfigMob(key, FactoryConfigMob.MobState.BLACKLISTED));
                }
        }

        /**
         * Process the factoryConfig
         */
        for (PatternBlock patternBlock : layout.getAbsolutePattern().getBlocks()) {
            if (patternBlock.getFactoryBlock() == FactoryBlock.UPGRADE) {
                BlockPos pos = patternBlock.getBlockPos().up();
                te = world.getTileEntity(pos);
                if (te instanceof TileEntityTotem) {
                    TileEntityTotem tileEntityTotem = (TileEntityTotem)te;
                    ItemUpgrade.UpgradeType upgradeType = tileEntityTotem.getUpgradeType();
                    if (upgradeType != null) {
                        // @todo has to be the correct tier
                        Woot.LOGGER.info("Adding upgrade " + upgradeType);
                        factoryConfig.factoryConfig.addUpgrade(upgradeType);
                    }
                }
            }
        }


        return factoryConfig;
    }
}
