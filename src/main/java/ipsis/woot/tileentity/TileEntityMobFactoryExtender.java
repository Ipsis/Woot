package ipsis.woot.tileentity;

import ipsis.woot.util.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TileEntityMobFactoryExtender extends TileEntity {

    TileEntityMobFactory master = null;

    public boolean hasMaster() { return master != null; }

    public void clearMaster() {

        if (master != null) {
            master = null;

            if (this.getWorld() != null)
                WorldHelper.updateClient(getWorld(), getPos());
        }
    }
    public void setMaster(TileEntityMobFactory master) {

        if (this.master != master) {
            this.master = master;

            if (this.getWorld() != null)
                WorldHelper.updateClient(getWorld(), getPos());
        }
    }

    TileEntityMobFactory findMaster() {

        TileEntityMobFactory tmpMaster = null;

        BlockPos blockPos = getPos().up(1);
        TileEntity te = getWorld().getTileEntity(blockPos);
        while (te != null && te instanceof TileEntityMobFactoryExtender) {
            blockPos = blockPos.up(1);
            te = getWorld().getTileEntity(blockPos);
        }

        if (te instanceof TileEntityMobFactory)
            tmpMaster = (TileEntityMobFactory)te;

        return tmpMaster;
    }

    public void blockAdded() {

        TileEntityMobFactory tmpMaster = findMaster();
        if (tmpMaster != null)
            tmpMaster.interruptProxy();
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster())
            master.interruptProxy();
    }

    public TileEntityMobFactoryExtender() {

    }

    /**
     * Client stuff
     */

    boolean isClientFormed;
    public boolean isClientFormed() { return isClientFormed; }

    /**
     * ChunkData packet handling
     * Currently calls readFromNBT on reception
     */
    @Override
    public NBTTagCompound getUpdateTag() {

        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("formed", master != null);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {

        super.handleUpdateTag(tag);
        isClientFormed = tag.getBoolean("formed");
    }

    /**
     * UpdateTileEntity packet handling
     */

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        NBTTagCompound nbtTagCompound = getUpdateTag();
        return new SPacketUpdateTileEntity(this.pos, getBlockMetadata(), nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {

        handleUpdateTag(pkt.getNbtCompound());
        WorldHelper.updateClient(getWorld(), getPos());
    }
}
