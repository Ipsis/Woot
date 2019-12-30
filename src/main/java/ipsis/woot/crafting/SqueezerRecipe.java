package ipsis.woot.crafting;

import ipsis.woot.Woot;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SqueezerRecipe {

    public final DyeMakeup output;
    public final ItemStack input;

    public SqueezerRecipe(DyeMakeup output, ItemStack input) {
        this.output = output;
        this.input = input;
    }

    public static ArrayList<SqueezerRecipe> recipeList = new ArrayList<>();
    public static void addRecipe(DyeMakeup output, ItemStack input) {
        SqueezerRecipe recipe = new SqueezerRecipe(output, input);
        if (recipe.input != null && !recipe.input.isEmpty()) {
            Woot.LOGGER.info("SqueezerRecipe: addRecipe {}", input.getTranslationKey());
            recipeList.add(recipe);
        }
    }

    public static void clearRecipes() {
        recipeList = new ArrayList<>();
    }

    public static @Nullable
    SqueezerRecipe findRecipe(ItemStack input) {
        if (!input.isEmpty()) {
            for (SqueezerRecipe recipe : recipeList) {
                if (recipe.input.isItemEqual(input))
                    return recipe;
            }
        }

        return null;
    }
}
