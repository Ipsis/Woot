package ipsis.woot.common;

import ipsis.woot.factory.Tier;
import ipsis.woot.simulation.SpawnController;
import ipsis.woot.util.FakeMob;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.HashMap;

/**
 * This handles the fact that we can override lots of configuration
 * based on the specific mob. It is populated by information pushed
 * from the main config files
 */
public class WootConfig {

    static final Logger LOGGER = LogManager.getLogger();
    static final Marker CONFIG = MarkerManager.getMarker("WOOT_CONFIG");

    public static WootConfig get() { return INSTANCE; }
    static WootConfig INSTANCE;
    static { INSTANCE = new WootConfig(); }

    public void loadFromConfig() {

        // Get rid of all the old mappings
        intMappings = new HashMap<>();

        for (String s : Config.COMMON.MOB_OVERRIDES.get()) {
            String[] parts = s.split(",");
            if (parts.length != 3) {
                LOGGER.error(s + " == INVALID");
            } else {
                String mob = parts[0];
                String param = parts[1];
                try {
                    int v = Integer.valueOf(parts[2]);
                    FakeMob fakeMob = new FakeMob(mob);
                    Key key = Key.valueOf(param.toUpperCase());

                    if (fakeMob.isValid())
                        addIntMapping(fakeMob, key, v);
                    else
                        LOGGER.error(s + " == INVALID (invalid mob)");
                } catch (NumberFormatException e) {
                    LOGGER.error(s + " == INVALID (invalid value)");
                } catch (IllegalArgumentException e) {
                    LOGGER.error(s + " == INVALID (unknown key");
                }
            }
        }
    }

    HashMap<FakeMob, HashMap<Key, Integer>> intMappings = new HashMap<>();
    void addIntMapping(FakeMob fakeMob, Key key, int value) {
        if (!intMappings.containsKey(fakeMob))
            intMappings.put(fakeMob, new HashMap<>());
        HashMap<Key, Integer> map = intMappings.get(fakeMob);
        map.put(key, value);
        LOGGER.info(CONFIG, "Added mapping {}:{} -> {}", fakeMob, key, value);
    }

    public int getIntValue(FakeMob fakeMob, Key key) {
        if (intMappings.containsKey(fakeMob) && intMappings.get(fakeMob).containsKey(key))
            return intMappings.get(fakeMob).get(key);
        return Config.getIntValue(key);
    }

    public boolean hasMobValue(FakeMob fakeMob, Key key) {
        return intMappings.containsKey(fakeMob) && intMappings.get(fakeMob).containsKey(key);
    }

    public Tier getMobTier(FakeMob fakeMob, World world) {
        Tier tier;

        if (hasMobValue(fakeMob, Key.TIER)) {
            int v = getIntValue(fakeMob, Key.TIER);
            v = MathHelper.clamp(v, 1, Tier.getMaxTier());
            tier = Tier.byIndex(v);
        } else {
            int health = SpawnController.get().getMobHealth(fakeMob, world);
            if (health <= Config.COMMON.TIER_1_MAX_UNITS.get())
                tier = Tier.TIER_1;
            else if (health <= Config.COMMON.TIER_2_MAX_UNITS.get())
                tier = Tier.TIER_2;
            else if (health <= Config.COMMON.TIER_3_MAX_UNITS.get())
                tier = Tier.TIER_3;
            else
                tier = Tier.TIER_4;
        }
        return tier;
    }

    public enum Key {
        MASS,
        MASS_1,
        MASS_2,
        MASS_3,
        SPAWN_UNITS,
        SPAWN_TICKS,
        TIER
    }
}
