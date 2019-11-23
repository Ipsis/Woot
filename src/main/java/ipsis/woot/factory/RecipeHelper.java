package ipsis.woot.factory;

import ipsis.woot.common.Config;
import ipsis.woot.factory.blocks.heart.HeartTileEntity;
import ipsis.woot.simulation.SpawnController;
import ipsis.woot.util.FakeMob;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RecipeHelper {

    static final Logger LOGGER = LogManager.getLogger();
    /**
     * Calculate the cost of running the factory for the provided configuration
     */
    public static HeartTileEntity.Recipe createRecipe(Setup setup, World world) {
        HeartTileEntity.Recipe recipe = new HeartTileEntity.Recipe(10, 10);
        List<MobConfig> mobs = new ArrayList<>();
        
        for (FakeMob fakeMob : setup.getMobs()) {
            MobConfig mobConfig = new MobConfig(new FakeMob(fakeMob), setup, world);
            mobs.add(mobConfig);
            LOGGER.debug("createRecipe: {} {}", fakeMob, mobConfig);
        }

        // Get the longest tick count
        int masterTicks =  Config.COMMON.SPAWN_TICKS.get();
        int masterMobCount = setup.getMaxMobCount();
        if (setup.upgrades.containsKey(FactoryUpgradeType.MASS)) {
            int level = setup.upgrades.get(FactoryUpgradeType.MASS);
            masterMobCount = Config.getIntValueForUpgrade(FactoryUpgradeType.MASS, level);
        }
        for (MobConfig m : mobs) {
            if (m.spawnTicks > masterTicks)
                masterTicks = m.spawnTicks;
            if (m.mass < masterMobCount)
                masterMobCount = m.mass;
        }
        LOGGER.debug("createRecipe: masterTicks:{} masterMobCount:{}", masterTicks, masterMobCount);

        int mobCost = 0;
        for (MobConfig m : mobs)
            mobCost += (m.getCost() * masterMobCount);
        LOGGER.debug("createRecipe: mobCost:{}", mobCost);

        int actualTicks = masterTicks;
        int totalCost = mobCost;
        LOGGER.debug("createRecipe created actualTicks:{} totalCost:{}", actualTicks, totalCost);
        return new HeartTileEntity.Recipe(actualTicks, totalCost);
    }
    
    static class MobConfig {
        public FakeMob fakeMob;
        public int spawnTicks;
        public int health;
        public int mass;
        public int units_per_health;
       
        private MobConfig() {}
        public MobConfig(FakeMob fakeMob, Setup setup, World world) {
            this.fakeMob = fakeMob;
            if (Config.hasIntValueByString(fakeMob, Config.COMMON.SPAWN_TICKS_TAG))
                spawnTicks = Config.getIntValueByString(fakeMob, Config.COMMON.SPAWN_TICKS_TAG);
            else
                spawnTicks = Config.COMMON.SPAWN_TICKS.get();
            health = SpawnController.get().getMobHealth(fakeMob, world);
            mass = Config.getIntValueForUpgrade(fakeMob,
                    FactoryUpgradeType.MASS,
                    setup.getUpgrades().getOrDefault(FactoryUpgradeType.MASS, 0));
            if (Config.hasIntValueByString(fakeMob, Config.COMMON.UNITS_PER_HEALTH_TAG))
                units_per_health = Config.getIntValueByString(fakeMob, Config.COMMON.UNITS_PER_HEALTH_TAG);
            else
                units_per_health = Config.COMMON.UNITS_PER_HEALTH.get();
        }

        public int getCost() {
            return spawnTicks * (health * units_per_health);
        }

        @Override
        public String toString() {
            return String.format("spawnTicks:%d health:%d mass:%d units_per_health:%d",
                    spawnTicks, health, mass, units_per_health);
        }
    }
}
