package ipsis.woot.util;

import net.minecraftforge.energy.EnergyStorage;

/**
 * No limit on the consuming of energy
 */
public class WootEnergyStorage extends EnergyStorage {

    public WootEnergyStorage(int capacity, int maxRx) {
        super(capacity, maxRx, 0);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int consumeInternal(int energy) {

        int energyExtracted = Math.min(this.energy, energy);
        this.energy -= energyExtracted;
        return energyExtracted;
    }
}
