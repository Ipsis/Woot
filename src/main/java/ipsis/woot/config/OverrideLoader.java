package ipsis.woot.config;

import ipsis.woot.Woot;
import ipsis.woot.policy.PolicyConfiguration;
import ipsis.woot.util.FakeMob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverrideLoader {

    public static void loadFromConfig() {

        HashMap<ConfigOverride.OverrideKey, List<String>> configs = new HashMap<>();
        configs.put(ConfigOverride.OverrideKey.MASS_COUNT, PolicyConfiguration.MOB_MASS_COUNT.get());
        configs.put(ConfigOverride.OverrideKey.SPAWN_TICKS, PolicyConfiguration.MOB_SPAWN_TICKS.get());
        configs.put(ConfigOverride.OverrideKey.HEALTH, PolicyConfiguration.MOB_HEALTH.get());
        configs.put(ConfigOverride.OverrideKey.XP, PolicyConfiguration.MOB_XP.get());
        configs.put(ConfigOverride.OverrideKey.UNITS_PER_HEALTH, PolicyConfiguration.MOB_UNITS_PER_HEALTH.get());
        configs.put(ConfigOverride.OverrideKey.TIER, PolicyConfiguration.MOB_TIER.get());
        configs.put(ConfigOverride.OverrideKey.SHARD_KILLS, PolicyConfiguration.MOB_SHARD_KILLS.get());
        configs.put(ConfigOverride.OverrideKey.FIXED_COST, PolicyConfiguration.MOB_FIXED_COST.get());
        configs.put(ConfigOverride.OverrideKey.PERK_EFFICIENCY_1_REDUCTION, PolicyConfiguration.MOB_PERK_EFFICIENCY_1.get());
        configs.put(ConfigOverride.OverrideKey.PERK_EFFICIENCY_2_REDUCTION, PolicyConfiguration.MOB_PERK_EFFICIENCY_2.get());
        configs.put(ConfigOverride.OverrideKey.PERK_EFFICIENCY_3_REDUCTION, PolicyConfiguration.MOB_PERK_EFFICIENCY_3.get());
        configs.put(ConfigOverride.OverrideKey.PERK_MASS_1_COUNT, PolicyConfiguration.MOB_PERK_MASS_1.get());
        configs.put(ConfigOverride.OverrideKey.PERK_MASS_2_COUNT, PolicyConfiguration.MOB_PERK_MASS_2.get());
        configs.put(ConfigOverride.OverrideKey.PERK_MASS_3_COUNT, PolicyConfiguration.MOB_PERK_MASS_3.get());
        configs.put(ConfigOverride.OverrideKey.PERK_RATE_1_REDUCTION, PolicyConfiguration.MOB_PERK_RATE_1.get());
        configs.put(ConfigOverride.OverrideKey.PERK_RATE_2_REDUCTION, PolicyConfiguration.MOB_PERK_RATE_2.get());
        configs.put(ConfigOverride.OverrideKey.PERK_RATE_3_REDUCTION, PolicyConfiguration.MOB_PERK_RATE_3.get());
        configs.put(ConfigOverride.OverrideKey.PERK_XP_1_PERCENTAGE, PolicyConfiguration.MOB_PERK_XP_1.get());
        configs.put(ConfigOverride.OverrideKey.PERK_XP_2_PERCENTAGE, PolicyConfiguration.MOB_PERK_XP_2.get());
        configs.put(ConfigOverride.OverrideKey.PERK_XP_3_PERCENTAGE, PolicyConfiguration.MOB_PERK_XP_3.get());

        for (Map.Entry<ConfigOverride.OverrideKey, List<String>> entry : configs.entrySet()) {
            for (String s : entry.getValue()) {
                String[] parts = s.split(",");
                if (parts.length != 2) {
                    Woot.setup.getLogger().error(s + " == INVALID");
                } else {
                    String mob = parts[0];
                    FakeMob fakeMob = new FakeMob((mob));
                    if (!fakeMob.isValid()) {
                        Woot.setup.getLogger().error(s + " == INVALID (mob {})", mob);
                    } else {
                        if (entry.getKey().getClazz() == Integer.class) {
                            try {
                                int v = Integer.valueOf(parts[1]);
                                Config.OVERRIDE.add(fakeMob, entry.getKey(), v);
                            } catch (NumberFormatException e) {
                                Woot.setup.getLogger().error(s + " == INVALID (integer value {})", parts[1]);
                            }
                        }
                    }
                }
            }
        }
    }

}
