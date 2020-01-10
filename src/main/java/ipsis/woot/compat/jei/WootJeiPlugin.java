package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.AnvilRecipe;
import ipsis.woot.crafting.DyeSqueezerRecipe;
import ipsis.woot.crafting.InfuserRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

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
                new InfuserRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new AnvilRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        ClientWorld world = Minecraft.getInstance().world;
        RecipeManager recipeManager = world.getRecipeManager();

        List<IRecipe> dyeRecipes = new ArrayList<>();
        List<IRecipe> anvilRecipes = new ArrayList<>();
        List<IRecipe> infuserRecipes = new ArrayList<>();
        for (IRecipe recipe : recipeManager.getRecipes()) {
            if (recipe instanceof DyeSqueezerRecipe) {
                dyeRecipes.add(recipe);
            } else if (recipe instanceof AnvilRecipe) {
                anvilRecipes.add(recipe);
            } else if (recipe instanceof InfuserRecipe) {
                infuserRecipes.add(recipe);
            }
        }
        registration.addRecipes(dyeRecipes, DyeSqueezerRecipeCategory.UID);
        registration.addRecipes(anvilRecipes, AnvilRecipeCategory.UID);
        registration.addRecipes(infuserRecipes, InfuserRecipeCategory.UID);
    }
}
