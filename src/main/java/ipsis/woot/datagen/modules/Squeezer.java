package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Squeezer {
    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shapedRecipe(SqueezerSetup.SQUEEZER_BLOCK.get())
                .patternLine("ipi")
                .patternLine("dbd")
                .patternLine("ioi")
                .key('i', GenericSetup.SI_PLATE_ITEM.get())
                .key('p', Blocks.PISTON)
                .key('d', Tags.Items.DYES)
                .key('o', Tags.Items.OBSIDIAN)
                .key('b', Items.BUCKET)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(SqueezerSetup.ENCHANT_SQUEEZER_BLOCK.get())
                .patternLine("ipi")
                .patternLine("tbg")
                .patternLine("ioi")
                .key('i', GenericSetup.SI_PLATE_ITEM.get())
                .key('p', Blocks.PISTON)
                .key('t', Items.BOOK)
                .key('g', Items.GLASS_BOTTLE)
                .key('o', Tags.Items.OBSIDIAN)
                .key('b', Items.BUCKET)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
    }
}
