package ipsis.woot.plugins.jei;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.plugins.jei.anvil.StygianAnvilRecipeCategory;
import ipsis.woot.plugins.jei.anvil.StygianAnvilRecipeHandler;
import ipsis.woot.plugins.jei.anvil.StygianAnvilRecipeMaker;
import ipsis.woot.reference.Reference;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    public static final String JEI_ANVIL = Reference.MOD_ID + ":stygianavil";

    public static IJeiHelpers jeiHelper;
    @Override
    public void register(IModRegistry registry) {

        jeiHelper = registry.getJeiHelpers();

        registry.addRecipeCategories(new StygianAnvilRecipeCategory(jeiHelper.getGuiHelper()));
        registry.addRecipeHandlers(new StygianAnvilRecipeHandler());
        registry.addRecipes(StygianAnvilRecipeMaker.getRecipes());
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.blockAnvil), JEI_ANVIL);
        registry.addDescription(new ItemStack(ModBlocks.blockAnvil), "Right-click with a die or Ender Shard to place it on the anvil. Drop the other ingredients on the anvil, then hit with the Yah Hammer");
    }
}
