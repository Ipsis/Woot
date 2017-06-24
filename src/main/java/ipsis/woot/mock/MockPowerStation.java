package ipsis.woot.mock;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.power.storage.IPowerStation;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public class MockPowerStation implements IPowerStation {

    @Override
    public void setTier(EnumMobFactoryTier tier) {

    }

    @Override
    public int consume(int power) {

        return power;
    }

    @Nonnull
    @Override
    public IEnergyStorage getEnergyStorage() {

        return new EnergyStorage(10000000, Integer.MAX_VALUE);
    }
}
