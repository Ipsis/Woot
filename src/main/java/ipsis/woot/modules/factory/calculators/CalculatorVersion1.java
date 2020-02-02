package ipsis.woot.modules.factory.calculators;

import ipsis.woot.config.ConfigHelper;
import ipsis.woot.config.WootConfig;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.Setup;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.util.FakeMob;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CalculatorVersion1 {

    static final Logger LOGGER = LogManager.getLogger();

    public static HeartTileEntity.Recipe calculate(Setup setup, World world) {

        List<MobParameters> mobs = new ArrayList<>();
        for (FakeMob fakeMob : setup.getMobs())
            mobs.add(new MobParameters(fakeMob, setup, world));

        int masterTicks = WootConfig.get().getIntConfig(WootConfig.ConfigKey.SPAWN_TICKS);

        // Spawn time is the maximum from all mobs
        for (MobParameters p : mobs) {
            if (p.spawnTicks > masterTicks)
                masterTicks = p.spawnTicks;
        }

        int masterCost = 0;
        for (MobParameters p : mobs)
            masterCost += (p.getCost() * p.massCount);

        int actualTicks = masterTicks;
        int actualCost = masterCost;

        /**
         * Each perk increases the overall cost
         */
        int tempCost = actualCost;
        for (PerkType perkType : setup.getPerks().keySet()) {
            int level = setup.getPerks().get(perkType);
            WootConfig.ConfigKey key = ConfigHelper.getConfigKeyByPerk(perkType, level);
            int cost = 10; // TODO
            actualCost += (int)(tempCost / 100.0F * cost);
        }

        /**
         * Rate perk - reduces spawn time
         */
        if (setup.getPerks().containsKey(PerkType.RATE)) {
            int reduction = (int)(masterTicks / 100.0F * setup.getPerks().get(PerkType.RATE));
            actualTicks = masterTicks - reduction;
            actualTicks = MathHelper.clamp(actualTicks, 1, Integer.MAX_VALUE);
            LOGGER.debug("calculate: rate reducing {} by {} to {}", masterTicks, reduction, actualTicks);
        }

        /**
         * Efficiency perk - reduced cost
         */
        if (setup.getPerks().containsKey(PerkType.EFFICIENCY)) {
            int reduction = (int)(masterCost / 100.0F * setup.getPerks().get(PerkType.EFFICIENCY));
            actualCost = masterCost - reduction;
            actualCost = MathHelper.clamp(actualCost, 0, Integer.MAX_VALUE);
            LOGGER.debug("calculate: cost reducing {} by {} to {}", masterCost, reduction, actualCost);
        }

        LOGGER.debug("calculate: master (ticks:{} mb:{}) actual (ticks:{} mb:{})", masterTicks, masterCost, actualTicks, actualCost);
        return new HeartTileEntity.Recipe(actualTicks, actualCost);
    }
}
