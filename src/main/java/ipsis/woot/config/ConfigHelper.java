package ipsis.woot.config;

import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.simulation.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import static ipsis.woot.modules.factory.Tier.TIER_5;

public class ConfigHelper {

    public static Tier getMobTier(FakeMob fakeMob, World world) {
        Tier tier = TIER_5;
        if (fakeMob.isValid() && world != null) {
            if (WootConfig.get().hasMobOverride(fakeMob, WootConfig.ConfigKey.MOB_TIER)) {
                int v = MobOverride.get().getIntMobOverride(fakeMob, WootConfig.ConfigKey.MOB_TIER);
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

    public static int getIntValueForPerk(PerkType upgradeType, int level) {
        level = MathHelper.clamp(level, 0, 3);
        WootConfig.ConfigKey configKey = getConfigKeyByPerk(upgradeType, level);
        if (configKey != WootConfig.ConfigKey.INVALID_KEY)
            return WootConfig.get().getIntConfig(configKey);
        return 1;
}

    public static int getIntValueForPerk(FakeMob fakeMob, PerkType perkType, int level) {
        int v = 1;
        level = MathHelper.clamp(level, 0, 3);
        if (fakeMob.isValid()) {
            // No override
            if (perkType == PerkType.LOOTING) {
                v = getIntValueForPerk(perkType, level);
            } else {
                WootConfig.ConfigKey configKey = getConfigKeyByPerk(perkType, level);
                if (configKey != WootConfig.ConfigKey.INVALID_KEY) {
                    // Handle mob override and default
                    if (MobOverride.get().hasIntMobOverride(fakeMob, configKey))
                        return MobOverride.get().getIntMobOverride(fakeMob, configKey);
                    else
                        return getIntValueForPerk(perkType, level);
                }
            }
        } else {
            v = getIntValueForPerk(perkType, level);
        }
        return v;
    }

    public static WootConfig.ConfigKey getConfigKeyByPerk(PerkType perkType, int level) {
        if (perkType == PerkType.EFFICIENCY) {
            if (level == 1) return WootConfig.ConfigKey.EFFICIENCY_1;
            if (level == 2) return WootConfig.ConfigKey.EFFICIENCY_2;
            if (level == 3) return WootConfig.ConfigKey.EFFICIENCY_3;
        } else if (perkType == PerkType.MASS) {
            if (level == 1) return WootConfig.ConfigKey.MASS_1;
            if (level == 2) return WootConfig.ConfigKey.MASS_2;
            if (level == 3) return WootConfig.ConfigKey.MASS_3;
        } else if (perkType == PerkType.RATE) {
            if (level == 1) return WootConfig.ConfigKey.RATE_1;
            if (level == 2) return WootConfig.ConfigKey.RATE_2;
            if (level == 3) return WootConfig.ConfigKey.RATE_3;
        }
        return WootConfig.ConfigKey.INVALID_KEY;
    }
}
