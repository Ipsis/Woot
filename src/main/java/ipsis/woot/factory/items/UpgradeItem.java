package ipsis.woot.factory.items;

import ipsis.woot.factory.FactoryUpgrade;
import ipsis.woot.factory.FactoryUpgradeType;
import ipsis.woot.mod.ModItems;
import ipsis.woot.util.WootItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class UpgradeItem extends WootItem {

    final FactoryUpgrade upgrade;

    public UpgradeItem(FactoryUpgrade upgrade) {
        super(new Item.Properties(), upgrade.getName());
        this.upgrade = upgrade;
    }

    public FactoryUpgrade getFactoryUpgrade() { return this.upgrade; }

    public static ItemStack getItemStack(FactoryUpgrade upgrade1) {

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

        if (type == FactoryUpgradeType.EFFICIENCY && level == 1)
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
