package ipsis.woot.tileentity;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.util.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileEntityMobFactoryProxy extends TileEntity  {

    TileEntityMobFactory master = null;

    public boolean hasMaster() { return master != null; }

    public void clearMaster() {

        if (master != null) {
            master = null;

            if (this.getWorld() != null) {
                WorldHelper.updateClient(getWorld(), getPos());
                WorldHelper.updateNeighbors(getWorld(), getPos(), ModBlocks.blockProxy);
            }
        }
    }

    public void setMaster(TileEntityMobFactory master) {

        if (this.master != master) {
            this.master = master;

            if (this.getWorld() != null) {
                WorldHelper.updateClient(getWorld(), getPos());
                WorldHelper.updateNeighbors(getWorld(), getPos(), ModBlocks.blockProxy);
            }
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

    public TileEntityMobFactoryProxy() {

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

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

        return capability == CapabilityEnergy.ENERGY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY) {

            if (hasMaster() && master.isFormed()) {
                IEnergyStorage energyStorage = master.getEnergyManager();
                return (T) energyStorage;
            }
        }

        return super.getCapability(capability, facing);
    }
}
