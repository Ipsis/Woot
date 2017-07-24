package ipsis.woot.plugins.jei.anvil;

import ipsis.woot.plugins.jei.JEIPlugin;
import ipsis.woot.reference.Reference;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class StygianAnvilRecipeHandler implements IRecipeHandler<StygianAnvilRecipeJEI> {

    @Override
    public Class<StygianAnvilRecipeJEI> getRecipeClass() {

        return StygianAnvilRecipeJEI.class;
    }

    @Override
    public String getRecipeCategoryUid(StygianAnvilRecipeJEI recipe) {

        return JEIPlugin.JEI_ANVIL;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(StygianAnvilRecipeJEI recipe) {

        return recipe;
    }

    @Override
    public boolean isRecipeValid(StygianAnvilRecipeJEI recipe) {

        return true;
    }
}
