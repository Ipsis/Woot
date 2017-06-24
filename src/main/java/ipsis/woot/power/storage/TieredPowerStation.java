package ipsis.woot.power.storage;

import ipsis.Woot;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.configuration.EnumConfigKey;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

/**
 * Power storage per tier
 */
public class TieredPowerStation implements IPowerStation {

    EnumMobFactoryTier tier;
    WootEnergyStorage energyStorage[];

    public TieredPowerStation() {

        tier = EnumMobFactoryTier.TIER_ONE;

        energyStorage[0] = new WootEnergyStorage(
                Woot.wootConfiguration.getInteger(EnumConfigKey.T1_POWER_MAX),
                Woot.wootConfiguration.getInteger(EnumConfigKey.T1_POWER_RX_TICK));

        energyStorage[1] = new WootEnergyStorage(
                Woot.wootConfiguration.getInteger(EnumConfigKey.T2_POWER_MAX),
                Woot.wootConfiguration.getInteger(EnumConfigKey.T2_POWER_RX_TICK));

        energyStorage[2] = new WootEnergyStorage(
                Woot.wootConfiguration.getInteger(EnumConfigKey.T3_POWER_MAX),
                Woot.wootConfiguration.getInteger(EnumConfigKey.T3_POWER_RX_TICK));

        energyStorage[3] = new WootEnergyStorage(
                Woot.wootConfiguration.getInteger(EnumConfigKey.T4_POWER_MAX),
                Woot.wootConfiguration.getInteger(EnumConfigKey.T4_POWER_RX_TICK));
    }

    private WootEnergyStorage getWootEnergyStorage() {

        if (tier == EnumMobFactoryTier.TIER_ONE)
            return energyStorage[0];
        else if (tier == EnumMobFactoryTier.TIER_TWO)
            return energyStorage[1];
        else if (tier == EnumMobFactoryTier.TIER_THREE)
            return energyStorage[2];
        else
            return energyStorage[3];
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder().append(tier).append(":");
        for (WootEnergyStorage e : energyStorage) {
            sb.append(" ").append(e.getEnergyStored()).append("/").append(e.getMaxEnergyStored());
        }

        return sb.toString();
    }

    /**
     * IPowerStation
     */
    @Override
    public int consume(int power) {

        WootEnergyStorage battery = getWootEnergyStorage();
        int consumed = battery.extractEnergyInternal(power, false);
        if (consumed > 0) {
            for (WootEnergyStorage e : energyStorage)
                if (e != battery)
                    e.extractEnergyInternal(consumed, false);
        }

        return consumed;
    }

    @Nonnull
    @Override
    public IEnergyStorage getEnergyStorage() {

        return getWootEnergyStorage();
    }

    @Override
    public void setTier(EnumMobFactoryTier tier) {

        this.tier = tier;
    }
}
