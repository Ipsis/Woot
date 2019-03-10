package ipsis.woot.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class WootEnergyStorage extends EnergyStorage {

    public WootEnergyStorage(int capacity, int maxReceive) {
        super(capacity, maxReceive, 0);
    }

    public int extractEnergyInternal(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(energy, Math.min(Integer.MAX_VALUE, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

    public void readFromNBT(NBTTagCompound compound) {
        this.energy = compound.getInt("energy");
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInt("energy", this.energy);
    }

}
