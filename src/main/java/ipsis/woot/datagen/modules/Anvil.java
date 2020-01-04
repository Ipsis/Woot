package ipsis.woot.datagen.modules;

import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.generic.GenericSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Anvil {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shapedRecipe(AnvilSetup.ANVIL_BLOCK.get())
                .patternLine("iii")
                .patternLine(" a ")
                .patternLine("ooo")
                .key('i', GenericSetup.SI_INGOT_ITEM.get())
                .key('a', Blocks.ANVIL)
                .key('o', Tags.Items.OBSIDIAN)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        // Dies are generated from the anvil recipes

        ShapedRecipeBuilder.shapedRecipe(AnvilSetup.HAMMER_ITEM.get())
                .patternLine(" si")
                .patternLine(" ws")
                .patternLine("w  ")
                .key('i', GenericSetup.SI_INGOT_ITEM.get())
                .key('s', Tags.Items.STRING)
                .key('w', Items.STICK)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
    }
}
