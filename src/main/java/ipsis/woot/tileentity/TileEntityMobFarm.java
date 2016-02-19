package ipsis.woot.tileentity;

import ipsis.oss.LogHelper;
import ipsis.woot.block.BlockUpgrade;
import ipsis.woot.manager.Upgrade;
import ipsis.woot.tileentity.multiblock.EnumMobFactorySize;
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
    EnumMobFactorySize factorySize;
    String mobName;

    boolean dirtyStructure;
    boolean dirtyUpgrades;

    static final int MULTIBLOCK_MIN_REFRESH_TICKS = 20;

    public TileEntityMobFarm() {

        this.facing = EnumFacing.SOUTH;
        this.dirtyStructure = true;
        this.dirtyUpgrades = false;
        this.factorySize = null;
        this.mobName = "Pig";
    }

    public void setFacing(EnumFacing facing) {

        this.facing = facing;
    }

    public EnumFacing getFacing() {

        return this.facing;
    }

    public void setMobName(String mobName) {

        // TODO changing this causes a power recalc!
        this.mobName = mobName;
    }

    public String getMobName() {

        return this.mobName;
    }

    public boolean isFormed() {

        return factorySize != null;
    }

    void checkFactory() {

        LogHelper.info("checkFactory");

        EnumMobFactorySize oldSize = factorySize;
        MobFactoryMultiblockLogic.FactorySetup factorySetup = MobFactoryMultiblockLogic.validateFactory(this);
        if (factorySetup.isValid()) {

        }
        LogHelper.info("checkFactory: " + oldSize + "->" + factorySize);

        if (isFormed() && factorySize != oldSize)
            checkUpgrades();
    }

    List<Upgrade.Type> upgradeList = new ArrayList<Upgrade.Type>();

    void checkUpgrades() {

        LogHelper.info("checkUpgrades:");
        upgradeList.clear();

        if (factorySize == EnumMobFactorySize.SIZE_ONE) {
            // No upgrades available on size one
        } else if (factorySize == EnumMobFactorySize.SIZE_TWO) {
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

        } else if (factorySize == EnumMobFactorySize.SIZE_THREE) {
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
            checkFactory();
            dirtyStructure = false;
        }

        if (!isFormed())
            return;
    }

    public void nudge() {

        LogHelper.info("nudge");
        dirtyStructure = true;
    }
}
