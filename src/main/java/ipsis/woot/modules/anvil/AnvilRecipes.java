package ipsis.woot.modules.anvil;

import ipsis.woot.Woot;
import ipsis.woot.mod.ModItems;
import ipsis.woot.modules.factory.FactorySetup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;

public class AnvilCraftingManagerLoader {

    public static void load() {
        Woot.LOGGER.debug("Loading anvil crafting recipes");

        // Die creation

        // Controller creation
        AnvilCraftingManager.get().addRecipe(
                new ItemStack(ModItems.MOB_SHARD_ITEM),
                new ItemStack(FactorySetup.CONTROLLER_BLOCK.get()),
                new ItemStack(Blocks.GLASS)
        );
    }
}
