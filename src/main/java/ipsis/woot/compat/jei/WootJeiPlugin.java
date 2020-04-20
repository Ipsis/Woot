package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.*;
import ipsis.woot.modules.generator.client.ConatusGeneratorScreen;
import ipsis.woot.modules.infuser.client.InfuserScreen;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.modules.squeezer.client.DyeSqueezerScreen;
import ipsis.woot.modules.squeezer.client.EnchantSqueezerScreen;
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

    public static int maxInfuserRecipeMb = 1000;

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
                new EnchantSqueezerRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new ConatusGeneratorRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        ClientWorld world = Minecraft.getInstance().world;
        RecipeManager recipeManager = world.getRecipeManager();

        List<IRecipe> dyeRecipes = new ArrayList<>();
        List<IRecipe> anvilRecipes = new ArrayList<>();
        List<IRecipe> infuserRecipes = new ArrayList<>();
        List<IRecipe> conatusGenRecipes = new ArrayList<>();
        for (IRecipe recipe : recipeManager.getRecipes()) {
            if (recipe instanceof DyeSqueezerRecipe) {
                dyeRecipes.add(recipe);
            } else if (recipe instanceof AnvilRecipe) {
                anvilRecipes.add(recipe);
            } else if (recipe instanceof InfuserRecipe) {
                InfuserRecipe r = (InfuserRecipe)recipe;
                infuserRecipes.add(r);
                if (r.getFluidInput().getAmount() > maxInfuserRecipeMb)
                    maxInfuserRecipeMb = r.getFluidInput().getAmount();
            } else if (recipe instanceof ConatusGeneratorRecipe) {
                conatusGenRecipes.add(recipe);
            }
        }
        registration.addRecipes(dyeRecipes, DyeSqueezerRecipeCategory.UID);
        registration.addRecipes(anvilRecipes, AnvilRecipeCategory.UID);
        registration.addRecipes(infuserRecipes, InfuserRecipeCategory.UID);
        registration.addRecipes(conatusGenRecipes, ConatusGeneratorRecipeCategory.UID);

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
        registration.addRecipeClickArea(DyeSqueezerScreen.class, 60, 39, 18, 18, DyeSqueezerRecipeCategory.UID);
        registration.addRecipeClickArea(EnchantSqueezerScreen.class, 101, 32, 50, 36, EnchantSqueezerRecipeCategory.UID);
        registration.addRecipeClickArea(ConatusGeneratorScreen.class, 74, 40, 70, 26, ConatusGeneratorRecipeCategory.UID);

    }
}
