package ipsis.woot.modules.factory.calculators;

import ipsis.woot.config.Config;
import ipsis.woot.config.ConfigOverride;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.Setup;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.simulator.spawning.SpawnController;
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

        int masterTicks = Config.OVERRIDE.getDefaultInteger(ConfigOverride.OverrideKey.SPAWN_TICKS);

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
            // Cannot override looting
            if (perkType != PerkType.LOOTING && level > 0) {
                ConfigOverride.OverrideKey key = Config.OVERRIDE.getKeyByPerk(perkType, level);
                int cost = 10; // TODO
                actualCost += (int) (tempCost / 100.0F * cost);
            }
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

    public static class MobParameters {

        public FakeMob fakeMob;
        public int spawnTicks;
        public int healthPoints;
        public int massCount;
        public int unitsPerHealthPoint;

        /**
         * A lot of these parameters can use mob specific overrides
         */
        public MobParameters(FakeMob fakeMob, Setup setup, World world) {
            this.fakeMob = fakeMob;
            spawnTicks = Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.SPAWN_TICKS);
            healthPoints = SpawnController.get().getMobHealth(fakeMob, world);

            massCount = Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.MASS_COUNT);
            if (setup.getPerks().containsKey(PerkType.MASS)) {
                int level = setup.getPerks().getOrDefault(PerkType.MASS, 0);
                if (level > 0)
                    massCount = Config.OVERRIDE.getIntegerOrDefault(fakeMob, Config.OVERRIDE.getKeyByPerk(PerkType.MASS, level));
            }

            unitsPerHealthPoint = Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.UNITS_PER_HEALTH);
        }

        public int getCost() {
            if (Config.OVERRIDE.hasOverride(fakeMob, ConfigOverride.OverrideKey.FIXED_COST))
                return Config.OVERRIDE.getInteger(fakeMob, ConfigOverride.OverrideKey.FIXED_COST);
            return healthPoints * unitsPerHealthPoint;
        }
    }
}
