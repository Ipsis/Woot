package ipsis.woot.power.storage;

import ipsis.woot.block.BlockMobFactoryCell;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public class SinglePowerStation implements IPowerStation {

    WootEnergyStorage energyStorage;

    public SinglePowerStation(BlockMobFactoryCell.EnumCellTier tier) {

        // TODO pull from config
        if (tier == BlockMobFactoryCell.EnumCellTier.TIER_I)
            energyStorage = new WootEnergyStorage(10000, 20);
        else if (tier == BlockMobFactoryCell.EnumCellTier.TIER_II)
            energyStorage = new WootEnergyStorage(100000, 50);
        else if (tier == BlockMobFactoryCell.EnumCellTier.TIER_III)
            energyStorage = new WootEnergyStorage(1000000, 100);
    }

    @Override
    public void setTier(EnumMobFactoryTier tier) {
        // NA
    }

    @Override
    public int consume(int power) {

        int consumed = energyStorage.extractEnergyInternal(power, true);
        if (consumed > 0)
            energyStorage.extractEnergyInternal(consumed, false);

        return consumed;
    }

    @Nonnull
    @Override
    public IEnergyStorage getEnergyStorage() {

        return energyStorage;
    }
}
