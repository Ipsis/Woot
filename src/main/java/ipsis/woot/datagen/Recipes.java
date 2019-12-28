package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.mod.ModBlocks;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.LAYOUT_BLOCK)
                .patternLine("grg")
                .patternLine("ygb")
                .patternLine("gwg")
                .key('g', Tags.Items.GLASS)
                .key('r', Tags.Items.DYES_RED)
                .key('y', Tags.Items.DYES_YELLOW)
                .key('b', Tags.Items.DYES_BLACK)
                .key('w', Tags.Items.DYES_WHITE)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone",
                        InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

    }
}
