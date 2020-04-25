package ipsis.woot.modules.factory.items;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.Perk;
import ipsis.woot.modules.factory.PerkType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PerkItem extends Item {

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

    final Perk perk;

    public PerkItem(Perk perk) {
        super(new Item.Properties().group(Woot.setup.getCreativeTab()));
        this.perk = perk;
    }

    public Perk getFactoryUpgrade() { return this.perk; }

    public static ItemStack getItemStack(Perk perk) {

        if (perk == Perk.EFFICIENCY_1)
            return new ItemStack(FactorySetup.EFFICIENCY_1_ITEM.get());
        else if (perk == Perk.EFFICIENCY_2)
            return new ItemStack(FactorySetup.EFFICIENCY_2_ITEM.get());
        else if (perk == Perk.EFFICIENCY_3)
            return new ItemStack(FactorySetup.EFFICIENCY_3_ITEM.get());
        else if (perk == Perk.LOOTING_1)
            return new ItemStack(FactorySetup.LOOTING_1_ITEM.get());
        else if (perk == Perk.LOOTING_2)
            return new ItemStack(FactorySetup.LOOTING_2_ITEM.get());
        else if (perk == Perk.LOOTING_3)
            return new ItemStack(FactorySetup.LOOTING_3_ITEM.get());
        else if (perk == Perk.MASS_1)
            return new ItemStack(FactorySetup.MASS_1_ITEM.get());
        else if (perk == Perk.MASS_2)
            return new ItemStack(FactorySetup.MASS_2_ITEM.get());
        else if (perk == Perk.MASS_3)
            return new ItemStack(FactorySetup.MASS_3_ITEM.get());
        else if (perk == Perk.RATE_1)
            return new ItemStack(FactorySetup.RATE_1_ITEM.get());
        else if (perk == Perk.RATE_2)
            return new ItemStack(FactorySetup.RATE_2_ITEM.get());
        else if (perk == Perk.RATE_3)
            return new ItemStack(FactorySetup.RATE_3_ITEM.get());
        else if (perk == Perk.XP_1)
            return new ItemStack(FactorySetup.XP_1_ITEM.get());
        else if (perk == Perk.XP_2)
            return new ItemStack(FactorySetup.XP_2_ITEM.get());
        else if (perk == Perk.XP_3)
            return new ItemStack(FactorySetup.XP_3_ITEM.get());

        return ItemStack.EMPTY;
    }

    public static ItemStack getItemStack(PerkType type, int level) {

        if (type == PerkType.EFFICIENCY && level == 1)
            return new ItemStack(FactorySetup.EFFICIENCY_1_ITEM.get());
        else if (type == PerkType.EFFICIENCY && level == 2)
            return new ItemStack(FactorySetup.EFFICIENCY_2_ITEM.get());
        else if (type == PerkType.EFFICIENCY && level == 3)
            return new ItemStack(FactorySetup.EFFICIENCY_3_ITEM.get());
        else if (type == PerkType.LOOTING && level == 1)
            return new ItemStack(FactorySetup.LOOTING_1_ITEM.get());
        else if (type == PerkType.LOOTING && level == 2)
            return new ItemStack(FactorySetup.LOOTING_2_ITEM.get());
        else if (type == PerkType.LOOTING && level == 3)
            return new ItemStack(FactorySetup.LOOTING_3_ITEM.get());
        else if (type == PerkType.MASS && level == 1)
            return new ItemStack(FactorySetup.MASS_1_ITEM.get());
        else if (type == PerkType.MASS && level == 2)
            return new ItemStack(FactorySetup.MASS_2_ITEM.get());
        else if (type == PerkType.MASS && level == 3)
            return new ItemStack(FactorySetup.MASS_3_ITEM.get());
        else if (type == PerkType.RATE && level == 1)
            return new ItemStack(FactorySetup.RATE_1_ITEM.get());
        else if (type == PerkType.RATE && level == 2)
            return new ItemStack(FactorySetup.RATE_2_ITEM.get());
        else if (type == PerkType.RATE && level == 3)
            return new ItemStack(FactorySetup.RATE_3_ITEM.get());
        else if (type == PerkType.XP && level == 1)
            return new ItemStack(FactorySetup.XP_1_ITEM.get());
        else if (type == PerkType.XP && level == 2)
            return new ItemStack(FactorySetup.XP_2_ITEM.get());
        else if (type == PerkType.XP && level == 3)
            return new ItemStack(FactorySetup.XP_3_ITEM.get());

        return ItemStack.EMPTY;
    }
}
