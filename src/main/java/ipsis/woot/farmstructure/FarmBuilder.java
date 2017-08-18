package ipsis.woot.farmstructure;

import ipsis.Woot;
import ipsis.woot.block.BlockMobFactoryHeart;
import ipsis.woot.power.storage.IPowerStation;
import ipsis.woot.tileentity.TileEntityMobFactoryCell;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.farming.ITickTracker;
import ipsis.woot.farmblocks.IFarmBlockConnection;
import ipsis.woot.farmblocks.IFarmBlockMaster;
import ipsis.woot.farmblocks.IFarmBlockUpgrade;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FarmBuilder implements IFarmStructure {

    private boolean farmDirty = false;
    private boolean proxyDirty = false;
    private World world;
    private BlockPos origin;

    private boolean changed = false;
    private ScannedFarm currFarm;

    public FarmBuilder() {

    }

    private void disconnectOldFarm(@Nullable ScannedFarm oldFarm, ScannedFarm newFarm) {

        if (oldFarm == null)
            return;

        Set<BlockPos> oldBlocks = new HashSet<>();
        oldBlocks.addAll(oldFarm.base.getBlocks());
        oldBlocks.add(oldFarm.controller.getBlocks());
        oldBlocks.addAll(oldFarm.upgrades.getBlocks());
        oldBlocks.addAll(oldFarm.remote.getBlocks());

        Set<BlockPos> newBlocks = new HashSet<>();
        if (newFarm != null) {
            newBlocks.addAll(newFarm.base.getBlocks());
            newBlocks.add(newFarm.controller.getBlocks());
            newBlocks.addAll(newFarm.upgrades.getBlocks());
            newBlocks.addAll(newFarm.remote.getBlocks());
        }

        oldBlocks.removeAll(newBlocks);
        for (BlockPos pos : oldBlocks) {
            if (world.isBlockLoaded(pos)) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof IFarmBlockConnection) {
                    Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_CLIENT_SYNC, this, "clearMaster", pos);
                    ((IFarmBlockConnection) te).clearMaster();
                }
            }
        }
    }

    private void connectNewFarm(ScannedFarm oldFarm, ScannedFarm newFarm) {

        IFarmBlockMaster master = (IFarmBlockMaster)world.getTileEntity(origin);
        Set<BlockPos> oldBlocks = new HashSet<>();

        if (oldFarm != null) {
            oldBlocks.addAll(oldFarm.base.getBlocks());
            oldBlocks.add(oldFarm.controller.getBlocks());
            oldBlocks.addAll(oldFarm.upgrades.getBlocks());
            oldBlocks.addAll(oldFarm.remote.getBlocks());
        }

        Set<BlockPos> newBlocks = new HashSet<>();
        newBlocks.addAll(newFarm.base.getBlocks());
        newBlocks.add(newFarm.controller.getBlocks());
        newBlocks.addAll(newFarm.upgrades.getBlocks());
        newBlocks.addAll(newFarm.remote.getBlocks());

        newBlocks.removeAll(oldBlocks);
        for (BlockPos pos : newBlocks) {
            if (world.isBlockLoaded(pos)) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof IFarmBlockUpgrade)
                    LogHelper.info("Connecting upgrade");
                if (te instanceof IFarmBlockConnection) {
                    Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_CLIENT_SYNC, this, "setMaster", pos);
                    ((IFarmBlockConnection) te).setMaster(master);
                }
            }
        }
    }

    private @Nullable ScannedFarm scanFullFarm() {

        EnumFacing facing = world.getBlockState(origin).getValue(BlockMobFactoryHeart.FACING);
        ScannedFarm scannedFarm = new ScannedFarm();
        IFarmScanner farmScanner = new FarmScanner();

        scannedFarm.base = farmScanner.scanFarmStructure(world, origin, facing);
        if (!scannedFarm.base.isValid()) {
            LogHelper.info("scanFullFarm: invalid base");
            return null;
        }

        scannedFarm.controller = farmScanner.scanFarmController(world, origin, facing);
        // Is the controller programmed
        if (!scannedFarm.controller.isValid()) {
            LogHelper.info("scanFullFarm: invalid controller");
            return null;
        }

        scannedFarm.remote = farmScanner.scanFarmRemote(world, origin);
        if (!scannedFarm.remote.isValid()) {
            LogHelper.info("scanFullFarm: invalid remote");
            return null;
        }

        scannedFarm.upgrades = farmScanner.scanFarmUpgrades(world, origin, facing, scannedFarm.base.tier);

        farmScanner.applyConfiguration(world, scannedFarm.controller, scannedFarm.upgrades, scannedFarm.base.tier);
        // Is the programmed controller valid for this factory
        if (!scannedFarm.controller.isValid()) {
            LogHelper.info("scanFullFarm: invalid controller for upgrades");
            return null;
        }

        return scannedFarm;
    }

    private void handleDirtyFarm() {

        ScannedFarm scannedFarm = scanFullFarm();

        if (currFarm == null && scannedFarm == null) {

            LogHelper.info("handleDirtyFarm: do nothing");
        } else if (currFarm == null && scannedFarm != null) {

            LogHelper.info("handleDirtyFarm: fresh farm");
            connectNewFarm(currFarm, scannedFarm);
            currFarm = scannedFarm;
            changed = true;
        } else if (currFarm != null && scannedFarm == null) {

            LogHelper.info("handleDirtyFarm: goodbye farm");
            disconnectOldFarm(currFarm, scannedFarm);
            currFarm = null;
        } else if (currFarm != null && scannedFarm != null) {

            if (!ScannedFarm.areFarmsEqual(currFarm, scannedFarm)) {
                LogHelper.info("handleDirtyFarm: changed farm");
                disconnectOldFarm(currFarm, scannedFarm);
                connectNewFarm(currFarm, scannedFarm);
                currFarm = scannedFarm;
                changed = true;
            }
        }
    }

    private void handleDirtyProxy() {

        handleDirtyFarm();
    }

    /**
     * IFarmStructure
     */

    @Override
    public void setStructureDirty() {

        farmDirty = true;
    }

    @Override
    public void setUpgradeDirty() {

        setStructureDirty();
    }

    @Override
    public IFarmStructure setWorld(@Nonnull World world) {

        this.world = world;
        return this;
    }

    @Override
    public IFarmStructure setPosition(BlockPos origin) {

        this.origin = origin;
        return this;
    }

    @Override
    public void tick(ITickTracker tickTracker) {

        if (tickTracker.hasStructureTickExpired()) {
            if (farmDirty) {
                handleDirtyFarm();
                tickTracker.resetStructureTickCount();
                farmDirty = false;
            } else if (proxyDirty) {
                handleDirtyProxy();
                tickTracker.resetStructureTickCount();
                proxyDirty = false;
            }
        }
    }

    /**
     * This should only ever be called when the farm is formed
     */
    @Override
    public IFarmSetup createSetup() {

        IFarmSetup farmSetup = new FarmSetup(world, currFarm.controller.wootMob);
        farmSetup.setFarmTier(currFarm.base.tier);
        for (ScannedFarmUpgrade.Upgrade u : currFarm.upgrades.getUpgrades())
            farmSetup.setUpgradeLevel(u.upgrade, u.upgradeTier);

        int level = farmSetup.getUpgradeLevel(EnumFarmUpgrade.LOOTING);
        if (level == 1)
            farmSetup.setEnchantKey(EnumEnchantKey.LOOTING_I);
        else if (level == 2)
            farmSetup.setEnchantKey(EnumEnchantKey.LOOTING_II);
        else if (level == 3)
            farmSetup.setEnchantKey(EnumEnchantKey.LOOTING_III);

        farmSetup.setFacing(world.getBlockState(origin).getValue(BlockMobFactoryHeart.FACING));

        farmSetup.setPowerCellBlockPos(currFarm.remote.getPowerPos());
        farmSetup.setExportBlockPos(currFarm.remote.getExportPos());
        farmSetup.setImportBlockPos(currFarm.remote.getImportPos());

        return farmSetup;
    }

    @Override
    public boolean isFormed() {

        return currFarm != null;
    }

    @Override
    public boolean hasChanged() {

        return changed;
    }

    @Override
    public void clearChanged() {

        this.changed = false;
    }
}
