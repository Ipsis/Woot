package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.FluidConvertorRecipe;
import ipsis.woot.modules.infuser.InfuserSetup;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class FluidConvertorRecipeCategory implements IRecipeCategory<FluidConvertorRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Woot.MODID, "fluidconvertor");
    private static IDrawableStatic background;
    private static IDrawable icon;

    public FluidConvertorRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = new ResourceLocation(Woot.MODID, "textures/gui/jei/fluidconvertor.png");
        background = guiHelper.createDrawable(resourceLocation, 0, 0, 180, 87);
        icon = guiHelper.createDrawableIngredient(new ItemStack(InfuserSetup.INFUSER_BLOCK.get()));
    }

    @Override
    public Class<? extends FluidConvertorRecipe> getRecipeClass() {
        return FluidConvertorRecipe.class;
    }

    @Override
    public void setIngredients(FluidConvertorRecipe fluidConvertorRecipe, IIngredients iIngredients) {
        iIngredients.setInputLists(VanillaTypes.ITEM, fluidConvertorRecipe.getInputs());
        iIngredients.setInput(VanillaTypes.FLUID, fluidConvertorRecipe.getInputFluid());
        iIngredients.setOutput(VanillaTypes.FLUID, fluidConvertorRecipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, FluidConvertorRecipe fluidConvertorRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
        itemStacks.init(0, true, 99, 21);
        itemStacks.set(iIngredients);

        IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
        fluidStacks.init(0, true, 46, 18, 16, 60, 10000, true, null);
        fluidStacks.init(1, false, 154, 18, 16, 60, 10000, true, null);
        fluidStacks.set(iIngredients);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StringHelper.translate("gui.woot.fluidconvertor.name");
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
    public void draw(FluidConvertorRecipe recipe, double mouseX, double mouseY) {
        IJeiRuntime runtime = WootJeiPlugin.jeiRuntime;
        if (runtime != null) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.fontRenderer.drawString(String.format("%d RF", recipe.getEnergy()),
                    90, 70, Color.BLACK.getRGB());
        }
    }
}
