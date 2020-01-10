package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.modules.infuser.InfuserConfiguration;
import ipsis.woot.modules.infuser.InfuserSetup;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

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
        if (slot == 2 && itemStack.getItem() == Items.ENCHANTED_BOOK || itemStack.isEnchanted())
            list.add("Random enchantment");
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
        fluidStacks.init(0, true, 154, 18, 16, 60, InfuserConfiguration.INFUSER_TANK_CAPACITY.get(), true, null);
        fluidStacks.set(iIngredients);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Infuser";
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
