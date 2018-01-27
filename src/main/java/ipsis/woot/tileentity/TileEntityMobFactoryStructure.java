package ipsis.woot.tileentity;

import ipsis.woot.farmblocks.*;
import ipsis.woot.util.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TileEntityMobFactoryStructure extends TileEntity implements IFarmBlockConnection, IFarmBlockStructure {

    private IFarmBlockMaster farmBlockMaster = null;

    @Override
    public boolean hasMaster() {

        return farmBlockMaster != null;
    }

    public void blockAdded() {

        IFarmBlockMaster tmpMaster = new StructureMasterLocator().findMaster(getWorld(), getPos(), this);
        if (tmpMaster != null)
            tmpMaster.interruptFarmStructure();
    }

    @Override
    public void validate() {

        super.validate();
        if (!getWorld().isRemote) {
            // This MUST use the non-te version
            IFarmBlockMaster tmpMaster = new StructureBlockMasterLocator().findMaster(getWorld(), getPos());
            if (tmpMaster != null)
                tmpMaster.interruptFarmStructure();
        }
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster())
            farmBlockMaster.interruptFarmStructure();
    }

    private boolean isClientFormed;
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
        }
    }

    public void setMaster(IFarmBlockMaster master) {

        if (farmBlockMaster != master) {
            farmBlockMaster = master;
            WorldHelper.updateClient(getWorld(), getPos());
        }
    }

    public BlockPos getStructurePos() {
        return getPos();
    }

    /**
     * IFarmBlockStructure
     */

}
