package ipsis.woot.generators;


import ipsis.woot.util.WootEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileEntityGeneratorRF extends TileEntityGenerator {

    public TileEntityGeneratorRF() {
        super();
    }

    private static final int WU_TO_RF = 1;

    @Override
    public int consume(int units) {

        int rf_cost = units * WU_TO_RF;
        return energyStorage.consumeInternal(rf_cost);
    }

    private WootEnergyStorage energyStorage = new WootEnergyStorage(10000, 100);

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(energyStorage);

        return super.getCapability(capability, facing);
    }

    /**
     * NBT
     */
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestorableFromNBT(compound);
    }

    public void readRestorableFromNBT(NBTTagCompound compound) {
        energyStorage.setEnergy(compound.getInteger("energy"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setInteger("energy", energyStorage.getEnergyStored());
    }
}
