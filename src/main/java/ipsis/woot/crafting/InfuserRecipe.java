package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class InfuserRecipe {
    private ItemStack input = ItemStack.EMPTY;
    private FluidStack inputFluid;
    private ItemStack output = ItemStack.EMPTY;

    public InfuserRecipe(ItemStack itemStack, FluidStack fluidStack, ItemStack output) {
        this.input = itemStack.copy();
        this.inputFluid = fluidStack.copy();
        this.output = output.copy();
    }

    public boolean isInput(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty())
            return false;

        if (input.isItemEqual(itemStack))
            return true;

        return false;
    }

    public boolean isInputFluid(FluidStack fluidStack) {
        if (fluidStack == null || fluidStack.isEmpty())
            return false;

        return inputFluid.isFluidEqual(fluidStack);
    }

    public ItemStack getOutput() { return this.output; }
    public FluidStack getInputFluid() { return this.inputFluid; }

}
