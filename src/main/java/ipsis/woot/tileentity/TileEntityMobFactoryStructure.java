package ipsis.woot.tileentity;

import ipsis.woot.util.WorldHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TileEntityMobFactoryStructure extends TileEntity  {

    TileEntityMobFactory master = null;

    public boolean hasMaster() { return master != null; }
    public void clearMaster() {

        if (master != null) {
            master = null;
            WorldHelper.updateClient(getWorld(), getPos());
        }
    }

    public void setMaster(TileEntityMobFactory master) {

        if (this.master != master) {
            this.master = master;
            WorldHelper.updateClient(getWorld(), getPos());
        }
    }

    TileEntityMobFactory findMaster() {

        List<TileEntityMobFactoryStructure> connectedTEs = new ArrayList<TileEntityMobFactoryStructure>();
        Stack<TileEntityMobFactoryStructure> traversingTEs = new Stack<TileEntityMobFactoryStructure>();

        TileEntityMobFactory tmpMaster = null;
        boolean masterFound = false;

        traversingTEs.add(this);
        while (!masterFound && !traversingTEs.isEmpty()) {
            TileEntityMobFactoryStructure currTE = traversingTEs.pop();

            connectedTEs.add(currTE);
            for (EnumFacing f : EnumFacing.values()) {
                TileEntity te = worldObj.getTileEntity(currTE.getPos().offset(f));
                if (te instanceof TileEntityMobFactoryStructure && !connectedTEs.contains(te)) {
                    traversingTEs.add((TileEntityMobFactoryStructure)te);
                } else if (te instanceof TileEntityMobFactory) {
                    masterFound = true;
                    tmpMaster = (TileEntityMobFactory)te;
                }
            }
        }

        return tmpMaster;
    }

    public void blockAdded() {

        TileEntityMobFactory tmpMaster = findMaster();
        if (tmpMaster != null)
            tmpMaster.interruptStructure();
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster()) {
            master.interruptStructure();
        }
    }

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
