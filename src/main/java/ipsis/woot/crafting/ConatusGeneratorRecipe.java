package ipsis.woot.crafting;

import ipsis.woot.setup.ModDefaults;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ConatusGeneratorRecipe {

    private final FluidStack inputFluid;
    private final ItemStack catalyst;
    private final FluidStack outputFluid;
    private final int energy;

    public ConatusGeneratorRecipe(FluidStack inputFluid, ItemStack catalyst, FluidStack outputFluid) {
       this(inputFluid, catalyst, outputFluid, ModDefaults.Generator.CONATUS_GEN_RECIPE_ENERGY_DEF);
    }

    public ConatusGeneratorRecipe(FluidStack inputFluid, ItemStack catalyst, FluidStack outputFluid, int energy) {
        this.inputFluid = inputFluid.copy();
        this.outputFluid = outputFluid.copy();
        this.catalyst = catalyst.copy();
        this.energy = energy;
    }

    public boolean hasCatalyst() { return !catalyst.isEmpty(); }
    public FluidStack getInputFluid() { return this.inputFluid; }
    public FluidStack getOutputFluid() { return this.outputFluid; }
    public int getEnergy() { return this.energy; }
    public ItemStack getCatalyst() { return this.catalyst; }

    public boolean matches(FluidStack input, ItemStack itemStack) {
        if (input.isEmpty())
            return false;

        if (hasCatalyst()) {
            if (itemStack.isEmpty() || itemStack.getItem() != catalyst.getItem() || itemStack.getCount() < catalyst.getCount())
                return false;
        }

        return input.isFluidEqual(inputFluid) && input.getAmount() >= inputFluid.getAmount();
    }

}
