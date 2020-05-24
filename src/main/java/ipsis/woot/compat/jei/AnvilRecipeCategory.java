package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.AnvilRecipe;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.anvil.items.DieItem;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.items.MobShardItem;
import ipsis.woot.util.helper.StringHelper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.gui.ingredient.ITooltipCallback;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class AnvilRecipeCategory implements IRecipeCategory<AnvilRecipe>, ITooltipCallback<ItemStack> {

    public static final ResourceLocation UID = new ResourceLocation(Woot.MODID, "anvil");
    private static IDrawableStatic background;
    private static IDrawable icon;

    public AnvilRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation resourceLocation = new ResourceLocation(Woot.MODID, "textures/gui/jei/anvil.png");
        background = guiHelper.createDrawable(resourceLocation, 0, 0, 180, 87);
        icon = guiHelper.createDrawableIngredient(new ItemStack(AnvilSetup.ANVIL_BLOCK.get()));
    }

    @Override
    public Class<? extends AnvilRecipe> getRecipeClass() {
        return AnvilRecipe.class;
    }

    @Override
    public void setIngredients(AnvilRecipe anvilRecipe, IIngredients iIngredients) {

        if (anvilRecipe.getBaseIngredient().getMatchingStacks().length == 1) {
            if (anvilRecipe.getBaseIngredient().getMatchingStacks()[0].getItem() == FactorySetup.MOB_SHARD_ITEM.get()) {
                ItemStack itemStack = new ItemStack(FactorySetup.MOB_SHARD_ITEM.get());
                MobShardItem.setJEIEnderShard(itemStack);
                iIngredients.setInput(VanillaTypes.ITEM, itemStack);
            } else {
                iIngredients.setInputLists(VanillaTypes.ITEM, anvilRecipe.getInputs());
            }
        } else {
            iIngredients.setInputLists(VanillaTypes.ITEM, anvilRecipe.getInputs());
        }

        iIngredients.setOutput(VanillaTypes.ITEM, anvilRecipe.getOutput());


    }

    @Override
    public void onTooltip(int i, boolean b, ItemStack itemStack, List<String> list) {
        if (i == 0) {
            list.add("Base Item");
            if (itemStack.getItem() instanceof DieItem)
                list.add("Not consumed on crafting");
        }
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, AnvilRecipe anvilRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
        itemStacks.init(0, true, 81, 39); // base
        itemStacks.init(1, true, 27, 28); // ingredient 1
        itemStacks.init(2, true, 45, 28); // ingredient 2
        itemStacks.init(3, true, 27, 46); // ingredient 3
        itemStacks.init(4, true, 45, 46); // ingredient 4
        itemStacks.init(5, false, 117, 39); // ingredient 4
        itemStacks.addTooltipCallback(this);
        itemStacks.set(iIngredients);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
       return StringHelper.translate("gui.woot.anvil.name");
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
