package ipsis.woot.plugins.jei.anvil;

import ipsis.woot.plugins.jei.JEIPlugin;
import ipsis.woot.reference.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class StygianAnvilRecipeCategory implements IRecipeCategory {

    private static final int BASE_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int INPUT_SLOT_START = 2;

    private static ResourceLocation background_loc = new ResourceLocation(Reference.MOD_ID, "gui/jei/stygiananvil.png");
    private final IDrawable background;

    public StygianAnvilRecipeCategory(IGuiHelper guiHelper) {

        background = guiHelper.createDrawable(background_loc, 0, 0, 109, 60);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {

        if (!(recipeWrapper instanceof StygianAnvilRecipeJEI))
            return;

        recipeLayout.getItemStacks().init(BASE_SLOT, true, 18, 38);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 87, 28);

        recipeLayout.getItemStacks().set(BASE_SLOT, ingredients.getInputs(ItemStack.class).get(0));
        recipeLayout.getItemStacks().set(OUTPUT_SLOT, ingredients.getOutputs(ItemStack.class).get(0));

        int c = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++) {
                // First input is the base item
                if (c < ingredients.getInputs(ItemStack.class).size() - 1) {
                    recipeLayout.getItemStacks().init(INPUT_SLOT_START + c, false, x * 18, y * 18);
                    recipeLayout.getItemStacks().set(INPUT_SLOT_START + c, ingredients.getInputs(ItemStack.class).get(1 + c));
                    c++;
                }
            }
        }
    }

    @Override
    public String getUid() {

        return JEIPlugin.JEI_ANVIL;
    }

    @Override
    public String getTitle() {

        return "Stygian Anvil";
    }

    @Override
    public IDrawable getBackground() {

        return background;
    }

    @Override
    public String getModName() {

        return Reference.MOD_NAME;
    }
}
