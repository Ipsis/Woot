package ipsis.woot.tileentity.ng;

import net.minecraftforge.energy.EnergyStorage;

/**
 * Only one area of power storage shared among all the tiers
 */
public class CommonPowerStation extends EnergyStorage implements  IPowerStation {

    public CommonPowerStation() {

        super(50000, 10, 0);
    }

    @Override
    public int consume(int power) {

        return power;
    }
}
