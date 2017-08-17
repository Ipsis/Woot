package ipsis.woot.tileentity;

import ipsis.woot.block.BlockMobFactoryCell;
import ipsis.woot.farmblocks.IFarmBlockConnection;
import ipsis.woot.farmblocks.IFarmBlockMaster;
import ipsis.woot.farmblocks.SimpleMasterLocator;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.power.storage.IPowerStation;
import ipsis.woot.power.storage.SinglePowerStation;
import ipsis.woot.util.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileEntityMobFactoryCell extends TileEntity implements IFarmBlockConnection {

    private IFarmBlockMaster farmBlockMaster = null;
    private IPowerStation powerStation;

    public boolean hasMaster() { return farmBlockMaster != null; }

    public TileEntityMobFactoryCell() {

        powerStation = new SinglePowerStation();
    }

    public void setTier(BlockMobFactoryCell.EnumCellTier tier) {

        powerStation.setTier(tier);
    }

    public void blockAdded() {

        IFarmBlockMaster tmpMaster = new SimpleMasterLocator().findMaster(getWorld(), getPos(), this);
        if (tmpMaster != null)
            tmpMaster.interruptFarmStructure();
    }

    public IPowerStation getPowerStation() {
        return powerStation;
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster())
            farmBlockMaster.interruptFarmStructure();
    }

    /**
     * Client stuff
     */

    boolean isClientFormed;
    public boolean isClientFormed() { return isClientFormed; }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        powerStation.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        powerStation.writeToNBT(compound);
        return compound;
    }

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
            WorldHelper.updateNeighbors(getWorld(), getPos(), ModBlocks.blockCell);
        }
    }

    public void setMaster(IFarmBlockMaster master) {

        if (farmBlockMaster != master) {
            farmBlockMaster = master;

            WorldHelper.updateClient(getWorld(), getPos());
            WorldHelper.updateNeighbors(getWorld(), getPos(), ModBlocks.blockCell);
        }
    }

    public BlockPos getStructurePos() {
        return getPos();
    }

    /**
     * Power connections
     */
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY) {
            if (hasMaster())
                return true;
            else
                return false;
        }

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY && hasMaster())
            return (T)powerStation.getEnergyStorage();

        return super.getCapability(capability, facing);
    }
}
