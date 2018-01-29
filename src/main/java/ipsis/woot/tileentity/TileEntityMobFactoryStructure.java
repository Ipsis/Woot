package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.farmblocks.*;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
public class TileEntityMobFactoryStructure extends TileEntity implements IFarmBlockStructure, IFactoryGlueProvider {

    private IFactoryGlue iFactoryGlue;

    public TileEntityMobFactoryStructure() {

        iFactoryGlue = new FactoryGlue(IFactoryGlue.FactoryBlockType.STRUCTURE, new StructureMasterLocator(), this, this);
    }

    public void onBlockAdded() {

        iFactoryGlue.onHello(getWorld(), getPos());
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

        super.invalidate();
        iFactoryGlue.onGoodbye();
    }

    @Override
    public void onChunkUnload() {

        super.onChunkUnload();
        iFactoryGlue.onGoodbye();
    }

    private boolean isClientFormed;
    public boolean isClientFormed() { return isClientFormed; }

    /**
     * ChunkData packet handling
     * Currently calls readFromNBT on reception
     */
    @Override
    public NBTTagCompound getUpdateTag() {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_CLIENT_SYNC, "TileEntityMobFactoryStructure:", "getUpdateTag");
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("formed", iFactoryGlue.hasMaster());
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {

        super.handleUpdateTag(tag);
        isClientFormed = tag.getBoolean("formed");
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_CLIENT_SYNC, "TileEntityMobFactoryStructure:", "handleUpdateTag formed:" + isClientFormed);
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

    @Nonnull
    @Override
    public IFactoryGlue getIFactoryGlue() {
        return iFactoryGlue;
    }
}
