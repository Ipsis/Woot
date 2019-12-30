package ipsis.woot.modules.squeezer;

import ipsis.woot.crafting.SqueezerRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class SqueezerRecipes {

    public static void load(@Nonnull RecipeManager manager) {

        // Loaded on server start so need to clear the old ones
        SqueezerRecipe.clearRecipes();
        SqueezerRecipe.addRecipe( DyeMakeup.BLACK, new ItemStack(Items.INK_SAC));
        SqueezerRecipe.addRecipe( DyeMakeup.RED, new ItemStack(Items.RED_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.GREEN, new ItemStack(Items.GREEN_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.BROWN, new ItemStack(Items.COCOA_BEANS));
        SqueezerRecipe.addRecipe( DyeMakeup.BLUE, new ItemStack(Items.LAPIS_LAZULI));
        SqueezerRecipe.addRecipe( DyeMakeup.PURPLE, new ItemStack(Items.PURPLE_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.CYAN, new ItemStack(Items.CYAN_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.LIGHTGRAY, new ItemStack(Items.LIGHT_GRAY_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.GRAY, new ItemStack(Items.GRAY_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.PINK, new ItemStack(Items.PINK_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.LIME, new ItemStack(Items.LIME_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.YELLOW, new ItemStack(Items.YELLOW_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.LIGHTBLUE, new ItemStack(Items.LIGHT_BLUE_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.MAGENTA, new ItemStack(Items.MAGENTA_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.ORANGE, new ItemStack(Items.ORANGE_DYE));
        SqueezerRecipe.addRecipe( DyeMakeup.WHITE, new ItemStack(Items.BONE_MEAL));

        ResourceLocation rs = new ResourceLocation("forge", "dyes");
        Tag<Item> tag = ItemTags.getCollection().get(rs);
        if (tag == null)
            return;

        for (IRecipe recipe : manager.getRecipes()) {
            if (recipe instanceof ShapelessRecipe || recipe instanceof AbstractCookingRecipe) {
                if (recipe.getIngredients().size() == 1) {
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
                                    SqueezerRecipe.addRecipe(dyeMakeup, itemStack.copy());
                                }
                            } else if (recipe instanceof AbstractCookingRecipe) {
                                for (ItemStack itemStack : ((AbstractCookingRecipe)recipe).getIngredients().get(0).getMatchingStacks()) {
                                    SqueezerRecipe.addRecipe(dyeMakeup, itemStack.copy());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
