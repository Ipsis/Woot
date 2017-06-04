package ipsis.woot.tileentity.ng.configuration;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.ng.WootMobName;
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

    void addToMobList(WootMobName wootMobName);
    void addToItemList(String itemName);
    void addToModItemList(String modName);

    void addToDragonDrops(EnumEnchantKey enchantKey, String itemName, int stackSize, float chance);
    void addToInternalModBlacklist(String modName);
    void addToInternalModItemBlacklist(String modName);
    void addToInternalMobBlacklist(String mobName);
    void addToInternalItemBlacklist(String itemName);

    boolean canCapture(WootMobName wootMobName);
    boolean canGenerate(WootMobName wootMobName, ItemStack itemStack);
    @Nonnull EnumMobFactoryTier getFactoryTier(World world, WootMobName wootMobName);
    int getSpawnCost(World world, WootMobName wootMobName);
    int getDeathCost(World world, WootMobName wootMobName);

}
