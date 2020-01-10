package ipsis.woot.modules.infuser;

import ipsis.woot.crafting.InfuserRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;

import javax.annotation.Nonnull;

public class InfuserRecipes {

    public static void load(@Nonnull RecipeManager manager) {

        // Setup the valid items for slot 0,1
        InfuserRecipe.clearValidInputs();
        InfuserRecipe.clearValidAugments();
        InfuserRecipe.clearValidFluids();
        for (IRecipe recipe : manager.getRecipes()) {
            if (recipe instanceof InfuserRecipe) {
                InfuserRecipe dRecipe = (InfuserRecipe) recipe;
                for (ItemStack itemStack : dRecipe.getIngredient().getMatchingStacks())
                    InfuserRecipe.addValidInput(itemStack);

                if (dRecipe.hasAugment()) {
                    for (ItemStack itemStack : dRecipe.getAugment().getMatchingStacks())
                        InfuserRecipe.addValidAugment(itemStack);
                }

                InfuserRecipe.addValidFluid(dRecipe.getFluidInput());
            }
        }
    }
}
