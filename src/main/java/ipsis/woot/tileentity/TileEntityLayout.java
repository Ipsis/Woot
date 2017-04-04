package ipsis.woot.tileentity;

import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
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

        // The server never needs the block list - it is only used for rendering
        if (this.worldObj != null && this.worldObj.isRemote) {
            layoutBlockInfoList.clear();
            MobFactoryMultiblockLogic.getFactoryLayout(tier, this.getPos(), facing, layoutBlockInfoList);

            /**
             * Now offset the layout by +2 in Y so the factory display is ABOVE the guide block
             */
            for (LayoutBlockInfo p : layoutBlockInfoList)
                p.blockPos = p.blockPos.offset(EnumFacing.UP, 2);
        }
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
        markDirty();
    }

    public void setNextTier() {

        this.tier = this.tier.getNext();
        markDirty();
        refreshLayout();
    }

    public EnumMobFactoryTier getTier() {

        return this.tier;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("facing", facing.ordinal());
        compound.setInteger("tier", tier.ordinal());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        facing = EnumFacing.getFront(compound.getInteger("facing"));
        tier = EnumMobFactoryTier.getTier(compound.getInteger("tier"));

        refreshLayout();
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        NBTTagCompound nbtTagCompound = getUpdateTag();
        return new SPacketUpdateTileEntity(this.getPos(), 0, nbtTagCompound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {

        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {

        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {

        /**
         * This defaults to the bounding box size which will be a single blocks.
         * For this block we need it to be a bit larger to accommodate the largest tier factory
         */
        BlockPos pos = getPos();
        return new AxisAlignedBB(
                pos.add(-EnumMobFactoryTier.getMaxXZOffset(), -1, -EnumMobFactoryTier.getMaxXZOffset()),
                pos.add(EnumMobFactoryTier.getMaxXZOffset(), EnumMobFactoryTier.getMaxYOffset() - 1, EnumMobFactoryTier.getMaxXZOffset()));
    }
}
