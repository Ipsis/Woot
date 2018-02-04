package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.multiblock.EnumMobFactoryTier;
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

    List<ILayoutBlockInfo> layoutBlockInfoList;
    EnumFacing facing;
    EnumMobFactoryTier tier;
    int currLevel = -1;

    public TileEntityLayout() {

        layoutBlockInfoList = new ArrayList<>();
        facing = EnumFacing.SOUTH;
        tier = EnumMobFactoryTier.TIER_ONE;
    }

    public List<ILayoutBlockInfo> getLayoutBlockInfoList() {

        return layoutBlockInfoList;
    }

    public void refreshLayout() {

        // The server never needs the block list - it is only used for rendering
        if (this.world != null && this.world.isRemote) {
            layoutBlockInfoList.clear();
            Woot.factoryPatternRepository.getFactoryLayout(tier, this.getPos(), facing, layoutBlockInfoList);

            /**
             * Add the heart and controller
             */
            layoutBlockInfoList.add(new HeartLayoutBlockInfo(this.getPos()));
            layoutBlockInfoList.add(new ControllerLayoutBlockInfo(this.getPos().up().offset(facing, -1)));

            /**
             * Now offset the layout by +2 in Y so the factory display is ABOVE the guide block
             */
            for (ILayoutBlockInfo p : layoutBlockInfoList)
                p.offsetY(2);
        }
    }

    public void buildFactory() {

        if (this.getWorld() != null && !this.getWorld().isRemote) {
            layoutBlockInfoList.clear();
            Woot.factoryPatternRepository.getFactoryLayout(tier, this.getPos(), facing, layoutBlockInfoList);

            /**
             * Now offset the layout by +2 in Y so the factory display is ABOVE the guide block
             */
            for (ILayoutBlockInfo p : layoutBlockInfoList)
                p.offsetY(2);

            for (ILayoutBlockInfo pos : layoutBlockInfoList)
                if (pos instanceof StructureLayoutBlockInfo)
                    getWorld().setBlockState(pos.getPos(),
                            ModBlocks.blockStructure.getStateFromMeta(((StructureLayoutBlockInfo) pos).module.getMetadata()));
        }

    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
        markDirty();
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setNextTier() {

        this.tier = this.tier.getNext();
        this.currLevel = -1; /* show all */
        markDirty();
        refreshLayout();
    }

    public EnumMobFactoryTier getTier() {

        return this.tier;
    }

    public int getCurrLevel() {

        return currLevel;
    }

    public void setNextLevel() {


        currLevel++;
        if (currLevel >= Woot.factoryPatternRepository.getTierHeight(tier))
            currLevel = -1; /* show all */
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("facing", facing.ordinal());
        compound.setInteger("tier", tier.ordinal());
        compound.setInteger("level", currLevel);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        facing = EnumFacing.getFront(compound.getInteger("facing"));
        tier = EnumMobFactoryTier.getTier(compound.getInteger("tier"));
        currLevel = compound.getInteger("level");

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
                pos.add(-Woot.factoryPatternRepository.getMaxXZOffset(), -1, -Woot.factoryPatternRepository.getMaxXZOffset()),
                pos.add(Woot.factoryPatternRepository.getMaxXZOffset(), Woot.factoryPatternRepository.getMaxYOffset() - 1, Woot.factoryPatternRepository.getMaxXZOffset()));
    }
}
