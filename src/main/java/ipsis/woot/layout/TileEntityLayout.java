package ipsis.woot.layout;

import ipsis.Woot;
import ipsis.woot.factory.structure.pattern.AbsolutePattern;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.helpers.LogHelper;
import ipsis.woot.util.helpers.WorldHelper;
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

public class TileEntityLayout extends TileEntity {

    private EnumFacing facing = EnumFacing.SOUTH;
    private FactoryTier factoryTier = FactoryTier.TIER_1;
    private int currLevel = -1;
    private AbsolutePattern absolutePattern;

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
        markDirty();
        refreshLayout();
    }

    public void refreshLayout() {

        // The server never needs the block list - it is only used for rendering
        if (WorldHelper.isServerWorld(getWorld()))
            return;

        // Now offset the layout by 2 so the factory display is BELOW the guide block
        BlockPos pos = getPos().down(2);
        absolutePattern = Woot.PATTERN_REPOSITORY.createAbsolutePattern(getWorld(), factoryTier, pos, facing);
    }

    public FactoryTier getFactoryTier() { return this.factoryTier; }
    public FactoryTier setNextFactoryTier() {
        factoryTier = factoryTier.getNext();
        refreshLayout();
        return factoryTier;
    }

    public int getCurrLevel() { return this.currLevel; }
    public int setNextFactoryLevel() {
        currLevel++;
        if (currLevel >= Woot.PATTERN_REPOSITORY.getPatternHeight(factoryTier))
            currLevel = -1;
        refreshLayout();
        return currLevel;
    }

    public AbsolutePattern getAbsolutePattern() { return this.absolutePattern; }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTagCompound = getUpdateTag();
        return new SPacketUpdateTileEntity(getPos(), 0, nbtTagCompound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    /**
     * NBT
     */

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("facing", facing.ordinal());
        compound.setInteger("tier", factoryTier.ordinal());
        compound.setInteger("level", currLevel);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        facing = EnumFacing.byIndex(compound.getInteger("facing"));
        factoryTier = FactoryTier.byIndex(compound.getInteger("tier"));
        currLevel = compound.getInteger("level");
        refreshLayout();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {

        /**
         * This defaults to the bounding box size which will be a single blocks.
         * For this block we need it to be a bit larger to accommodate the largest tier factory
         */
        BlockPos pos = getPos();
        return new AxisAlignedBB(
                pos.add(-Woot.PATTERN_REPOSITORY.getMaxXZOffset(), -1, -Woot.PATTERN_REPOSITORY.getMaxXZOffset()),
                pos.add(Woot.PATTERN_REPOSITORY.getMaxXZOffset(), Woot.PATTERN_REPOSITORY.getMaxYOffset() - 1, Woot.PATTERN_REPOSITORY.getMaxXZOffset()));
    }
}
