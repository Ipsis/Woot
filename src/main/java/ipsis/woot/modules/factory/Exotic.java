package ipsis.woot.modules.factory;

import ipsis.woot.util.helper.StringHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Locale;

public enum Exotic {
    /**
     * Empty exotic
     */
    NONE,
    /**
     * Fluid ingredient reduction
     */
    EXOTIC_A,
    /**
     * Item ingredient reduction
     */
    EXOTIC_B,
    /**
     * Conatus efficiency
     */
    EXOTIC_C,
    /**
     * Fixed spawn time
     */
    EXOTIC_D,
    /**
     * Fixed mass count
     */
    EXOTIC_E
    ;

    public static Exotic[] VALUES = values();
    public String getName() { return name().toLowerCase(Locale.ROOT); }
    public static int getExoticCount() { return VALUES.length - 1; }

    public static Exotic getExotic(int index) {
        index = MathHelper.clamp(index, 0, VALUES.length);
        return VALUES[index];
    }

    public TranslationTextComponent getTooltip() {
        return new TranslationTextComponent("info.woot.exotic." + getName());
    }

    public ItemStack getItemStack() {
        if (this == EXOTIC_A)
            return new ItemStack(FactorySetup.EXOTIC_A_BLOCK.get());
        else if (this == EXOTIC_B)
            return new ItemStack(FactorySetup.EXOTIC_B_BLOCK.get());
        else if (this == EXOTIC_C)
            return new ItemStack(FactorySetup.EXOTIC_C_BLOCK.get());
        else if (this == EXOTIC_D)
            return new ItemStack(FactorySetup.EXOTIC_D_BLOCK.get());
        else if (this == EXOTIC_E)
            return new ItemStack(FactorySetup.EXOTIC_E_BLOCK.get());

        return ItemStack.EMPTY;
    }

}
