package ipsis.woot.manager;

/**
 * Power upgrade allows more RF/tick to be received therefore shortening the spawn time.
 */

public enum SpawnerUpgrade {

    BASE(1),
    POWER_I(2),
    POWER_II(10),
    POWER_III(50),
    MASS_I(2),
    MASS_II(10),
    MASS_III(50),
    LOOTING_I(2),
    LOOTING_II(10),
    LOOTING_III(50);

    int powerFactor;
    SpawnerUpgrade(int powerFactor) {
       this.powerFactor = powerFactor;
    }

    public int getPowerFactor(){
        return this.powerFactor;
    }
}
