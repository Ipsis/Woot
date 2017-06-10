package ipsis.woot.tileentity.ng;

import ipsis.woot.block.BlockMobFactory;
import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryModule;
import ipsis.woot.tileentity.ng.farmblocks.*;
import ipsis.woot.tileentity.ng.farmstructure.*;
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

    /**
     * A factory is valid if it has a valid structure AND a programmed controller
     * A farm setup depends on the tier, controller and upgrades and if any of these
     * change the the structure is deemed to have changed. This in turn will mean that a
     * new setup should be request by the caller
     *
     * if the structure is dirty then
     * - scan the farm structure
     * - scan the controller
     * - scan the upgrades
     * - scan the proxy
     *
     * if the upgrade is dirty then
     * - scan the upgrades
     *
     * if the proxy is dirty then
     * - scan the proxy
     *
     *
     */
    private void handleDirtyStructure() {

        scannedFarm = new FarmStructureBlocks();
        scanFarm(scannedFarm);
        scanController(scannedFarm);
        scanUpgrades(scannedFarm);
        scanProxy(scannedFarm);

        if (scannedFarm.isValid()) {
            if (currFarm.areFarmBlocksEqual(scannedFarm)) {
                LogHelper.info("handleDirtyStructure: same farm");

            } else {
                LogHelper.info("handleDirtyStructure: new farm");
                disconnectPartialFarm(currFarm, scannedFarm);
                connectFarmBlocks(scannedFarm.farmBlocks);
                currFarm = scannedFarm;
                changed = true;
            }
        } else {
            // disconnect all the blocks in the current farm
            disconnectFullFarm(currFarm);
            currFarm = new FarmStructureBlocks();
            LogHelper.info("handleDirtyStructure: invalid farm");
        }

        scannedFarm = null;
        structureDirty = false;
    }

    private void handleDirtyUpgrade() {

    }

    private void handleDirtyProxy() {


    }

    @Override
    public void tick(ITickTracker tickTracker) {

        if (tickTracker.hasStructureTickExpired()) {
            if (structureDirty) {
                handleDirtyStructure();
                tickTracker.resetStructureTickCount();
            } else if (upgradeDirty) {
                handleDirtyUpgrade();
                tickTracker.resetStructureTickCount();
            } else if (proxyDirty) {
                handleDirtyProxy();
                tickTracker.resetStructureTickCount();
            }
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

    private void scanUpgrades(FarmStructureBlocks farm) {

        BlockPos[] positions;
        Class upgradeClass;

        if (farm.farmTier == EnumMobFactoryTier.TIER_ONE) {
            positions = new BlockPos[]{
                    new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0)
            };
            upgradeClass = UpgradeTotemTierOne.class;
        } else if (farm.farmTier == EnumMobFactoryTier.TIER_TWO) {
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

    private void scanProxy(FarmStructureBlocks farm) {

    }

    private void scanFarm(FarmStructureBlocks farm) {

        if (scanFarmTier(farm.farmBlocks, EnumMobFactoryTier.TIER_FOUR))
            farm.farmTier = EnumMobFactoryTier.TIER_FOUR;
        else if (scanFarmTier(farm.farmBlocks, EnumMobFactoryTier.TIER_THREE))
            farm.farmTier = EnumMobFactoryTier.TIER_THREE;
        else if (scanFarmTier(farm.farmBlocks, EnumMobFactoryTier.TIER_TWO))
            farm.farmTier = EnumMobFactoryTier.TIER_TWO;
        else if (scanFarmTier(farm.farmBlocks, EnumMobFactoryTier.TIER_ONE))
            farm.farmTier = EnumMobFactoryTier.TIER_ONE;
        else
            farm.farmTier = null;
    }

    private void scanController(FarmStructureBlocks farmStructureBlocks) {

        BlockPos pos = origin.up();
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IFarmBlockController && ((IFarmBlockController) te).isProgrammed())
            farmStructureBlocks.controllerBlock = pos;
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

    private void connectFarmBlocks(List<BlockPos> farmBlocks) {

        IFarmBlockMaster master = (IFarmBlockMaster)world.getTileEntity(origin);

        for (BlockPos p : farmBlocks) {
            TileEntity te = world.getTileEntity(p);
            if (te instanceof  IFarmBlockConnection)
                ((IFarmBlockConnection)te).setMaster(master);
        }
    }

    private void disconnectFullFarm(FarmStructureBlocks farm) {

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

    private void disconnectPartialFarm(FarmStructureBlocks curr, FarmStructureBlocks scan) {

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

        // These are VALID blocks in the structure
        List<BlockPos> farmBlocks = new ArrayList<>();
        List<BlockPos> upgradeBlocks = new ArrayList<>();
        List<BlockPos> proxyBlocks = new ArrayList<>();
        BlockPos controllerBlock = null;
        EnumMobFactoryTier farmTier = null;

        boolean areFarmBlocksEqual(FarmStructureBlocks b) {

            if (this.farmTier != b.farmTier)
                return false;

            if (!this.controllerBlock.equals(b.controllerBlock))
                return false;

            if (!this.farmBlocks.containsAll(b.farmBlocks))
                return false;

            if (!b.farmBlocks.containsAll(this.farmBlocks))
                return false;

            if (!this.upgradeBlocks.containsAll(b.upgradeBlocks))
                return false;

            if (!b.upgradeBlocks.containsAll(this.upgradeBlocks))
                return false;

            /**
             * Proxy blocks do not count as they do not contribute to the farm setup
             */

            return true;
        }

        boolean isValid() {

            return farmTier != null && controllerBlock != null;
        }
    }
}
