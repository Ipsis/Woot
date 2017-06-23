package ipsis.woot.tileentity.ng.mock;

import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.ng.power.storage.IPowerStation;
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
