package ipsis.woot.util;

import net.minecraft.util.math.MathHelper;
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

    public void consume(int energy) {
        this.energy -= energy;
        MathHelper.clamp(this.energy, 0, Integer.MAX_VALUE);
    }
}
