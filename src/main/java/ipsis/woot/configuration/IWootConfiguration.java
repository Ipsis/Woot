package ipsis.woot.configuration;

import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IWootConfiguration {

    boolean getBoolean(EnumConfigKey key);
    boolean getBoolean(WootMobName wootMobName, EnumConfigKey key);

    int getInteger(EnumConfigKey key);
    int getInteger(WootMobName wootMobName, EnumConfigKey key);

    void setBoolean(EnumConfigKey key, boolean v);
    void setBoolean(WootMobName wootMobName, EnumConfigKey key, boolean v);

    void setInteger(EnumConfigKey key, int v);
    void setInteger(WootMobName wootMobName, EnumConfigKey key, int v);

    void addToDragonDrops(EnumEnchantKey enchantKey, String itemName, int stackSize, float chance);

    @Nonnull EnumMobFactoryTier getFactoryTier(World world, WootMobName wootMobName);
    int getSpawnCost(World world, WootMobName wootMobName);
    int getDeathCost(World world, WootMobName wootMobName);

}
