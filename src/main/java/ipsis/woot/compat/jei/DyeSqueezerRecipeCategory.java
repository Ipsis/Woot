package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.DyeSqueezerRecipe;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class DyeSqueezerRecipeCategory implements IRecipeCategory<DyeSqueezerRecipe>  {

    public static final ResourceLocation UID = new ResourceLocation(Woot.MODID, "dyesqueezer");
    private static IDrawableStatic background;
    private final IDrawable icon;

    public DyeSqueezerRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = new ResourceLocation(Woot.MODID, "textures/gui/jei/squeezer.png");
        background = guiHelper.createDrawable(resourceLocation, 0, 0, 180, 86);
        icon = guiHelper.createDrawableIngredient(new ItemStack(FactorySetup.HEART_BLOCK.get()));
    }

    @Override
    public Class<? extends DyeSqueezerRecipe> getRecipeClass() {
        return DyeSqueezerRecipe.class;
    }

    @Override
    public void setIngredients(DyeSqueezerRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputLists(VanillaTypes.ITEM, recipe.getJeiInputs());
        iIngredients.setOutput(VanillaTypes.FLUID, recipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, DyeSqueezerRecipe recipe, IIngredients iIngredients) {
        IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
        itemStacks.init(0, true, 38, 39);
        itemStacks.set(iIngredients);

        IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
        fluidStacks.init(0, false, 154, 40, 16, 16, SqueezerConfiguration.TANK_CAPACITY.get(), true, null);
        fluidStacks.set(iIngredients);
    }

    @Override
    public void draw(DyeSqueezerRecipe recipe, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.fontRenderer.drawString("Red: " + recipe.output.getRed() + " mb", 70.0F, 28.0F, Color.BLACK.getRGB());
        minecraft.fontRenderer.drawString("Yellow: " + recipe.output.getYellow() + " mb", 70.0F, 38.0F, Color.BLACK.getRGB());
        minecraft.fontRenderer.drawString("Blue: " + recipe.output.getBlue() + " mb", 70.0F, 48.0F, Color.BLACK.getRGB());
        minecraft.fontRenderer.drawString("White: " + recipe.output.getWhite() + " mb", 70.0F, 58.0F, Color.BLACK.getRGB());
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Dye Squeezer";
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
