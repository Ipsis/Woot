package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.*;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.fluidconvertor.client.FluidConvertorScreen;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.infuser.client.InfuserScreen;
import ipsis.woot.modules.layout.LayoutSetup;

import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.modules.squeezer.client.DyeSqueezerScreen;
import ipsis.woot.modules.squeezer.client.EnchantSqueezerScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JeiPlugin
public class WootJeiPlugin implements IModPlugin {

    public static int maxInfuserRecipeMb = 1;
    public static int maxFluidConvRecipeInputMb = 1;
    public static int maxFluidConvRecipeOutputMb = 1;
    public static int maxEnchantRecipeMb = 1;

    @Nullable public static IJeiRuntime jeiRuntime;

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
                new FluidConvertorRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        ClientWorld world = Minecraft.getInstance().level;
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
            } else if (recipe instanceof FluidConvertorRecipe) {
                FluidConvertorRecipe r = (FluidConvertorRecipe)recipe;
                if (r.getInputFluid().getAmount() > maxFluidConvRecipeInputMb)
                    maxFluidConvRecipeInputMb = r.getInputFluid().getAmount();
                if (r.getOutput().getAmount() > maxFluidConvRecipeOutputMb)
                    maxFluidConvRecipeOutputMb = r.getOutput().getAmount();
                conatusGenRecipes.add(recipe);
            }
        }
        registration.addRecipes(dyeRecipes, DyeSqueezerRecipeCategory.UID);
        registration.addRecipes(anvilRecipes, AnvilRecipeCategory.UID);
        registration.addRecipes(infuserRecipes, InfuserRecipeCategory.UID);
        registration.addRecipes(conatusGenRecipes, FluidConvertorRecipeCategory.UID);

        Woot.setup.getLogger().debug("registerRecipes: infuser {} conv {} {}",
                maxInfuserRecipeMb, maxFluidConvRecipeInputMb, maxFluidConvRecipeOutputMb);

        /**
         * Enchanted books
         */
        List<EnchantSqueezerRecipe> books = new ArrayList<>();
        for (Enchantment e : ForgeRegistries.ENCHANTMENTS) {
            for (int i = e.getMinLevel(); i <= e.getMaxLevel(); i++) {
                ItemStack itemStack = new ItemStack(Items.ENCHANTED_BOOK);
                itemStack.enchant(e, i);
                books.add(new EnchantSqueezerRecipe(itemStack, SqueezerConfiguration.getEnchantFluidAmount(i), SqueezerConfiguration.getEnchantEnergy(i)));
                if (SqueezerConfiguration.getEnchantFluidAmount(i) > maxEnchantRecipeMb)
                    maxEnchantRecipeMb = SqueezerConfiguration.getEnchantFluidAmount(i);
            }
        }
        registration.addRecipes(books, EnchantSqueezerRecipeCategory.UID);

        registration.addIngredientInfo(
                Arrays.asList(
                    new ItemStack(GenericSetup.T1_SHARD_ITEM.get()),
                    new ItemStack(GenericSetup.T2_SHARD_ITEM.get()),
                    new ItemStack(GenericSetup.T3_SHARD_ITEM.get())
                    ),
                VanillaTypes.ITEM, "jei.woot.shard");
        registration.addIngredientInfo(
                new ItemStack(AnvilSetup.ANVIL_BLOCK.get()),
                VanillaTypes.ITEM,
                "jei.woot.anvil.0",
                "jei.woot.anvil.1",
                "jei.woot.anvil.2",
                "jei.woot.anvil.3"
        );
        registration.addIngredientInfo(
                new ItemStack(LayoutSetup.INTERN_ITEM.get()),
                VanillaTypes.ITEM,
                "jei.woot.intern.0",
                "jei.woot.intern.1"
        );
        registration.addIngredientInfo(
                new ItemStack(FactorySetup.MOB_SHARD_ITEM.get()),
                VanillaTypes.ITEM,
                "jei.woot.mob_shard.0",
                "jei.woot.mob_shard.1",
                "jei.woot.mob_shard.2"
        );
        registration.addIngredientInfo(
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), 1000),
                VanillaTypes.FLUID,
                "jei.woot.puredye"
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(AnvilSetup.ANVIL_BLOCK.get()), AnvilRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(SqueezerSetup.SQUEEZER_BLOCK.get()), DyeSqueezerRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(SqueezerSetup.ENCHANT_SQUEEZER_BLOCK.get()), EnchantSqueezerRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get()), FluidConvertorRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(InfuserSetup.INFUSER_BLOCK.get()), InfuserRecipeCategory.UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(InfuserScreen.class, 84, 39, 30, 18, InfuserRecipeCategory.UID);
        registration.addRecipeClickArea(DyeSqueezerScreen.class, 60, 39, 18, 18, DyeSqueezerRecipeCategory.UID);
        registration.addRecipeClickArea(EnchantSqueezerScreen.class, 101, 32, 50, 36, EnchantSqueezerRecipeCategory.UID);
        registration.addRecipeClickArea(FluidConvertorScreen.class, 74, 40, 70, 26, FluidConvertorRecipeCategory.UID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        WootJeiPlugin.jeiRuntime = jeiRuntime;
    }
}
