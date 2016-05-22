package ipsis.woot.tileentity;

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
            IBlockState iblockstate = this.getWorld().getBlockState(pos);
            // TODO notifyBlockUpdate 4?
            worldObj.notifyBlockUpdate(pos, iblockstate, iblockstate, 4);
        }
    }

    public void setMaster(TileEntityMobFactory master) {

        if (this.master != master) {
            this.master = master;
            IBlockState iblockstate = this.getWorld().getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, iblockstate, iblockstate, 4);
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

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        NBTTagCompound nbtTagCompound = getUpdateTag();
        return new SPacketUpdateTileEntity(this.pos, getBlockMetadata(), nbtTagCompound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {

        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean("formed", master != null);
        return nbtTagCompound;
    }

    boolean isClientFormed;
    public boolean isClientFormed() { return isClientFormed; }
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {

        isClientFormed = pkt.getNbtCompound().getBoolean("formed");
        IBlockState iblockstate = this.getWorld().getBlockState(pos);
        worldObj.notifyBlockUpdate(pos, iblockstate, iblockstate, 4);
    }
}
