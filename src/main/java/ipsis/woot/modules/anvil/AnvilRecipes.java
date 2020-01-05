package ipsis.woot.modules.anvil;

import ipsis.woot.Woot;
import ipsis.woot.crafting.AnvilRecipe;
import ipsis.woot.mod.ModItems;
import ipsis.woot.modules.anvil.items.DieItem;
import ipsis.woot.modules.factory.FactorySetup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AnvilRecipes {

    public static void load() {
        Woot.LOGGER.debug("Loading anvil crafting recipes");

        AnvilRecipe.recipeList.clear();

        // Die creation
        AnvilRecipe.addRecipe(
                new ItemStack(Items.STONE_SLAB),
                false,
                DieItem.getItemStack(DieType.PLATE),
                new ItemStack(Blocks.OBSIDIAN));

        AnvilRecipe.addRecipe(
                new ItemStack(Items.QUARTZ),
                false,
                DieItem.getItemStack(DieType.SHARD),
                new ItemStack(Blocks.OBSIDIAN));

        // Controller creation
        AnvilRecipe.addRecipe(
                new ItemStack(ModItems.MOB_SHARD_ITEM),
                false,
                new ItemStack(FactorySetup.CONTROLLER_BLOCK.get()),
                new ItemStack(Blocks.GLASS)
        );
    }
}
