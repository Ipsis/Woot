package ipsis.woot.modules.factory.calculators;

import ipsis.woot.config.Config;
import ipsis.woot.config.ConfigOverride;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.util.FakeMob;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CalculatorVersion2 {

    private static final Logger LOGGER = LogManager.getLogger();

    public static HeartTileEntity.Recipe calculate(FormedSetup setup) {

        int baseSpawnTicks = setup.getMaxSpawnTime();
        int actualSpawnTicks = baseSpawnTicks;
        int resolvedRate = setup.getMinRateValue();

        int fluidCost = 0;
        for (FakeMob fakeMob : setup.getAllMobs()) {
            int mobFluidCost = setup.getAllMobParams().get(fakeMob).baseFluidCost * setup.getAllMobParams().get(fakeMob).perkMassValue;
            if (setup.getAllPerks().containsKey(PerkType.EFFICIENCY)) {
                int fluidSaving = (int)(mobFluidCost / 100.0F) * setup.getAllMobParams().get(fakeMob).perkEfficiencyValue;
                mobFluidCost -= fluidSaving;
                mobFluidCost = MathHelper.clamp(mobFluidCost, 0, Integer.MAX_VALUE);
            }
            fluidCost += mobFluidCost;
        }

        if (setup.getAllPerks().containsKey(PerkType.RATE)) {
            int rateSaving = (int)(baseSpawnTicks / 100.0F) * resolvedRate;
            actualSpawnTicks -= rateSaving;
        }

        return new HeartTileEntity.Recipe(actualSpawnTicks, fluidCost);
    }
}
