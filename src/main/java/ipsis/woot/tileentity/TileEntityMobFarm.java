package ipsis.woot.tileentity;

import ipsis.oss.LogHelper;
import ipsis.woot.block.BlockUpgrade;
import ipsis.woot.manager.Upgrade;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMobFarm extends TileEntity implements ITickable {

    EnumFacing facing;
    EnumMobFactoryTier factoryTier;
    String mobName;

    boolean dirtyStructure;
    boolean dirtyUpgrade;
    List<BlockPos> structureBlockList = new ArrayList<BlockPos>();
    List<BlockPos> upgradeBlockList = new ArrayList<BlockPos>();
    List<Upgrade.Type> upgradeList = new ArrayList<Upgrade.Type>();

    static final int MULTIBLOCK_MIN_REFRESH_TICKS = 20;

    public TileEntityMobFarm() {

        this.facing = EnumFacing.SOUTH;
        this.dirtyStructure = true;
        this.dirtyUpgrade = false;
        this.factoryTier = null;
        this.mobName = "Pig";
    }

    public void setFacing(EnumFacing facing) {

        this.facing = facing;
    }

    public EnumFacing getFacing() {

        return this.facing;
    }

    public boolean isFormed() {

        return factoryTier != null && mobName.equals("");
    }

    void updateStructureBlocks(boolean connected) {

        for (BlockPos p : structureBlockList) {
            if (worldObj.isBlockLoaded(p)) {
                TileEntity te = worldObj.getTileEntity(p);
                if (te instanceof TileEntityMobFactoryStructure) {
                    if (connected)
                        ((TileEntityMobFactoryStructure) te).setMaster(this);
                    else
                        ((TileEntityMobFactoryStructure) te).clearMaster();
                }
            }
        }
    }

    void disconnectUpgradeBlocks(boolean connected) {

        for (BlockPos p : upgradeBlockList) {
            if (worldObj.isBlockLoaded(p)) {
                TileEntity te = worldObj.getTileEntity(p);
                if (te instanceof TileEntityMobFactoryUpgrade) {
                    if (connected)
                        ((TileEntityMobFactoryUpgrade) te).setMaster(this);
                    else
                        ((TileEntityMobFactoryUpgrade) te).clearMaster();
                }
            }
        }
    }

    void onStructureCheck() {

        LogHelper.info("onStructureChanged");

        EnumMobFactoryTier oldFactoryTier = factoryTier;
        MobFactoryMultiblockLogic.FactorySetup factorySetup = MobFactoryMultiblockLogic.validateFactory(this);

        if (factorySetup.getSize() == null) {
            LogHelper.info("onStructureChanged: new size is null");
            updateStructureBlocks(false);
            disconnectUpgradeBlocks(false);
            factoryTier = factorySetup.getSize();
            mobName = "";
            return;
        }

        if (oldFactoryTier != factoryTier) {
            LogHelper.info("onStructureChanged: new size != old size");
            updateStructureBlocks(false);
        }

        mobName = factorySetup.getMobName();
        structureBlockList = factorySetup.getBlockPosList();
        updateStructureBlocks(true);

        onUpgradeCheck();
    }

    void onUpgradeCheck() {

        LogHelper.info("onUpgradeChanged");
    }

    void checkUpgrades() {

        LogHelper.info("checkUpgrades:");
        upgradeList.clear();

        if (factoryTier == EnumMobFactoryTier.TIER_ONE) {
            // No upgrades available on size one
        } else if (factoryTier == EnumMobFactoryTier.TIER_TWO) {
            // One on left up to level 2
            // One on right up to level 2

            BlockPos offset = new BlockPos(1, 0, 0);
            offset = BlockPosHelper.rotateToSouth(offset, facing.getOpposite());
            BlockPos b = getPos().add(offset.getX(), offset.getY(), offset.getZ());
            if (worldObj.isBlockLoaded(b)) {
                IBlockState iBlockState = worldObj.getBlockState(b);
                Block block = iBlockState.getBlock();
                if (block instanceof BlockUpgrade) {
                    // if is a level 1 upgrade
                }
            }

        } else if (factoryTier == EnumMobFactoryTier.TIER_THREE) {
        }

        LogHelper.info("checkUpgrades: " + upgradeList);
    }

    void handleDirtyStructure() {


    }

    @Override
    public void update() {

        if (worldObj.isRemote)
            return;

        if (dirtyStructure && worldObj.getWorldTime() % MULTIBLOCK_MIN_REFRESH_TICKS == 0) {
            onStructureCheck();
            dirtyStructure = false;
        }

        if (!isFormed())
            return;
    }

    public void interruptStructure() {

        LogHelper.info("interruptStructure");
        dirtyStructure = true;
    }

    public void interruptUpgrade() {

        LogHelper.info("interruptUpgrade");
        dirtyUpgrade = true;
    }
}
