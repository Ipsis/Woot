package ipsis.woot.tileentity.ng.farmstructure;

import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryModule;
import ipsis.woot.tileentity.ng.EnumFarmUpgrade;
import ipsis.woot.tileentity.ng.farmblocks.IFarmBlockController;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class FarmScanner implements IFarmScanner {

    private void scanFarmBaseTier(World world, BlockPos origin, EnumFacing facing, ScannedFarmBase base, EnumMobFactoryTier tier) {

        base.tier = null;
        base.clearBlocks();

        Set<BlockPos> blockPosList = new HashSet<>();
        BlockPos patternOrigin = origin;
        for (MobFactoryModule module : tier.getStructureModules()) {

            BlockPos p = BlockPosHelper.rotateFromSouth(module.getOffset(), facing.getOpposite());
            p = patternOrigin.add(p);

            if (!world.isBlockLoaded(p))
                return;

            IBlockState blockState = world.getBlockState(p);
            Block block = blockState.getBlock();

            if (!(block instanceof BlockMobFactoryStructure))
                return;

            if (!(((BlockMobFactoryStructure)block).getModuleTypeFromState(blockState) == module.getModuleType()))
                return;

            blockPosList.add(p);
        }

        base.addBlocks(blockPosList);
        base.tier = tier;
        LogHelper.info("Farm is correct for " + tier);
    }


    /**
     * IFarmScanner
     */

    @Nonnull
    @Override
    public ScannedFarmBase scanFarmStructure(World world, BlockPos origin, EnumFacing facing) {

        ScannedFarmBase base = new ScannedFarmBase();

        for (EnumMobFactoryTier tier : EnumMobFactoryTier.values()) {
            scanFarmBaseTier(world, origin, facing, base, tier);
            if (base.isValid())
                break;
        }

        return base;
    }

    @Nonnull
    @Override
    public ScannedFarmProxy scanFarmProxy(World world, BlockPos origin) {

        ScannedFarmProxy proxy = new ScannedFarmProxy();
        return proxy;
    }

    @Nonnull
    @Override
    public ScannedFarmController scanFarmController(World world, BlockPos origin) {

        ScannedFarmController controller = new ScannedFarmController();

        BlockPos pos = origin.up();
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IFarmBlockController && ((IFarmBlockController)te).isProgrammed()) {
            controller.setBlocks(pos);
            controller.wootMob = ((IFarmBlockController)te).getWootMob();
        }

        return controller;
    }

    @Nonnull
    @Override
    public ScannedFarmUpgrade scanFarmUpgrades(World world, BlockPos origin, EnumFacing facing, EnumMobFactoryTier tier) {

        ScannedFarmUpgrade upgrades = new ScannedFarmUpgrade();

        BlockPos[] positions;
        Class upgradeClass;

        if (tier == EnumMobFactoryTier.TIER_ONE) {
            positions = new BlockPos[]{
                    new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0)
            };
            upgradeClass = UpgradeTotemTierOne.class;
        } else if (tier == EnumMobFactoryTier.TIER_TWO) {
            positions = new BlockPos[]{
                    new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0),
                    new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0)};
            upgradeClass = UpgradeTotemTierTwo.class;
        } else {
            positions = new BlockPos[]{
                    new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0),
                    new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0),
                    new BlockPos(3, 0, 0), new BlockPos(-3, 0, 0)};
            upgradeClass = UpgradeTotemTierThree.class;
        }


        for (BlockPos p : positions) {
            BlockPos offset = BlockPosHelper.rotateFromSouth(p, facing.getOpposite());
            BlockPos p2 = origin.add(offset.getX(), offset.getY(), offset.getZ());

            AbstractUpgradeTotem upgradeTotem = AbstractUpgradeTotemBuilder.build(upgradeClass, world, p2);
            if (upgradeTotem != null) {
                upgradeTotem.scan();

                if (upgradeTotem.isValid()) {

                    ScannedFarmUpgrade.Upgrade upgrade = new ScannedFarmUpgrade.Upgrade();
                    upgrade.upgradeTier = upgradeTotem.spawnerUpgradeLevel;
                    upgrade.blocks.addAll(upgradeTotem.blockPosList);

                    if (EnumSpawnerUpgrade.isRateUpgrade(upgradeTotem.spawnerUpgrade))
                        upgrade.upgrade = EnumFarmUpgrade.RATE;
                    else if (EnumSpawnerUpgrade.isMassUpgrade(upgradeTotem.spawnerUpgrade))
                        upgrade.upgrade = EnumFarmUpgrade.MASS;
                    else if (EnumSpawnerUpgrade.isLootingUpgrade(upgradeTotem.spawnerUpgrade))
                        upgrade.upgrade = EnumFarmUpgrade.LOOTING;
                    else if (EnumSpawnerUpgrade.isXpUpgrade(upgradeTotem.spawnerUpgrade))
                        upgrade.upgrade = EnumFarmUpgrade.XP;
                    else if (EnumSpawnerUpgrade.isDecapitateUpgrade(upgradeTotem.spawnerUpgrade))
                        upgrade.upgrade = EnumFarmUpgrade.DECAPITATE;
                    else if (EnumSpawnerUpgrade.isEfficiencyUpgrade(upgradeTotem.spawnerUpgrade))
                        upgrade.upgrade = EnumFarmUpgrade.EFFICIENCY;
                    else if (EnumSpawnerUpgrade.isBloodMagicUpgrade(upgradeTotem.spawnerUpgrade))
                        upgrade.upgrade = EnumFarmUpgrade.LIFE_ESSENCE;

                    upgrades.addUpgrade(upgrade);
                }
            }
        }

        return upgrades;
    }
}
