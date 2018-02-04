package ipsis.woot.plugins.jei.anvil;

import ipsis.woot.crafting.IAnvilRecipe;
import ipsis.woot.item.ItemEnderShard;
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

        ItemStack baseItem = recipe.getBaseItem().copy();
        if (ItemEnderShard.isEnderShard(baseItem))
            ItemEnderShard.setJEIEnderShared(baseItem);

        inputs.add(0, baseItem);
        inputs.addAll(recipe.getInputs());
        ingredients.setInputs(ItemStack.class, inputs);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }
}
