package ipsis.woot.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import ipsis.woot.Woot;
import ipsis.woot.crafting.EnchantSqueezerRecipe;
import ipsis.woot.modules.fluidconvertor.FluidConvertorConfiguration;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.util.helper.StringHelper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class EnchantSqueezerRecipeCategory implements IRecipeCategory<EnchantSqueezerRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Woot.MODID, SqueezerSetup.ENCHANT_SQUEEZER_TAG);

    private static IDrawableStatic background;
    private static IDrawable icon;

    public EnchantSqueezerRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = new ResourceLocation(Woot.MODID, "textures/gui/jei/" + SqueezerSetup.ENCHANT_SQUEEZER_TAG + ".png");
        background = guiHelper.createDrawable(resourceLocation, 0, 0, 180, 87);
        icon = guiHelper.createDrawableIngredient(new ItemStack(SqueezerSetup.ENCHANT_SQUEEZER_BLOCK.get()));
    }

    @Override
    public Class<? extends EnchantSqueezerRecipe> getRecipeClass() {
        return EnchantSqueezerRecipe.class;
    }

    @Override
    public void setIngredients(EnchantSqueezerRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, recipe.getInput());
        iIngredients.setOutput(VanillaTypes.FLUID, recipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, EnchantSqueezerRecipe enchantSqueezerRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
        itemStacks.init(0, true, 81, 39);
        itemStacks.set(iIngredients);

        IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
        fluidStacks.init(0, false, 154, 18, 16, 60, WootJeiPlugin.maxEnchantRecipeMb, false, null);
        fluidStacks.set(iIngredients);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StringHelper.translate("gui.woot.enchsqueezer.name");
    }

    @Override
    public IDrawableStatic getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(EnchantSqueezerRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        IJeiRuntime runtime = WootJeiPlugin.jeiRuntime;
        if (runtime != null) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.fontRenderer.drawString(matrixStack, String.format("%d RF", recipe.getEnergy()),
                    70, 70, Color.BLACK.getRGB());

            Screen screen = Minecraft.getInstance().currentScreen;
            EnergyBarHelper.drawEnergyBar(matrixStack,
                    screen,
                    SqueezerConfiguration.ENCH_SQUEEZER_MAX_ENERGY.get(),
                    recipe.getEnergy(),
                    10, 78, 16, 60);
        }
    }
}
