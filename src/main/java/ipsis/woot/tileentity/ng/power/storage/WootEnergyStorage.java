package ipsis.woot.tileentity.ng.power.storage;

import net.minecraftforge.energy.EnergyStorage;

public class WootEnergyStorage extends EnergyStorage {

    public WootEnergyStorage(int capacity, int maxReceive) {

        super(capacity, maxReceive, 0);
    }

    /**
     * Only internal usage of the power storage
     */
    public int extractEnergyInternal(int maxExtract, boolean simulate) {

        int energyExtracted = Math.min(energy, Math.min(Integer.MAX_VALUE, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }
}
