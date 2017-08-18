package ipsis.woot.power.storage;

import ipsis.Woot;
import ipsis.woot.block.BlockMobFactoryCell;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public class SinglePowerStation implements IPowerStation {

    WootEnergyStorage energyStorage;
    BlockMobFactoryCell.EnumCellTier tier;

    public SinglePowerStation() {

        setTier(BlockMobFactoryCell.EnumCellTier.TIER_I);
    }

    @Override
    public void setTier(BlockMobFactoryCell.EnumCellTier tier) {

        this.tier = tier;
        if (tier == BlockMobFactoryCell.EnumCellTier.TIER_I)
            energyStorage = new WootEnergyStorage(Woot.wootConfiguration.getInteger(EnumConfigKey.T1_POWER_MAX), Woot.wootConfiguration.getInteger(EnumConfigKey.T1_POWER_RX_TICK));
        else if (tier == BlockMobFactoryCell.EnumCellTier.TIER_II)
            energyStorage = new WootEnergyStorage(Woot.wootConfiguration.getInteger(EnumConfigKey.T2_POWER_MAX), Woot.wootConfiguration.getInteger(EnumConfigKey.T2_POWER_RX_TICK));
        else if (tier == BlockMobFactoryCell.EnumCellTier.TIER_III)
            energyStorage = new WootEnergyStorage(Woot.wootConfiguration.getInteger(EnumConfigKey.T3_POWER_MAX), Woot.wootConfiguration.getInteger(EnumConfigKey.T3_POWER_RX_TICK));
    }

    @Override
    public int consume(int power) {

        int consumed = energyStorage.extractEnergyInternal(power, true);
        if (consumed > 0)
            energyStorage.extractEnergyInternal(consumed, false);

        return consumed;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        setTier(BlockMobFactoryCell.EnumCellTier.byMetadata(compound.getInteger("Tier")));
        energyStorage.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {

        compound.setInteger("Tier", tier.ordinal());
        energyStorage.writeToNBT(compound);
    }

    @Nonnull
    @Override
    public IEnergyStorage getEnergyStorage() {

        return energyStorage;
    }
}
