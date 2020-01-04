package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.modules.layout.LayoutSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Layout {
    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shapedRecipe(LayoutSetup.LAYOUT_BLOCK.get())
                .patternLine("grg")
                .patternLine("ygb")
                .patternLine("gwg")
                .key('g', Tags.Items.GLASS)
                .key('r', Tags.Items.DYES_RED)
                .key('y', Tags.Items.DYES_YELLOW)
                .key('b', Tags.Items.DYES_BLACK)
                .key('w', Tags.Items.DYES_WHITE)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
    }
}
