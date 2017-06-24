package ipsis.woot.power.storage;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public interface IPowerStation {

    void setTier(EnumMobFactoryTier tier);
    int consume(int power);
    @Nonnull IEnergyStorage getEnergyStorage();
}
