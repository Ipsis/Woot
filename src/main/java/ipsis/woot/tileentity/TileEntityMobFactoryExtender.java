package ipsis.woot.tileentity;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.tileentity.ng.farmblocks.ExtenderMasterLocator;
import ipsis.woot.tileentity.ng.farmblocks.IFarmBlockConnection;
import ipsis.woot.tileentity.ng.farmblocks.IFarmBlockMaster;
import ipsis.woot.tileentity.ng.farmblocks.IFarmBlockProxy;
import ipsis.woot.util.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TileEntityMobFactoryExtender extends TileEntity implements IFarmBlockConnection, IFarmBlockProxy{

    private IFarmBlockMaster farmBlockMaster = null;

    public boolean hasMaster() { return farmBlockMaster != null; }

    public void blockAdded() {

        IFarmBlockMaster tmpMaster = new ExtenderMasterLocator().findMaster(getWorld(), getPos(), this);
        if (tmpMaster != null)
            tmpMaster.interruptFarmProxy();
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster())
            farmBlockMaster.interruptFarmProxy();
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
        nbtTagCompound.setBoolean("formed", farmBlockMaster != null);
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

    /**
     * IFarmBlockConnection
     */
    public void clearMaster() {

        if (farmBlockMaster != null) {
            farmBlockMaster = null;

            WorldHelper.updateClient(getWorld(), getPos());
            WorldHelper.updateNeighbors(getWorld(), getPos(), ModBlocks.blockExtender);
        }
    }

    public void setMaster(IFarmBlockMaster master) {

        if (farmBlockMaster != master) {
            farmBlockMaster = master;

            WorldHelper.updateClient(getWorld(), getPos());
            WorldHelper.updateNeighbors(getWorld(), getPos(), ModBlocks.blockExtender);
        }
    }

    public BlockPos getStructurePos() {
        return getPos();
    }

    /**
     * IFarmBlockProxy
     */
    public boolean isProxy() {

        return true;
    }

    public boolean isExtender() {

        return false;
    }
}
