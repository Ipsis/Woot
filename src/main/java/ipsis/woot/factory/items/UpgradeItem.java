package ipsis.woot.factory.items;

import ipsis.woot.factory.FactoryUpgrade;
import ipsis.woot.factory.FactoryUpgradeType;
import ipsis.woot.mod.ModItems;
import ipsis.woot.util.WootItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class UpgradeItem extends WootItem {

    public static final String CAPACITY_1_REGNAME = "capacity_1";
    public static final String CAPACITY_2_REGNAME = "capacity_2";
    public static final String CAPACITY_3_REGNAME = "capacity_3";
    public static final String EFFICIENCY_1_REGNAME = "efficiency_1";
    public static final String EFFICIENCY_2_REGNAME = "efficiency_2";
    public static final String EFFICIENCY_3_REGNAME = "efficiency_3";
    public static final String LOOTING_1_REGNAME = "looting_1";
    public static final String LOOTING_2_REGNAME = "looting_2";
    public static final String LOOTING_3_REGNAME = "looting_3";
    public static final String MASS_1_REGNAME = "mass_1";
    public static final String MASS_2_REGNAME = "mass_2";
    public static final String MASS_3_REGNAME = "mass_3";
    public static final String RATE_1_REGNAME = "rate_1";
    public static final String RATE_2_REGNAME = "rate_2";
    public static final String RATE_3_REGNAME = "rate_3";
    public static final String XP_1_REGNAME = "xp_1";
    public static final String XP_2_REGNAME = "xp_2";
    public static final String XP_3_REGNAME = "xp_3";

    final FactoryUpgrade upgrade;

    public UpgradeItem(FactoryUpgrade upgrade, String name) {
        super(new Item.Properties(), name);
        this.upgrade = upgrade;
    }

    public FactoryUpgrade getFactoryUpgrade() { return this.upgrade; }

    public static ItemStack getItemStack(FactoryUpgrade upgrade1) {

        if (upgrade1 == FactoryUpgrade.CAPACITY_1)
            return new ItemStack(ModItems.CAPACITY_1_ITEM);
        if (upgrade1 == FactoryUpgrade.CAPACITY_2)
            return new ItemStack(ModItems.CAPACITY_2_ITEM);
        if (upgrade1 == FactoryUpgrade.CAPACITY_3)
            return new ItemStack(ModItems.CAPACITY_3_ITEM);
        if (upgrade1 == FactoryUpgrade.EFFICIENCY_1)
            return new ItemStack(ModItems.EFFICIENCY_1_ITEM);
        else if (upgrade1 == FactoryUpgrade.EFFICIENCY_2)
            return new ItemStack(ModItems.EFFICIENCY_2_ITEM);
        else if (upgrade1 == FactoryUpgrade.EFFICIENCY_3)
            return new ItemStack(ModItems.EFFICIENCY_3_ITEM);
        else if (upgrade1 == FactoryUpgrade.LOOTING_1)
            return new ItemStack(ModItems.LOOTING_1_ITEM);
        else if (upgrade1 == FactoryUpgrade.LOOTING_2)
            return new ItemStack(ModItems.LOOTING_2_ITEM);
        else if (upgrade1 == FactoryUpgrade.LOOTING_3)
            return new ItemStack(ModItems.LOOTING_3_ITEM);
        else if (upgrade1 == FactoryUpgrade.MASS_1)
            return new ItemStack(ModItems.MASS_1_ITEM);
        else if (upgrade1 == FactoryUpgrade.MASS_2)
            return new ItemStack(ModItems.MASS_2_ITEM);
        else if (upgrade1 == FactoryUpgrade.MASS_3)
            return new ItemStack(ModItems.MASS_3_ITEM);
        else if (upgrade1 == FactoryUpgrade.RATE_1)
            return new ItemStack(ModItems.RATE_1_ITEM);
        else if (upgrade1 == FactoryUpgrade.RATE_2)
            return new ItemStack(ModItems.RATE_2_ITEM);
        else if (upgrade1 == FactoryUpgrade.RATE_3)
            return new ItemStack(ModItems.RATE_3_ITEM);
        else if (upgrade1 == FactoryUpgrade.XP_1)
            return new ItemStack(ModItems.XP_1_ITEM);
        else if (upgrade1 == FactoryUpgrade.XP_2)
            return new ItemStack(ModItems.XP_2_ITEM);
        else if (upgrade1 == FactoryUpgrade.XP_3)
            return new ItemStack(ModItems.XP_3_ITEM);

        return ItemStack.EMPTY;
    }

    public static ItemStack getItemStack(FactoryUpgradeType type, int level) {

        if (type == FactoryUpgradeType.CAPACITY && level == 1)
            return new ItemStack(ModItems.CAPACITY_1_ITEM);
        if (type == FactoryUpgradeType.CAPACITY && level == 2)
            return new ItemStack(ModItems.CAPACITY_2_ITEM);
        if (type == FactoryUpgradeType.CAPACITY && level == 3)
            return new ItemStack(ModItems.CAPACITY_3_ITEM);
        else if (type == FactoryUpgradeType.EFFICIENCY && level == 1)
            return new ItemStack(ModItems.EFFICIENCY_1_ITEM);
        else if (type == FactoryUpgradeType.EFFICIENCY && level == 2)
            return new ItemStack(ModItems.EFFICIENCY_2_ITEM);
        else if (type == FactoryUpgradeType.EFFICIENCY && level == 3)
            return new ItemStack(ModItems.EFFICIENCY_3_ITEM);
        else if (type == FactoryUpgradeType.LOOTING && level == 1)
            return new ItemStack(ModItems.LOOTING_1_ITEM);
        else if (type == FactoryUpgradeType.LOOTING && level == 2)
            return new ItemStack(ModItems.LOOTING_2_ITEM);
        else if (type == FactoryUpgradeType.LOOTING && level == 3)
            return new ItemStack(ModItems.LOOTING_3_ITEM);
        else if (type == FactoryUpgradeType.MASS && level == 1)
            return new ItemStack(ModItems.MASS_1_ITEM);
        else if (type == FactoryUpgradeType.MASS && level == 2)
            return new ItemStack(ModItems.MASS_2_ITEM);
        else if (type == FactoryUpgradeType.MASS && level == 3)
            return new ItemStack(ModItems.MASS_3_ITEM);
        else if (type == FactoryUpgradeType.RATE && level == 1)
            return new ItemStack(ModItems.RATE_1_ITEM);
        else if (type == FactoryUpgradeType.RATE && level == 2)
            return new ItemStack(ModItems.RATE_2_ITEM);
        else if (type == FactoryUpgradeType.RATE && level == 3)
            return new ItemStack(ModItems.RATE_3_ITEM);
        else if (type == FactoryUpgradeType.XP && level == 1)
            return new ItemStack(ModItems.XP_1_ITEM);
        else if (type == FactoryUpgradeType.XP && level == 2)
            return new ItemStack(ModItems.XP_2_ITEM);
        else if (type == FactoryUpgradeType.XP && level == 3)
            return new ItemStack(ModItems.XP_3_ITEM);

        return ItemStack.EMPTY;
    }
}
