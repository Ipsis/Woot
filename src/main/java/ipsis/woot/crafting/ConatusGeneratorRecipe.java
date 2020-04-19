package ipsis.woot.crafting;

import ipsis.woot.setup.ModDefaults;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

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

    public FluidStack getInputFluid() { return this.inputFluid; }
    public FluidStack getOutputFluid() { return this.outputFluid; }
    public int getEnergy() { return this.energy; }
    public ItemStack getCatalyst() { return this.catalyst; }

    public boolean matches(FluidStack input, ItemStack itemStack) {
        if (input.isEmpty())
            return false;

        if (itemStack.isEmpty() || itemStack.getItem() != catalyst.getItem() || itemStack.getCount() < catalyst.getCount())
            return false;

        return input.isFluidEqual(inputFluid) && input.getAmount() >= inputFluid.getAmount();
    }

    /**
     * Valid inputs
     */
    private static List<ItemStack> validCatalysts = new ArrayList<>();
    public static void clearValidCatalysts() { validCatalysts.clear(); }
    public static void addValidCatalyst(ItemStack itemStack) { validCatalysts.add(itemStack); }
    public static boolean isValidCatalyst(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;

        for (ItemStack i : validCatalysts) {
            if (i.isItemEqual(itemStack))
                return true;
        }
        return false;
    }

    private static List<FluidStack> validInputs = new ArrayList<>();
    public static void clearValidInputs() { validInputs.clear(); }
    public static void addValidInput(FluidStack fluidStack) { validInputs.add(fluidStack); }
    public static boolean isValidInput(FluidStack fluidStack) {
        if (fluidStack.isEmpty())
            return false;

        for (FluidStack f : validInputs) {
            if (f.isFluidEqual(fluidStack))
                return true;
        }
        return false;
    }
}
