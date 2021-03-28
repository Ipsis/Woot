package ipsis.woot.config;

import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.simulator.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ConfigOverride {

    private final Logger LOGGER = LogManager.getLogger();
    private HashMap<FakeMob, HashMap<OverrideKey, Object>> overrides = new HashMap<>();
    private HashMap<Perk.Group, OverrideKey[]> perkMap = new HashMap<>();

    public ConfigOverride() {
        perkMap.put(Perk.Group.EFFICIENCY,
                new OverrideKey[]{OverrideKey.PERK_EFFICIENCY_1_REDUCTION, OverrideKey.PERK_EFFICIENCY_2_REDUCTION, OverrideKey.PERK_EFFICIENCY_3_REDUCTION});
        perkMap.put(Perk.Group.MASS,
                new OverrideKey[]{OverrideKey.PERK_MASS_1_COUNT, OverrideKey.PERK_MASS_2_COUNT, OverrideKey.PERK_MASS_3_COUNT});
        perkMap.put(Perk.Group.RATE,
                new OverrideKey[]{OverrideKey.PERK_RATE_1_REDUCTION, OverrideKey.PERK_RATE_2_REDUCTION, OverrideKey.PERK_RATE_3_REDUCTION});
        perkMap.put(Perk.Group.XP,
                new OverrideKey[]{OverrideKey.PERK_XP_1_PERCENTAGE, OverrideKey.PERK_XP_2_PERCENTAGE, OverrideKey.PERK_XP_3_PERCENTAGE});
        perkMap.put(Perk.Group.HEADLESS,
                new OverrideKey[]{OverrideKey.PERK_HEADLESS_1_PERCENTAGE, OverrideKey.PERK_HEADLESS_2_PERCENTAGE, OverrideKey.PERK_HEADLESS_3_PERCENTAGE});
    }

    public void add(FakeMob fakeMob, OverrideKey key, int v) {

        if (key.getClazz() != Integer.class) {
            LOGGER.error("Attempt to set integer override for {}:{}", fakeMob, key);
            return;
        }

        HashMap<OverrideKey, Object> map = overrides.getOrDefault(fakeMob, null);
        if (map == null) {
            map = new HashMap<>();
            overrides.put(fakeMob, map);
        }
        map.put(key, v);
        LOGGER.info("Added integer override of {}:{} -> {}", fakeMob, key, v);
    }

    public boolean hasOverride(FakeMob fakeMob, OverrideKey key) {
        HashMap<OverrideKey, Object> map = overrides.getOrDefault(fakeMob, null);
        if (map == null)
            return false;
        return map.containsKey(key);
    }

    /**
     * Returns the mob specific or default value
     */
    public int getIntegerOrDefault(FakeMob fakeMob, ConfigOverride.OverrideKey key) {
        if (Config.OVERRIDE.hasOverride(fakeMob, key))
            return Config.OVERRIDE.getInteger(fakeMob, key);

        return Config.OVERRIDE.getDefaultInteger(key);
    }

    /**
     * Returns the mob specific value
     */
    public int getInteger(FakeMob fakeMob, OverrideKey key) {
        if (key.clazz != Integer.class) {
            LOGGER.error("Config override " + key + " is not an integer");
            return -1;
        }

        HashMap<OverrideKey, Object> map = overrides.getOrDefault(fakeMob, null);
        if (map == null) {
            LOGGER.error("Config override " + fakeMob + " does not exist");
            return -1;
        }

        if (!map.containsKey(key)) {
            LOGGER.error("Config override " + fakeMob + "/" + key + " does not exist");
            return -1;
        }

        if (!(map.get(key) instanceof Integer)) {
            LOGGER.error("Config override " + fakeMob + "/" + key + " is not an integer");
            return -1;
        }

        return (int)map.get(key);
    }

    public int getDefaultInteger(OverrideKey key) {
        // HEALTH and TIER handled elsewhere

        if (key == OverrideKey.MASS_COUNT)
            return FactoryConfiguration.MASS_COUNT.get();
        if (key == OverrideKey.SPAWN_TICKS)
            return FactoryConfiguration.SPAWN_TICKS.get();
        if (key == OverrideKey.UNITS_PER_HEALTH)
            return FactoryConfiguration.UNITS_PER_HEALTH.get();
        if (key == OverrideKey.SHARD_KILLS)
            return FactoryConfiguration.MOB_SHARD_KILLS.get();
        if (key == OverrideKey.PERK_EFFICIENCY_1_REDUCTION)
            return FactoryConfiguration.EFFICIENCY_1.get();
        if (key == OverrideKey.PERK_EFFICIENCY_2_REDUCTION)
            return FactoryConfiguration.EFFICIENCY_2.get();
        if (key == OverrideKey.PERK_EFFICIENCY_3_REDUCTION)
            return FactoryConfiguration.EFFICIENCY_3.get();
        if (key == OverrideKey.PERK_MASS_1_COUNT)
            return FactoryConfiguration.MASS_COUNT_1.get();
        if (key == OverrideKey.PERK_MASS_2_COUNT)
            return FactoryConfiguration.MASS_COUNT_2.get();
        if (key == OverrideKey.PERK_MASS_3_COUNT)
            return FactoryConfiguration.MASS_COUNT_3.get();
        if (key == OverrideKey.PERK_RATE_1_REDUCTION)
            return FactoryConfiguration.RATE_1.get();
        if (key == OverrideKey.PERK_RATE_2_REDUCTION)
            return FactoryConfiguration.RATE_2.get();
        if (key == OverrideKey.PERK_RATE_3_REDUCTION)
            return FactoryConfiguration.RATE_3.get();
        if (key == OverrideKey.PERK_XP_1_PERCENTAGE)
            return FactoryConfiguration.XP_1.get();
        if (key == OverrideKey.PERK_XP_2_PERCENTAGE)
            return FactoryConfiguration.XP_2.get();
        if (key == OverrideKey.PERK_XP_3_PERCENTAGE)
            return FactoryConfiguration.XP_3.get();
        if (key == OverrideKey.PERK_HEADLESS_1_PERCENTAGE)
            return FactoryConfiguration.HEADLESS_1.get();
        if (key == OverrideKey.PERK_HEADLESS_2_PERCENTAGE)
            return FactoryConfiguration.HEADLESS_2.get();
        if (key == OverrideKey.PERK_HEADLESS_3_PERCENTAGE)
            return FactoryConfiguration.HEADLESS_3.get();

        return INVALID_CONFIG_OVERRIDE_DEFAULT;
    }

    public static final int INVALID_CONFIG_OVERRIDE_DEFAULT = -1;

    public enum OverrideKey {
        MASS_COUNT(ConfigPath.Factory.MASS_COUNT_TAG),
        SPAWN_TICKS(ConfigPath.Factory.SPAWN_TICKS_TAG),
        HEALTH(ConfigPath.Factory.HEALTH_TAG),
        XP(ConfigPath.Factory.XP_TAG),
        UNITS_PER_HEALTH(ConfigPath.Factory.MB_PER_HEALTH_TAG),
        TIER(ConfigPath.Factory.FIXED_TIER_TAG),
        SHARD_KILLS(ConfigPath.Factory.SHARD_KILLS_TAG),
        FIXED_COST(ConfigPath.Factory.FIXED_COST_TAG),
        PERK_EFFICIENCY_1_REDUCTION("perkEff" + ConfigPath.Factory.EFFICIENCY_1_TAG),
        PERK_EFFICIENCY_2_REDUCTION("perkEff" + ConfigPath.Factory.EFFICIENCY_2_TAG),
        PERK_EFFICIENCY_3_REDUCTION("perkEff" + ConfigPath.Factory.EFFICIENCY_3_TAG),
        PERK_MASS_1_COUNT("perkMass" + ConfigPath.Factory.MASS_1_TAG),
        PERK_MASS_2_COUNT("perkMass" + ConfigPath.Factory.MASS_2_TAG),
        PERK_MASS_3_COUNT("perkMass" + ConfigPath.Factory.MASS_3_TAG),
        PERK_RATE_1_REDUCTION("perkRate" + ConfigPath.Factory.RATE_1_TAG),
        PERK_RATE_2_REDUCTION("perkRate" + ConfigPath.Factory.RATE_2_TAG),
        PERK_RATE_3_REDUCTION("perkRate" + ConfigPath.Factory.RATE_3_TAG),
        PERK_XP_1_PERCENTAGE("perkXp" + ConfigPath.Factory.XP_1_TAG),
        PERK_XP_2_PERCENTAGE("perkXp" + ConfigPath.Factory.XP_1_TAG),
        PERK_XP_3_PERCENTAGE("perkXp" + ConfigPath.Factory.XP_1_TAG),
        PERK_HEADLESS_1_PERCENTAGE("perkHeadless" + ConfigPath.Factory.HEADLESS_1_TAG),
        PERK_HEADLESS_2_PERCENTAGE("perkHeadless" + ConfigPath.Factory.HEADLESS_2_TAG),
        PERK_HEADLESS_3_PERCENTAGE("perkHeadless" + ConfigPath.Factory.HEADLESS_3_TAG)
        ;

        private Class clazz;
        private String tag;
        private OverrideKey(String tag) {
            this(Integer.class);
            this.tag = tag;
        }
        private OverrideKey(Class clazz) { this.clazz = clazz; }
        public Class getClazz() { return clazz; }
        public String getTag() { return this.tag; }

        public static boolean isValidTag(String s) {
            for (OverrideKey k : values()) {
                if (k.tag.equalsIgnoreCase(s))
                    return true;
            }
            return false;
        }

        public static @Nullable OverrideKey getFromString(String s) {
            for (OverrideKey k : values()) {
                if (k.tag.equalsIgnoreCase(s))
                    return k;
            }
            return null;
        }
    }

    public OverrideKey getKeyByPerk(Perk.Group group, int level) {
        level = MathHelper.clamp(level, 1, 3) - 1;
        return perkMap.get(group)[level];
    }

    public Tier getMobTier(FakeMob fakeMob, World world) {
        Tier tier = Tier.TIER_5;
        if (fakeMob.isValid() && world != null) {
            if (Config.OVERRIDE.hasOverride(fakeMob, ConfigOverride.OverrideKey.TIER)) {
                int v = Config.OVERRIDE.getInteger(fakeMob, ConfigOverride.OverrideKey.TIER);
                v = MathHelper.clamp(v, 1, Tier.getMaxTier());
                tier = Tier.byIndex(v);
            } else {

                int health = SpawnController.get().getMobHealth(fakeMob, world);
                if (health <= FactoryConfiguration.TIER_1_MAX_UNITS.get())
                    tier = Tier.TIER_1;
                else if (health <= FactoryConfiguration.TIER_2_MAX_UNITS.get())
                    tier = Tier.TIER_2;
                else if (health <= FactoryConfiguration.TIER_3_MAX_UNITS.get())
                    tier = Tier.TIER_3;
                else if (health <= FactoryConfiguration.TIER_4_MAX_UNITS.get())
                    tier = Tier.TIER_4;
            }
        }
        return tier;
    }

    public String getConfigAsString(FakeMob fakeMob, String key) {
        ConfigOverride.OverrideKey overrideKey = ConfigOverride.OverrideKey.getFromString(key);
        return "getConfigAsString not supported";
    }
}
