package ipsis.woot.farmstructure;

import ipsis.Woot;
import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.multiblock.MobFactoryModule;
import ipsis.woot.tileentity.TileEntityMobFactoryCell;
import ipsis.woot.tileentity.TileEntityMobFactoryExporter;
import ipsis.woot.tileentity.TileEntityMobFactoryImporter;
import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.farmblocks.IFarmBlockController;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FarmScanner implements IFarmScanner {

    private List<BadFarmBlock> scanFarmTier(World world, BlockPos origin, EnumFacing facing, EnumMobFactoryTier tier) {

        List<BadFarmBlock> badFarmBlocks = new ArrayList<>();

        Set<BlockPos> blockPosList = new HashSet<>();
        BlockPos patternOrigin = origin;
        for (MobFactoryModule module : tier.getStructureModules()) {

            BlockPos p = BlockPosHelper.rotateFromSouth(module.getOffset(), facing.getOpposite());
            p = patternOrigin.add(p);

            if (!world.isBlockLoaded(p)) {
                badFarmBlocks.add(new BadFarmBlock(p, ModBlocks.blockStructure, module.getModuleType().getMetadata()));
                continue;
            }

            IBlockState blockState = world.getBlockState(p);
            Block block = blockState.getBlock();

            if (!(block instanceof BlockMobFactoryStructure)) {
                badFarmBlocks.add(new BadFarmBlock(p, ModBlocks.blockStructure, module.getModuleType().getMetadata()));
                continue;
            }

            if (!(((BlockMobFactoryStructure)block).getModuleTypeFromState(blockState) == module.getModuleType())) {
                badFarmBlocks.add(new BadFarmBlock(p, ModBlocks.blockStructure, module.getModuleType().getMetadata()));
                continue;
            }

            blockPosList.add(p);
        }

        return badFarmBlocks;
    }

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
    }


    /**
     * IFarmScanner
     */

    @Nonnull
    @Override
    public ScannedFarmBase scanFarmStructure(World world, BlockPos origin, EnumFacing facing) {

        ScannedFarmBase base = new ScannedFarmBase();

        for (int i = EnumMobFactoryTier.values().length - 1; i >= 0; i--) {
            scanFarmBaseTier(world, origin, facing, base, EnumMobFactoryTier.values()[i]);
            if (base.isValid())
                break;
        }

        return base;
    }

    @Nonnull
    @Override
    public ScannedFarmController scanFarmController(World world, BlockPos origin, EnumFacing facing) {

        ScannedFarmController controller = new ScannedFarmController();

        BlockPos pos = origin.up().offset(facing, -1);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IFarmBlockController && ((IFarmBlockController)te).isProgrammed()) {
            controller.setBlocks(pos);
            controller.wootMob = ((IFarmBlockController)te).getWootMob();
        }

        return controller;
    }

    @Nonnull
    @Override
    public ScannedFarmRemote scanFarmRemote(World world, BlockPos origin) {

        ScannedFarmRemote remote = new ScannedFarmRemote();

        boolean powerFound = false;
        boolean exportFound = false;
        boolean importFound = false;
        for (int step = 0; step < 10; step++) {

            BlockPos p = origin.down(step + 1);
            TileEntity te = world.getTileEntity(p);
            if (te instanceof TileEntityMobFactoryCell && !powerFound && ((TileEntityMobFactoryCell) te).getPowerStation() != null) {
                powerFound = true;
                remote.setPowerPos(p);
            } else if (te instanceof TileEntityMobFactoryExporter && !exportFound) {
                exportFound = true;
                remote.setExportPos(p);
            } else if (te instanceof TileEntityMobFactoryImporter && !importFound) {
                importFound = true;
                remote.setImportPos(p);
            }

            if (powerFound && exportFound && importFound)
                break;
        }

        return remote;
    }

    @Nonnull
    @Override
    public ScannedFarmUpgrade scanFarmUpgrades(World world, BlockPos origin, EnumFacing facing, EnumMobFactoryTier tier) {

        ScannedFarmUpgrade upgrades = new ScannedFarmUpgrade();

        BlockPos[] positions;
        Class upgradeClass;

        if (tier == EnumMobFactoryTier.TIER_ONE) {
            positions = new BlockPos[]{
                    new BlockPos(1, 1, 1), new BlockPos(-1, 1, 1)
            };
            upgradeClass = UpgradeTotemTierOne.class;
        } else if (tier == EnumMobFactoryTier.TIER_TWO) {
            positions = new BlockPos[]{
                    new BlockPos(1, 1, 1), new BlockPos(-1, 1, 1),
                    new BlockPos(2, 1, 1), new BlockPos(-2, 1, 1)};
            upgradeClass = UpgradeTotemTierTwo.class;
        } else {
            positions = new BlockPos[]{
                    new BlockPos(1, 1, 1), new BlockPos(-1, 1, 1),
                    new BlockPos(2, 1, 1), new BlockPos(-2, 1, 1),
                    new BlockPos(3, 1, 1), new BlockPos(-3, 1, 1)};
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
                    upgrade.upgrade = EnumFarmUpgrade.getFromEnumSpawnerUpgrade(upgradeTotem.spawnerUpgrade);
                    upgrades.addUpgrade(upgrade);
                }
            }
        }

        return upgrades;
    }

    @Override
    public void applyConfiguration(World world, @Nonnull ScannedFarmController farmController, @Nonnull ScannedFarmUpgrade farmUpgrade, EnumMobFactoryTier tier) {

        EnumMobFactoryTier mobTier = Woot.wootConfiguration.getFactoryTier(world, farmController.wootMob.getWootMobName());

        // TODO fix using the ordinal to order the tier enum
        if (mobTier.ordinal() > tier.ordinal()) {
            LogHelper.info("TODO Factory tier not good enough for mob");
        }

        for (ScannedFarmUpgrade.Upgrade upgrade : farmUpgrade.getUpgrades()) {

            LogHelper.info("TODO validate upgrade against mob");
        }
    }

    @Override
    public void scanFarmNoStop(World world, BlockPos origin, EnumFacing facing, EnumMobFactoryTier tier, BadFarmInfo badFarmInfo) {

        /**
         * Scan main farm
         */
        badFarmInfo.badFarmBlocks.addAll(scanFarmTier(world, origin, facing, tier));

        /**
         * Scan remote
         */
        ScannedFarmRemote scannedFarmRemote = scanFarmRemote(world, origin);
        badFarmInfo.hasCell = scannedFarmRemote.hasPower();
        badFarmInfo.hasExporter = scannedFarmRemote.hasExport();
        badFarmInfo.hasImporter = scannedFarmRemote.hasImport();

        /**
         * Scan controller
         */
        ScannedFarmController scannedFarmController = scanFarmController(world, origin, facing);
        if (!scannedFarmController.isValid())
            badFarmInfo.badFarmBlocks.add(new BadFarmBlock(origin.up().offset(facing, -1), ModBlocks.blockFactoryController, 0));
    }
}
