package ipsis.woot.tileentity;

import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class TileEntityLayout extends TileEntity {

    List<LayoutBlockInfo> layoutBlockInfoList;
    EnumFacing facing;
    EnumMobFactoryTier tier;

    public TileEntityLayout() {

        layoutBlockInfoList = new ArrayList<LayoutBlockInfo>();
        facing = EnumFacing.SOUTH;
        tier = EnumMobFactoryTier.TIER_ONE;
    }

    public List<LayoutBlockInfo> getLayoutBlockInfoList() {

        return layoutBlockInfoList;
    }

    public void refreshLayout() {

        MobFactoryMultiblockLogic.getFactoryLayout(tier, this.getPos(), facing, layoutBlockInfoList);
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    public void setNextTier() {

        this.tier = this.tier.getNext();
        refreshLayout();
    }

    public EnumMobFactoryTier getTier() {

        return this.tier;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("facing", facing.ordinal());
        compound.setInteger("tier", tier.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        facing = EnumFacing.getFront(compound.getInteger("facing"));
        tier = EnumMobFactoryTier.getTier(compound.getInteger("tier"));

        refreshLayout();
    }
}
