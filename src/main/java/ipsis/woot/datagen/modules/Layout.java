package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.layout.LayoutSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Layout {
    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(LayoutSetup.LAYOUT_BLOCK.get())
                .pattern("grg")
                .pattern("ytb")
                .pattern("gwg")
                .define('g', Tags.Items.GLASS)
                .define('r', Tags.Items.DYES_RED)
                .define('y', Tags.Items.DYES_YELLOW)
                .define('b', Tags.Items.DYES_BLACK)
                .define('w', Tags.Items.DYES_WHITE)
                .define('t', Items.TORCH)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(LayoutSetup.INTERN_ITEM.get())
                .pattern(" si")
                .pattern(" ws")
                .pattern("w  ")
                .define('i', GenericSetup.SI_INGOT_ITEM.get())
                .define('s', Tags.Items.DUSTS_REDSTONE)
                .define('w', Items.STICK)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);
    }
}
