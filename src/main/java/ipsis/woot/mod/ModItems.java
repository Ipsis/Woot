package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.debug.ItemDebug;
import ipsis.woot.factory.ItemUpgrade;
import ipsis.woot.tools.ItemHammer;
import ipsis.woot.tools.ItemIntern;
import ipsis.woot.tools.ItemMobShard;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModItems {

    @ObjectHolder((Woot.MODID + ":" + ItemDebug.BASENAME))
    public static final ItemDebug debugItem = null;

    @ObjectHolder((Woot.MODID + ":" + ItemHammer.BASENAME))
    public static final ItemHammer hammerItem = null;

    @ObjectHolder((Woot.MODID + ":" + ItemIntern.BASENAME))
    public static final ItemIntern internItem = null;

    @ObjectHolder((Woot.MODID + ":" + ItemMobShard.BASENAME))
    public static final ItemMobShard mobShardItem = null;

    /**
     * Upgrades
     */
    @ObjectHolder((Woot.MODID + ":" + "upgrade_looting_1"))
    public static final ItemUpgrade upgradeLooting1Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_looting_2"))
    public static final ItemUpgrade upgradeLooting2Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_looting_3"))
    public static final ItemUpgrade upgradeLooting3Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_mass_1"))
    public static final ItemUpgrade upgradeMass1Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_mass_2"))
    public static final ItemUpgrade upgradeMass2Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_mass_3"))
    public static final ItemUpgrade upgradeMass3Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_rate_1"))
    public static final ItemUpgrade upgradeRate1Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_rate_2"))
    public static final ItemUpgrade upgradeRate2Item = null;
    @ObjectHolder((Woot.MODID + ":" + "upgrade_rate_3"))
    public static final ItemUpgrade upgradeRate3Item = null;

    public static @Nonnull ItemStack getItemUpgradeByType(@Nullable ItemUpgrade.UpgradeType upgradeType) {
        if (upgradeType == null)
            return ItemStack.EMPTY;

        ItemStack itemStack = new ItemStack(Items.DIAMOND);
        switch (upgradeType){
            case LOOTING_1: itemStack = new ItemStack(upgradeLooting1Item); break;
            case LOOTING_2: itemStack = new ItemStack(upgradeLooting2Item); break;
            case LOOTING_3: itemStack = new ItemStack(upgradeLooting3Item); break;

            case MASS_1: itemStack =  new ItemStack(upgradeMass1Item); break;
            case MASS_2: itemStack =  new ItemStack(upgradeMass2Item); break;
            case MASS_3: itemStack =  new ItemStack(upgradeMass3Item); break;

            case RATE_1: itemStack =  new ItemStack(upgradeRate1Item); break;
            case RATE_2: itemStack =  new ItemStack(upgradeRate2Item); break;
            case RATE_3: itemStack =  new ItemStack(upgradeRate3Item); break;
        }

        return itemStack;
    }

}
