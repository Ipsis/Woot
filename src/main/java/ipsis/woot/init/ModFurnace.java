package ipsis.woot.init;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ModFurnace {

    public static void preInit() {

    }

    public static void init() {

        FurnaceRecipes.instance().addSmeltingRecipeForBlock(ModBlocks.blockStygianIronOre, new ItemStack(ModItems.itemStygianIronIngot), 0.7F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.itemStygianIronDust), new ItemStack(ModItems.itemStygianIronIngot), 0.7F);
    }

    public static void postInit() {

    }
}
