package ipsis.woot.manager;

import cofh.api.energy.EnergyStorage;
import ipsis.woot.tileentity.TileEntityMobFactory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class PowerManager {

    static final int MAX_RF_TICK = 32000;
    static final int RF_STORED = MAX_RF_TICK * 10;

    protected EnergyStorage energyStorage = new EnergyStorage(RF_STORED, MAX_RF_TICK);
    private TileEntityMobFactory factory;

    public PowerManager(TileEntityMobFactory factory) {

       this.factory = factory;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        energyStorage.writeToNBT(compound);
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {

        energyStorage.readFromNBT(compound);
    }

    public boolean isValidFrom(EnumFacing from, boolean isFactory) {

        if (isFactory)
            return from == EnumFacing.DOWN;

        return factory.getProxyManager().validProxy && from != EnumFacing.UP;
    }

    public int extractEnergy(int rfpertick, boolean simulate) {

        return energyStorage.extractEnergy(rfpertick, simulate);
    }


    /**
     * RF API
     */
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate, boolean isFactory) {

        if (isValidFrom(from, isFactory))
            return energyStorage.receiveEnergy(maxReceive, simulate);

        return 0;
    }

    public int getEnergyStored(EnumFacing from, boolean isFactory) {

        if (isValidFrom(from, isFactory))
            return energyStorage.getEnergyStored();

        return 0;
    }

    public int getMaxEnergyStored(EnumFacing from, boolean isFactory) {

        if (isValidFrom(from, isFactory))
            return energyStorage.getMaxEnergyStored();

        return 0;
    }

    public boolean canConnectEnergy(EnumFacing from, boolean isFactory) {

        return isValidFrom(from, isFactory);
    }

}
