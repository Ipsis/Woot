package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.AnvilRecipe;
import ipsis.woot.crafting.DyeSqueezerRecipe;
import ipsis.woot.crafting.EnchantSqueezerRecipe;
import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.modules.infuser.client.InfuserScreen;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

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
                new AnvilRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new EnchantSqueezerRecipeCategory(registration.getJeiHelpers().getGuiHelper())
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

        /**
         * Enchanted books
         */
        List<EnchantSqueezerRecipe> books = new ArrayList<>();
        for (Enchantment e : ForgeRegistries.ENCHANTMENTS) {
            for (int i = e.getMinLevel(); i <= e.getMaxLevel(); i++) {
                ItemStack itemStack = new ItemStack(Items.ENCHANTED_BOOK);
                itemStack.addEnchantment(e, i);
                books.add(new EnchantSqueezerRecipe(itemStack, SqueezerConfiguration.getEnchantFluidAmount(i)));
            }
        }
        registration.addRecipes(books, EnchantSqueezerRecipeCategory.UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(InfuserScreen.class, 84, 39, 30, 18, InfuserRecipeCategory.UID);
    }
}
