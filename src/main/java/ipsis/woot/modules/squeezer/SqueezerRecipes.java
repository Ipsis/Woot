package ipsis.woot.modules.squeezer;

import net.minecraft.item.crafting.RecipeManager;

import javax.annotation.Nonnull;

public class SqueezerRecipes {

    public static void load(@Nonnull RecipeManager manager) {

        /*
        // Loaded on server start so need to clear the old ones
        DyeSqueezerRecipe.clearRecipes();

        // Map Forge dye tag to relevant dye
        for (DyeMakeup d : DyeMakeup.VALUES)
            DyeSqueezerRecipe.addRecipe(d, d.getForgeTag());

        ResourceLocation rs = new ResourceLocation("forge", "dyes");
        Tag<Item> tag = ItemTags.getCollection().get(rs);
        if (tag == null)
            return;

        // Add all single item shapeless and smelting that generate dyes
        for (IRecipe recipe : manager.getRecipes()) {
            if (recipe instanceof ShapelessRecipe || recipe instanceof AbstractCookingRecipe) {
                if (recipe.getIngredients().size() == 1) {
                    // TODO stop duplicates from dye tag inputs
                    if (tag.contains(recipe.getRecipeOutput().getItem())) {
                        // Output item is a dye so add it to the squeezer
                        DyeMakeup dyeMakeup = null;
                        for (DyeMakeup d : DyeMakeup.VALUES) {
                            if (recipe.getRecipeOutput().getItem().getTags().contains(d.getForgeTag())) {
                                dyeMakeup = d;
                                break;
                            }
                        }

                        if (dyeMakeup != null) {
                            if (recipe instanceof ShapelessRecipe) {
                                for (ItemStack itemStack : ((ShapelessRecipe)recipe).getIngredients().get(0).getMatchingStacks()) {
                                    DyeSqueezerRecipe.addRecipe(dyeMakeup, itemStack.copy());
                                }
                            } else if (recipe instanceof AbstractCookingRecipe) {
                                for (ItemStack itemStack : ((AbstractCookingRecipe)recipe).getIngredients().get(0).getMatchingStacks()) {
                                    DyeSqueezerRecipe.addRecipe(dyeMakeup, itemStack.copy());
                                }
                            }
                        }
                    }
                }
            }
        } */
    }
}
