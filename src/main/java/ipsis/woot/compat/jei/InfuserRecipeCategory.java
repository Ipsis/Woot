package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.util.helper.StringHelper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.gui.ingredient.ITooltipCallback;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.List;

public class InfuserRecipeCategory implements IRecipeCategory<InfuserRecipe>, ITooltipCallback<ItemStack>  {

    public static final ResourceLocation UID = new ResourceLocation(Woot.MODID, "infuser");
    private static IDrawableStatic background;
    private static IDrawable icon;

    public InfuserRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = new ResourceLocation(Woot.MODID, "textures/gui/jei/infuser.png");
        background = guiHelper.createDrawable(resourceLocation, 0, 0, 180, 87);
        icon = guiHelper.createDrawableIngredient(new ItemStack(InfuserSetup.INFUSER_BLOCK.get()));
    }

    @Override
    public Class<? extends InfuserRecipe> getRecipeClass() {
        return InfuserRecipe.class;
    }

    @Override
    public void setIngredients(InfuserRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputLists(VanillaTypes.ITEM, recipe.getInputs());
        iIngredients.setInput(VanillaTypes.FLUID, recipe.getFluidInput());
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public void onTooltip(int slot, boolean isInput, ItemStack itemStack, List<String> list) {

        if (slot != 2)
            return;

        if (itemStack.getItem() == Items.ENCHANTED_BOOK || itemStack.isEnchanted()) {
            if (itemStack.getCount() == 1)
                list.add("Random enchantment I");
            else if (itemStack.getCount() == 2)
                list.add("Random enchantment II");
            else if (itemStack.getCount() == 3)
                list.add("Random enchantment III");
            else if (itemStack.getCount() == 4)
                list.add("Random enchantment IV");
            else if (itemStack.getCount() == 5)
                list.add("Random enchantment V");
        }
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, InfuserRecipe infuserRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
        itemStacks.init(0, true, 45, 39);
        itemStacks.init(1, true, 63, 39);
        itemStacks.init(2, false, 117, 39);
        itemStacks.addTooltipCallback(this);
        itemStacks.set(iIngredients);

        IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
        fluidStacks.init(0, true, 154, 18, 16, 60, WootJeiPlugin.maxInfuserRecipeMb, true, null);
        fluidStacks.set(iIngredients);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StringHelper.translate("gui.woot.infuser.name").getUnformattedComponentText();
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
    public void draw(InfuserRecipe recipe, double mouseX, double mouseY) {

        IJeiRuntime runtime = WootJeiPlugin.jeiRuntime;
        if (runtime != null) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.fontRenderer.drawString(String.format("%d RF", recipe.getEnergy()),
                    70.0F, 68.0F, Color.BLACK.getRGB());
        }
    }
}
