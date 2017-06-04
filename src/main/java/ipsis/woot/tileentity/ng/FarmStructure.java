package ipsis.woot.tileentity.ng;

import ipsis.woot.block.BlockMobFactory;
import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryModule;
import ipsis.woot.tileentity.ng.farmblocks.*;
import ipsis.woot.tileentity.ng.upgrades.*;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class FarmStructure implements IFarmStructure {

    private boolean structureDirty = false;
    private boolean upgradeDirty = false;
    private boolean proxyDirty = false;
    private World world;
    private BlockPos origin;

    private boolean changed = false;
    private FarmStructureBlocks currFarm;
    private FarmStructureBlocks scannedFarm;

    public FarmStructure() {

        currFarm = new FarmStructureBlocks();
    }

    @Override
    public void setStructureDirty() {

        structureDirty = true;
    }

    @Override
    public void setUpgradeDirty() {

        upgradeDirty = true;
    }

    @Override
    public void setProxyDirty() {

        proxyDirty = true;
    }

    @Override
    public IFarmStructure setWorld(@Nonnull World world) {

        this.world = world;
        return this;
    }

    public IFarmStructure setPosition(BlockPos origin) {

        this.origin = origin;
        return this;
    }

    @Override
    public void tick(ITickTracker tickTracker) {

        if (tickTracker.hasStructureTickExpired() && structureDirty) {

            scannedFarm = new FarmStructureBlocks();
            scanFarm(scannedFarm);
            scanController(scannedFarm);
            if (scannedFarm.farmTier == null && scannedFarm.controllerBlock == null) {
                // no more farm or unprogrammed controller
                invalidateFullFarm(currFarm);
                currFarm = new FarmStructureBlocks();
                LogHelper.info("FarmStructure:tick invalid farm");
            } else {
                if (!currFarm.areFarmsEqual(scannedFarm)) {
                    // tier or upgrade has changed
                    invalidatePartialFarm(currFarm, scannedFarm);
                    currFarm = scannedFarm;
                    changed = true;
                    LogHelper.info("FarmStructure:tick new farm");
                }
            }

            scannedFarm = null;
            structureDirty = false;
            tickTracker.resetStructureTickCount();
        }
    }

    @Override
    public IFarmSetup createSetup() {

        IFarmBlockController controller = (IFarmBlockController)world.getTileEntity(currFarm.controllerBlock);
        FarmSetup farmSetup = new FarmSetup(controller.getWootMob());
        return farmSetup;
    }

    @Override
    public boolean isFormed() {
        return !currFarm.farmBlocks.isEmpty() && currFarm.farmTier != null;
    }

    @Override
    public boolean hasChanged() {
        return changed;
    }

    @Override
    public void clearChanged() { changed = false;

    }

    @Override
    public @Nonnull List<IFluidHandler> getConnectedTanks() {

        List<IFluidHandler> tankList = new ArrayList<>();
        return tankList;
    }

    @Override
    public @Nonnull List<IItemHandler> getConnectedChests() {

        List<IItemHandler> chestList = new ArrayList<>();
        return chestList;
    }

    private void scanUpgrades() {

        BlockPos[] positions;
        Class upgradeClass;

        if (currFarm.farmTier == EnumMobFactoryTier.TIER_ONE) {
            positions = new BlockPos[]{
                    new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0)
            };
            upgradeClass = UpgradeTotemTierOne.class;
        } else if (currFarm.farmTier == EnumMobFactoryTier.TIER_TWO) {
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

        EnumFacing facing = world.getBlockState(origin).getValue(BlockMobFactory.FACING);
        for (BlockPos p : positions) {
            BlockPos offset = BlockPosHelper.rotateFromSouth(p, facing.getOpposite());
            BlockPos p2 = origin.add(offset.getX(), offset.getY(), offset.getZ());

            AbstractUpgradeTotem upgradeTotem = AbstractUpgradeTotemBuilder.build(upgradeClass, world, origin);
            if (upgradeTotem != null) {
                // TODO add to the upgrade totem list
            }
        }

    }

    private void scanProxy() {

    }

    private void scanFarm(FarmStructureBlocks scannedFarm) {

        if (scanFarmTier(scannedFarm.farmBlocks, EnumMobFactoryTier.TIER_FOUR))
            scannedFarm.farmTier = EnumMobFactoryTier.TIER_FOUR;
        else if (scanFarmTier(scannedFarm.farmBlocks, EnumMobFactoryTier.TIER_THREE))
            scannedFarm.farmTier = EnumMobFactoryTier.TIER_THREE;
        else if (scanFarmTier(scannedFarm.farmBlocks, EnumMobFactoryTier.TIER_TWO))
            scannedFarm.farmTier = EnumMobFactoryTier.TIER_TWO;
        else if (scanFarmTier(scannedFarm.farmBlocks, EnumMobFactoryTier.TIER_ONE))
            scannedFarm.farmTier = EnumMobFactoryTier.TIER_ONE;
        else
            scannedFarm.farmTier = null;
    }

    private void scanController(FarmStructureBlocks scannedFarm) {

        BlockPos pos = origin.up();
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IFarmBlockController && ((IFarmBlockController) te).isProgrammed())
            scannedFarm.controllerBlock = pos;
    }

    private boolean scanFarmTier(List<BlockPos> scannedFarmBlocks, EnumMobFactoryTier tier) {

        BlockPos patternOrigin = origin;
        EnumFacing facing = world.getBlockState(origin).getValue(BlockMobFactory.FACING);
        for (MobFactoryModule module : tier.getStructureModules()) {

            BlockPos p = BlockPosHelper.rotateFromSouth(module.getOffset(), facing.getOpposite());
            p = patternOrigin.add(p);

            if (!world.isBlockLoaded(p))
                return false;

            IBlockState blockState = world.getBlockState(p);
            Block block = blockState.getBlock();

            if (!(block instanceof BlockMobFactoryStructure))
                return false;

            if (!(((BlockMobFactoryStructure)block).getModuleTypeFromState(blockState) == module.getModuleType()))
                return false;

            scannedFarmBlocks.add(p);
        }

        LogHelper.info("scanFarmTier: farm is correct for " + tier);
        return true;
    }

    private void invalidateFullFarm(FarmStructureBlocks farm) {

        List<BlockPos> blocks = new ArrayList<>();
        blocks.addAll(farm.farmBlocks);
        blocks.addAll(farm.upgradeBlocks);
        blocks.addAll(farm.proxyBlocks);

        for (BlockPos p : blocks) {
            if (world.isBlockLoaded(p)) {
                TileEntity te = world.getTileEntity(p);
                if (te instanceof IFarmBlockConnection)
                    ((IFarmBlockConnection) te).clearMaster();
            }
        }
    }

    private void invalidatePartialFarm(FarmStructureBlocks curr, FarmStructureBlocks scan) {

        List<BlockPos> currBlocks = new ArrayList<>();
        currBlocks.addAll(curr.farmBlocks);
        currBlocks.addAll(curr.upgradeBlocks);

        List<BlockPos> scanBlocks = new ArrayList<>();
        scanBlocks.addAll(scan.farmBlocks);
        scanBlocks.addAll(scan.upgradeBlocks);

        for (BlockPos p : currBlocks) {
            if (!scanBlocks.contains(p)) {
                if (world.isBlockLoaded(p)) {
                    TileEntity te = world.getTileEntity(p);
                    if (te instanceof IFarmBlockConnection)
                        ((IFarmBlockConnection) te).clearMaster();
                }
            }
        }
    }

    private class FarmStructureBlocks {

        List<BlockPos> farmBlocks = new ArrayList<>();
        List<BlockPos> upgradeBlocks = new ArrayList<>();
        List<BlockPos> proxyBlocks = new ArrayList<>();
        BlockPos controllerBlock = null;
        EnumMobFactoryTier farmTier = null;

        boolean areFarmsEqual(FarmStructureBlocks b) {

            return true;
        }
    }
}
