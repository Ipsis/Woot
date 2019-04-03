package ipsis.woot.config;

import ipsis.woot.Woot;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class ConfigRegistry {

    public static final ConfigRegistry CONFIG_REGISTRY = new ConfigRegistry();
    private ConfigRegistry() { }

    public void loadFromConfig() {

        for (String s : MobConfig.MOB_OVERRIDE.get()) {

            String[] parts = s.split(",");
            if (parts.length != 3) {
                Woot.LOGGER.error(s + " == INVALID");
            } else {
                Woot.LOGGER.info(s + " == INVALID");
                String mob = parts[0];
                String param = parts[1];
                try {
                    int v = Integer.valueOf(parts[2]);
                    FakeMobKey fakeMobKey = new FakeMobKey(mob);
                    if (fakeMobKey.isValid())
                        addConfigMapping(fakeMobKey, param, v);
                } catch (NumberFormatException e) {
                    Woot.LOGGER.error(s + " == INVALID");
                }
            }
        }
    }

    private HashMap<FakeMobKey, HashMap<String, Integer>> mappings = new HashMap<>();
    private void addConfigMapping(@Nonnull FakeMobKey fakeMobKey, String key, int value) {
        if (!mappings.containsKey(fakeMobKey))
            mappings.put(fakeMobKey, new HashMap<>());
        HashMap<String, Integer> map = mappings.get(fakeMobKey);
        map.put(key, value);
        Woot.LOGGER.info("addConfigMapping: " + fakeMobKey + " " + key + "/" + value);
    }

    /**
     * Lookup
     * Tier = 0 is no upgrade
     */
    private static final String[] MASS_KEYS = { "MASS", "MASS_1", "MASS_2", "MASS_3" };
    public int getMass(@Nonnull FakeMobKey fakeMobKey, int tier) {
        int mass = FactoryConfig.SPAWN_MOB_COUNT.get();
        tier = MathHelper.clamp(tier, 0, MASS_KEYS.length - 1);
        if (mappings.containsKey(fakeMobKey)) {
            if (mappings.get(fakeMobKey).containsKey(MASS_KEYS[tier]))
                mass = mappings.get(fakeMobKey).get(MASS_KEYS[tier]);
        }

        return MathHelper.clamp(mass, 1, Integer.MAX_VALUE);
    }

    private static final String[] LOOTING_KEYS = { "LOOTING", "LOOTING_1", "LOOTING_2", "LOOTING_2" };
    public int getLooting(@Nonnull FakeMobKey fakeMobKey, int tier) {
        // There is no looting override
        if (tier == 0) return 0;
        else if (tier == 1) return 1;
        else if (tier == 2) return 2;
        else if (tier == 3) return 3;
        else return 0;
    }

    private static final String[] RATE_KEYS = { "RATE", "RATE_1", "RATE_2", "RATE_3" };
    public int getRate(@Nonnull FakeMobKey fakeMobKey, int tier) {
        int rate = FactoryConfig.SPAWN_RATE.get();
        tier = MathHelper.clamp(tier, 0, RATE_KEYS.length - 1);
        if (mappings.containsKey(fakeMobKey)) {
            if (mappings.get(fakeMobKey).containsKey(RATE_KEYS[tier]))
                rate = mappings.get(fakeMobKey).get(RATE_KEYS[tier]);
        }

        return MathHelper.clamp(rate, 1, Integer.MAX_VALUE);
    }
}
