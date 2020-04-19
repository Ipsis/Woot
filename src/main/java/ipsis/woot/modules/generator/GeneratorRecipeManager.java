package ipsis.woot.modules.generator;

import ipsis.woot.crafting.ConatusGeneratorRecipe;
import ipsis.woot.fluilds.FluidSetup;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class GeneratorRecipeManager {

    private static ArrayList<ConatusGeneratorRecipe> recipes = new ArrayList<>();

    public static void load(@Nonnull RecipeManager manager) {
        recipes.clear();
        ConatusGeneratorRecipe.clearValidInputs();
        ConatusGeneratorRecipe.clearValidCatalysts();

        recipes.add(new ConatusGeneratorRecipe(
                new FluidStack(Fluids.WATER, 1000),
                new ItemStack(Items.GUNPOWDER),
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1000)));
        recipes.add(new ConatusGeneratorRecipe(
                new FluidStack(Fluids.LAVA, 1000),
                new ItemStack(Items.BLAZE_POWDER),
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1000), 5000));

        for (ConatusGeneratorRecipe recipe : recipes) {
            ConatusGeneratorRecipe.addValidInput(recipe.getInputFluid());
            if (recipe.hasCatalyst())
                ConatusGeneratorRecipe.addValidCatalyst(recipe.getCatalyst());
        }
    }

    public static List<ConatusGeneratorRecipe> getRecipes() { return recipes; }
}
