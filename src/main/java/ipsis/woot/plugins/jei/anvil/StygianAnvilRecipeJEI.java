package ipsis.woot.plugins.jei.anvil;

import ipsis.woot.crafting.IAnvilRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StygianAnvilRecipeJEI extends BlankRecipeWrapper {

    private IAnvilRecipe recipe;

    public StygianAnvilRecipeJEI(IAnvilRecipe recipe) {

        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setOutput(ItemStack.class, recipe.getCopyOutput());

        List<ItemStack> inputs = new ArrayList<>();
        inputs.add(0, recipe.getBaseItem());
        inputs.addAll(recipe.getInputs());
        ingredients.setInputs(ItemStack.class, inputs);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }
}
