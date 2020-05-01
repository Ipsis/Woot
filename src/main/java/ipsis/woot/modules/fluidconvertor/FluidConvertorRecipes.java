package ipsis.woot.modules.fluidconvertor;

import ipsis.woot.crafting.FluidConvertorRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class FluidConvertorRecipes {

    private static ArrayList<FluidConvertorRecipe> recipes = new ArrayList<>();

    public static void load(@Nonnull RecipeManager manager) {

        FluidConvertorRecipe.clearValidInputs();
        FluidConvertorRecipe.clearValidCatalysts();
        for (IRecipe recipe : manager.getRecipes()) {
            if (recipe instanceof FluidConvertorRecipe) {
                FluidConvertorRecipe dRecipe = (FluidConvertorRecipe)recipe;
                for (ItemStack itemStack : dRecipe.getCatalyst().getMatchingStacks())
                    FluidConvertorRecipe.addValidCatalyst(itemStack);

                FluidConvertorRecipe.addValidInput(dRecipe.getInputFluid());
            }
        }
    }
}
