package ipsis.woot.manager;

import ipsis.woot.tileentity.TileEntityMobFactory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyManager extends EnergyStorage {

    public static final int MAX_RF_TICK = 32000;
    public static final int RF_STORED = 10000000;

    private TileEntityMobFactory factory;
    private EnergyManager(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public EnergyManager(int capacity, int maxReceive, TileEntityMobFactory factory) {

        // no extraction of power
        super(capacity, maxReceive, 0);
        this.factory = factory;
    }

    public void readFromNBT(NBTTagCompound compound){

        this.energy = compound.getInteger("Energy");
    }

    public void writeToNBT(NBTTagCompound compound){

        compound.setInteger("Energy", this.energy);
    }

    @Override
    public boolean canExtract() {

        return super.canExtract();
    }

    @Override
    public boolean canReceive() {

        return super.canReceive() && factory.isFormed();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {

        return factory.isFormed() ? super.receiveEnergy(maxReceive, simulate) : 0;
    }

    @Override
    public int getEnergyStored() {

        return factory.isFormed() ? super.getEnergyStored() : 0;
    }

    @Override
    public int getMaxEnergyStored() {

        return factory.isFormed() ? super.getMaxEnergyStored() : 0;
    }


    public int extractEnergyInternal(int amount)
    {
        int energyExtracted = Math.min(energy, Math.min(Integer.MAX_VALUE, amount));
        energy -= energyExtracted;
        return energyExtracted;
    }
}
