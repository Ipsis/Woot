package ipsis.woot.configuration;

import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.WootMobName;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class WootConfigurationManager implements IWootConfiguration {

    /**
     * Main Configuration
     */
    private Map<EnumConfigKey, Integer> integerMap = new HashMap<>();
    private Map<EnumConfigKey, Boolean> booleanMap = new HashMap<>();
    private Map<String, Integer> integerMobMap = new HashMap<>();
    private Map<String, Boolean> booleanMobMap = new HashMap<>();

    private String makeKey(WootMobName wootMobName, EnumConfigKey configKey) {
        return wootMobName.toString() + ":" + configKey.toString();
    }

    /**
     * IWootConfiguration
     */
    @Override
    public boolean getBoolean(EnumConfigKey key) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.CONFIG_ACCESS, "getBoolean", key);
        return booleanMap.get(key);
    }

    @Override
    public boolean getBoolean(WootMobName wootMobName, EnumConfigKey key) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.CONFIG_ACCESS, "getBoolean", wootMobName + " " + key);
        String k = makeKey(wootMobName, key);
        if (booleanMobMap.containsKey(k))
            return booleanMobMap.get(k);

        return booleanMap.get(key);
    }

    @Override
    public int getInteger(EnumConfigKey key) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.CONFIG_ACCESS, "getInteger", key);
        return integerMap.get(key);
    }

    @Override
    public int getInteger(WootMobName wootMobName, EnumConfigKey key) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.CONFIG_ACCESS, "getInteger", wootMobName + " " + key);
        String k = makeKey(wootMobName, key);
        if (integerMobMap.containsKey(k))
            return integerMobMap.get(k);

        return integerMap.get(key);
    }

    @Override
    public void setBoolean(EnumConfigKey key, boolean v) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.CONFIG_LOAD, "setBoolean",  key + " " + v);
        booleanMap.put(key, v);
    }

    @Override
    public void setBoolean(WootMobName wootMobName, EnumConfigKey key, boolean v) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.CONFIG_LOAD, "setBoolean", wootMobName + " " + key + " " + v);
        if (key.canMobOverride()) {
            String k = makeKey(wootMobName, key);
            booleanMobMap.put(k, v);
        }
    }

    @Override
    public void setInteger(EnumConfigKey key, int v) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.CONFIG_LOAD, "setInteger", key + " " + v);
        integerMap.put(key, v);
    }

    @Override
    public void setInteger(WootMobName wootMobName, EnumConfigKey key, int v) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.CONFIG_LOAD, "setInteger", wootMobName + " " + key + " " + v);
        if (key.canMobOverride()) {
            String k = makeKey(wootMobName, key);
            integerMobMap.put(k, v);
        }
    }

    @Override
    public void addToDragonDrops(EnumEnchantKey enchantKey, String itemName, int stackSize, float chance) {

    }

    @Nonnull
    @Override
    public EnumMobFactoryTier getFactoryTier(World world, WootMobName wootMobName) {

        EnumMobFactoryTier tier;
        String key = makeKey(wootMobName, EnumConfigKey.FACTORY_TIER);
        if (integerMobMap.containsKey(key)) {
            // User will select 1-4, but getTier expects enum oridinals
            int tierKey = integerMobMap.get(key) - 1;
            tier = EnumMobFactoryTier.getTier(tierKey);
        } else {
            int cost = getSpawnCost(world, wootMobName);
            if (cost >= getInteger(wootMobName, EnumConfigKey.T4_UNITS_MAX))
                tier = EnumMobFactoryTier.TIER_FOUR;
            else if (cost >= getInteger(wootMobName, EnumConfigKey.T3_UNITS_MAX))
                tier = EnumMobFactoryTier.TIER_THREE;
            else if (cost >= getInteger(wootMobName, EnumConfigKey.T2_UNITS_MAX))
                tier = EnumMobFactoryTier.TIER_TWO;
            else
                tier = EnumMobFactoryTier.TIER_ONE;
        }

        return tier;
    }

    @Override
    public int getSpawnCost(World world, WootMobName wootMobName) {

        String key = makeKey(wootMobName, EnumConfigKey.SPAWN_UNITS);
        if (integerMobMap.containsKey(key))
            return integerMobMap.get(key);

        return Woot.mobCosting.getMobSpawnCost(world, wootMobName);
    }

    @Override
    public int getDeathCost(World world, WootMobName wootMobName) {

        String key = makeKey(wootMobName, EnumConfigKey.DEATH_XP);
        if (integerMobMap.containsKey(key))
            return integerMobMap.get(key);

        return Woot.wootConfiguration.getInteger(EnumConfigKey.DEATH_XP);
    }


}
