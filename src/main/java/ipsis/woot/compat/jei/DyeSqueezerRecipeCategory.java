package ipsis.woot.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import ipsis.woot.Woot;
import ipsis.woot.crafting.DyeSqueezerRecipe;
import ipsis.woot.crafting.EnchantSqueezerRecipe;
import ipsis.woot.modules.squeezer.DyeMakeup;
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

public class DyeSqueezerRecipeCategory implements IRecipeCategory<DyeSqueezerRecipe>  {

    public static final ResourceLocation UID = new ResourceLocation(Woot.MODID, "dyesqueezer");
    private static IDrawableStatic background;
    private final IDrawable icon;

    public DyeSqueezerRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = new ResourceLocation(Woot.MODID, "textures/gui/jei/squeezer.png");
        background = guiHelper.createDrawable(resourceLocation, 0, 0, 180, 86);
        icon = guiHelper.createDrawableIngredient(new ItemStack(SqueezerSetup.SQUEEZER_BLOCK.get()));
    }

    @Override
    public Class<? extends DyeSqueezerRecipe> getRecipeClass() {
        return DyeSqueezerRecipe.class;
    }

    @Override
    public void setIngredients(DyeSqueezerRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputLists(VanillaTypes.ITEM, recipe.getInputs());
        iIngredients.setOutput(VanillaTypes.FLUID, recipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, DyeSqueezerRecipe recipe, IIngredients iIngredients) {
        IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
        itemStacks.init(0, true, 38, 39);
        itemStacks.set(iIngredients);

/*        IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
        fluidStacks.init(0, false, 154, 40, 16, 16, DyeMakeup.LCM, false, null);
        fluidStacks.set(iIngredients); */
    }

    @Override
    public void draw(DyeSqueezerRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        IJeiRuntime runtime = WootJeiPlugin.jeiRuntime;
        if (runtime != null) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.fontRenderer.drawString(matrixStack, "Red: " + recipe.getRed() + " mb", 70.0F, 28.0F, Color.BLACK.getRGB());
            minecraft.fontRenderer.drawString(matrixStack, "Yellow: " + recipe.getYellow() + " mb", 70.0F, 38.0F, Color.BLACK.getRGB());
            minecraft.fontRenderer.drawString(matrixStack, "Blue: " + recipe.getBlue() + " mb", 70.0F, 48.0F, Color.BLACK.getRGB());
            minecraft.fontRenderer.drawString(matrixStack, "White: " + recipe.getWhite() + " mb", 70.0F, 58.0F, Color.BLACK.getRGB());

            minecraft.fontRenderer.drawString(matrixStack, String.format("%d RF", recipe.getEnergy()),
                    70.0F, 68.0F, Color.BLACK.getRGB());

            Screen screen = Minecraft.getInstance().currentScreen;
            EnergyBarHelper.drawEnergyBar(matrixStack,
                    screen,
                    SqueezerConfiguration.DYE_SQUEEZER_MAX_ENERGY.get(),
                    recipe.getEnergy(),
                    10, 78, 16, 60);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StringHelper.translate("gui.woot.squeezer.name");
    }

    @Override
    public IDrawableStatic getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
}
