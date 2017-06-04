package ipsis.woot.tileentity.ng;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.ng.configuration.EnumConfigKey;
import ipsis.woot.tileentity.ng.configuration.IWootConfiguration;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class WootConfiguration implements IWootConfiguration {

    private Map<EnumConfigKey, Integer> integerMap = new HashMap<>();
    private Map<EnumConfigKey, Boolean> booleanMap = new HashMap<>();
    private Map<String, Integer> integerMobMap = new HashMap<>();
    private Map<String, Boolean> booleanMobMap = new HashMap<>();

    private String makeKey(WootMobName wootMobName, EnumConfigKey key) {

        return wootMobName.toString() + ":" + key.toString();
    }

    @Override
    public boolean getBoolean(EnumConfigKey key) {

        return booleanMap.get(key);
    }

    @Override
    public boolean getBoolean(WootMobName wootMobName, EnumConfigKey key) {

        String mapKey = makeKey(wootMobName, key);
        if (booleanMobMap.containsKey(mapKey))
            return booleanMobMap.get(mapKey);

        return booleanMap.get(key);
    }

    @Override
    public int getInteger(EnumConfigKey key) {

        return integerMap.get(key);
    }

    @Override
    public int getInteger(WootMobName wootMobName, EnumConfigKey key) {

        String mapKey = makeKey(wootMobName, key);
        if (integerMobMap.containsKey(mapKey))
            return integerMobMap.get(mapKey);

        return integerMap.get(key);
    }

    @Override
    public void setBoolean(EnumConfigKey key, boolean v) {

        booleanMap.put(key, v);
    }

    @Override
    public void setBoolean(WootMobName wootMobName, EnumConfigKey key, boolean v) {

        booleanMobMap.put(makeKey(wootMobName, key), v);

    }

    @Override
    public void setInteger(EnumConfigKey key, int v) {

        integerMap.put(key, v);
    }

    @Override
    public void setInteger(WootMobName wootMobName, EnumConfigKey key, int v) {

        integerMobMap.put(makeKey(wootMobName, key), v);
    }

    @Override
    public void addToMobList(WootMobName wootMobName) {

    }

    @Override
    public void addToItemList(String itemName) {

    }

    @Override
    public void addToModItemList(String modName) {

    }

    @Override
    public void addToDragonDrops(EnumEnchantKey enchantKey, String itemName, int stackSize, float chance) {

    }

    @Override
    public void addToInternalModBlacklist(String modName) {

    }

    @Override
    public void addToInternalModItemBlacklist(String modName) {

    }

    @Override
    public void addToInternalMobBlacklist(String mobName) {

    }

    @Override
    public void addToInternalItemBlacklist(String itemName) {

    }

    public void setInteger(WootMobName wootMobName, String key, int v) {

        EnumConfigKey configKey = getKey(key);
        if (configKey != null) {
            integerMap.put(configKey, v);
        }
    }

    private static Map<String, EnumConfigKey> keyMap = new HashMap<>();
    static {
        keyMap.put("SPAWN_TICK", EnumConfigKey.SPAWN_TICKS);
        keyMap.put("SPAWN_XP", EnumConfigKey.SPAWN_XP);
        keyMap.put("DEATH_XP", EnumConfigKey.DEATH_XP);
        keyMap.put("FACTORY_TIER", EnumConfigKey.FACTORY_TIER);
        keyMap.put("KILL_COUNT", EnumConfigKey.KILL_COUNT);
    }

    private @Nullable
    EnumConfigKey getKey(String key) {

        if (keyMap.containsKey(key))
            return keyMap.get(key);

        return null;
    }

    public boolean canCapture(WootMobName wootMobName) {
        return true;
    }

    public boolean canGenerate(WootMobName wootMobName, ItemStack itemStack) {
        return true;
    }

    @Nonnull
    @Override
    public EnumMobFactoryTier getFactoryTier(World world, WootMobName wootMobName) {
        return null;
    }

    @Override
    public int getSpawnCost(World world, WootMobName wootMobName) {
        return 0;
    }

    @Override
    public int getDeathCost(World world, WootMobName wootMobName) {
        return 0;
    }
}
