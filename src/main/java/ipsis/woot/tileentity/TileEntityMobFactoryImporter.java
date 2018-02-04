package ipsis.woot.tileentity;

import ipsis.woot.farmblocks.*;
import ipsis.woot.util.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityMobFactoryImporter extends TileEntity implements IFactoryGlueProvider {

    private IFactoryGlue iFactoryGlue;

    public TileEntityMobFactoryImporter() {

        iFactoryGlue = new FactoryGlue(IFactoryGlue.FactoryBlockType.IMPORTER, new SimpleMasterLocator(), this, this);
    }

    public void onBlockAdded() {

        iFactoryGlue.onHello(getWorld(), getPos());
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
        nbtTagCompound.setBoolean("formed", iFactoryGlue.hasMaster());
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

    @Nonnull
    @Override
    public IFactoryGlue getIFactoryGlue() {
        return iFactoryGlue;
    }

}
