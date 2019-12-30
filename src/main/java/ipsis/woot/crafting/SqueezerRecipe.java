package ipsis.woot.crafting;

import ipsis.woot.Woot;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SqueezerRecipe {

    public final DyeMakeup output;
    public final WootIngredient input;

    public SqueezerRecipe(DyeMakeup output, ItemStack input) {
        this.output = output;
        this.input = new WootIngredient(input);
    }

    public SqueezerRecipe(DyeMakeup output, ResourceLocation tag) {
        this.output = output;
        this.input = new WootIngredient(tag);
    }

    public static ArrayList<SqueezerRecipe> recipeList = new ArrayList<>();
    public static void addRecipe(DyeMakeup output, ItemStack input) {
        SqueezerRecipe recipe = new SqueezerRecipe(output, input);
        recipeList.add(recipe);
    }

    public static void addRecipe(DyeMakeup output, ResourceLocation tag) {
        SqueezerRecipe recipe = new SqueezerRecipe(output, tag);
        recipeList.add(recipe);
    }


    public static void clearRecipes() {
        recipeList = new ArrayList<>();
    }

    public static @Nullable
    SqueezerRecipe findRecipe(ItemStack input) {
        if (input != null && !input.isEmpty()) {
            for (SqueezerRecipe recipe : recipeList) {
                if (recipe.input.isSameIngredient(input))
                    return recipe;
            }
        }

        return null;
    }
}
