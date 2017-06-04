package ipsis.woot.tileentity.ng.configuration;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.ng.BlacklistComparator;
import ipsis.woot.tileentity.ng.WootMobName;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WootConfigurationManager implements IWootConfiguration {

    /**
     * Internal Configuration
     */
    private List<String> internalModCaptureBlacklist = new ArrayList<>();
    private List<String> internalMobCaptureBlacklist = new ArrayList<>();
    private List<String> internalModItemBlacklist = new ArrayList<>();
    private List<String> internalItemBlacklist = new ArrayList<>();

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
        return booleanMap.get(key);
    }

    @Override
    public boolean getBoolean(WootMobName wootMobName, EnumConfigKey key) {

        String k = makeKey(wootMobName, key);
        if (booleanMobMap.containsKey(k))
            return booleanMobMap.get(k);

        return booleanMap.get(key);
    }

    @Override
    public int getInteger(EnumConfigKey key) {
        return integerMap.get(key);
    }

    @Override
    public int getInteger(WootMobName wootMobName, EnumConfigKey key) {

        String k = makeKey(wootMobName, key);
        if (integerMobMap.containsKey(k))
            return integerMobMap.get(k);

        return integerMap.get(key);
    }

    @Override
    public void setBoolean(EnumConfigKey key, boolean v) {
        booleanMap.put(key, v);
    }

    @Override
    public void setBoolean(WootMobName wootMobName, EnumConfigKey key, boolean v) {

        if (key.canMobOverride()) {
            String k = makeKey(wootMobName, key);
            booleanMobMap.put(k, v);
        }
    }

    @Override
    public void setInteger(EnumConfigKey key, int v) {
        integerMap.put(key, v);
    }

    @Override
    public void setInteger(WootMobName wootMobName, EnumConfigKey key, int v) {

        if (key.canMobOverride()) {
            String k = makeKey(wootMobName, key);
            integerMobMap.put(k, v);
        }
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

        internalMobCaptureBlacklist.add(modName);
    }

    @Override
    public void addToInternalModItemBlacklist(String modName) {

        internalModItemBlacklist.add(modName);
    }

    @Override
    public void addToInternalMobBlacklist(String mobName) {

        internalModCaptureBlacklist.add(mobName);
    }

    @Override
    public void addToInternalItemBlacklist(String itemName) {

        internalItemBlacklist.add(itemName);
    }

    @Override
    public boolean canCapture(WootMobName wootMobName) {

        // Internal mod blacklist
        for (String modName : internalModCaptureBlacklist)
            if (BlacklistComparator.isSameMod(wootMobName, modName))
                return false;

        // Internal mob blacklist
        for (String mobName : internalMobCaptureBlacklist)
            if (BlacklistComparator.isSameMob(wootMobName, mobName))
                return false;

        // TODO user config
        if (getBoolean(EnumConfigKey.MOB_WHITELIST)) {

        } else {

        }

        return true;
    }

    @Override
    public boolean canGenerate(WootMobName wootMobName, ItemStack itemStack) {

        String name = itemStack.getUnlocalizedName();

        // Internal mod blacklist
        for (String modName : internalModItemBlacklist)
            if (BlacklistComparator.isSameMod(itemStack, modName))
                return false;

        // Internal item blacklist
        for (String itemName : internalItemBlacklist)
            if (BlacklistComparator.isSameItem(itemStack, itemName))
            if (name.equalsIgnoreCase(itemName))
                return false;

        // TODO user config

        return false;
    }

    @Nonnull
    @Override
    public EnumMobFactoryTier getFactoryTier(World world, WootMobName wootMobName) {

        EnumMobFactoryTier tier;
        String key = makeKey(wootMobName, EnumConfigKey.FACTORY_TIER);
        if (integerMobMap.containsKey(key)) {
            tier = EnumMobFactoryTier.getTier(integerMobMap.get(key));
        } else {
            int cost = getSpawnCost(world, wootMobName);
            if (cost >= getInteger(wootMobName, EnumConfigKey.T4_XP_MAX))
                tier = EnumMobFactoryTier.TIER_FOUR;
            else if (cost >= getInteger(wootMobName, EnumConfigKey.T3_XP_MAX))
                tier = EnumMobFactoryTier.TIER_THREE;
            else if (cost >= getInteger(wootMobName, EnumConfigKey.T2_XP_MAX))
                tier = EnumMobFactoryTier.TIER_TWO;
            else
                tier = EnumMobFactoryTier.TIER_ONE;
        }

        return tier;
    }

    @Override
    public int getSpawnCost(World world, WootMobName wootMobName) {

        String key = makeKey(wootMobName, EnumConfigKey.SPAWN_XP);
        if (integerMobMap.containsKey(key))
            return integerMobMap.get(key);

        return Woot.mobCosting.getMobSpawnCost(world, wootMobName);
    }

    @Override
    public int getDeathCost(World world, WootMobName wootMobName) {

        String key = makeKey(wootMobName, EnumConfigKey.DEATH_XP);
        if (integerMobMap.containsKey(key))
            return integerMobMap.get(key);

        /**
         * If no local override then death == spawn
         */
        return Woot.mobCosting.getMobSpawnCost(world, wootMobName);
    }


}
