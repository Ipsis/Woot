package ipsis.woot.crafting;

import ipsis.woot.fluilds.FluidSetup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class EnchantSqueezerRecipe {

    ItemStack itemStack;
    int enchantFluidAmount;

    public EnchantSqueezerRecipe(ItemStack itemStack, int enchantFluidAmount) {
        this.itemStack = itemStack;
        this.enchantFluidAmount = enchantFluidAmount;
    }

    public FluidStack getOutput() { return new FluidStack(FluidSetup.ENCHANT_FLUID.get(), enchantFluidAmount); }
    public ItemStack getInput() { return itemStack.copy(); }
}
