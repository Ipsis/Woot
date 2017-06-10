package ipsis.woot.tileentity.ng.power;

import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

/**
 * Only one area of power storage shared among all the tiers
 */
public class CommonPowerStation extends EnergyStorage implements IPowerStation {

    public CommonPowerStation() {

        super(50000, 10, 0);
    }

    @Override
    public void setTier(EnumMobFactoryTier tier) {

        /* Don't care */
    }

    @Override
    public int consume(int power) {

        return power;
    }

    @Nonnull
    @Override
    public IEnergyStorage getEnergyStorage() {

        return this;
    }
}
