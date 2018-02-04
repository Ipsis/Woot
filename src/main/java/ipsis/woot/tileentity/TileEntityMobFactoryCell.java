package ipsis.woot.tileentity;

import ipsis.woot.block.BlockMobFactoryCell;
import ipsis.woot.farmblocks.*;
import ipsis.woot.power.storage.IPowerStation;
import ipsis.woot.power.storage.SinglePowerStation;
import ipsis.woot.util.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityMobFactoryCell extends TileEntity implements IFactoryGlueProvider {

    private IFactoryGlue iFactoryGlue;
    private IPowerStation powerStation;

    public TileEntityMobFactoryCell() {

        iFactoryGlue = new FactoryGlue(IFactoryGlue.FactoryBlockType.CELL, new SimpleMasterLocator(), this, this);
        powerStation = new SinglePowerStation();
    }

    public void setTier(BlockMobFactoryCell.EnumCellTier tier) {

        powerStation.setTier(tier);
    }

    public void onBlockAdded() {

        iFactoryGlue.onHello(getWorld(), getPos());
    }

    public IPowerStation getPowerStation() {
        return powerStation;
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


    /**
     * Power connections
     */
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY)
            return iFactoryGlue.hasMaster();

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY && iFactoryGlue.hasMaster())
            return (T)powerStation.getEnergyStorage();

        return super.getCapability(capability, facing);
    }

    @Nonnull
    @Override
    public IFactoryGlue getIFactoryGlue() {
        return iFactoryGlue;
    }
}
