package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.DyeSqueezerRecipe;
import ipsis.woot.crafting.InfuserRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class WootJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Woot.MODID, "main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new DyeSqueezerRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new InfuserRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        registration.addRecipes(DyeSqueezerRecipe.recipeList, DyeSqueezerRecipeCategory.UID);
        registration.addRecipes(InfuserRecipe.recipeList, InfuserRecipeCategory.UID);
    }
}
