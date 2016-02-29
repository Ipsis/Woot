package ipsis.woot.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TileEntityMobFactoryStructure extends TileEntity  {

    TileEntityMobFactory master = null;

    public boolean hasMaster() { return master != null; }
    public void clearMaster() {

        if (master != null) {
            master = null;
            worldObj.markBlockForUpdate(pos);
        }
    }

    public void setMaster(TileEntityMobFactory master) {

        if (this.master != master) {
            this.master = master;
            worldObj.markBlockForUpdate(pos);
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

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("formed", master != null);
        return new S35PacketUpdateTileEntity(this.pos, getBlockMetadata(), nbtTagCompound);
    }

    boolean isClientFormed;
    public boolean isClientFormed() { return isClientFormed; }
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.readFromNBT(pkt.getNbtCompound());
        isClientFormed = pkt.getNbtCompound().getBoolean("formed");
        worldObj.markBlockForUpdate(pos);
    }
}
