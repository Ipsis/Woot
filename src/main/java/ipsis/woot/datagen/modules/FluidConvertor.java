package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.crafting.FluidConvertorRecipe;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.generic.GenericSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class FluidConvertor {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shapedRecipe(FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get())
                .patternLine(" s ")
                .patternLine(" c ")
                .patternLine("bfb")
                .key('s', Blocks.BREWING_STAND)
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .key('b', Items.BUCKET)
                .key('f', Blocks.FURNACE)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ResourceLocation rl = new ResourceLocation(Woot.MODID, "fluidconvertor/recipe1");
        FluidConvertorRecipe.convertorRecipe(
                rl,
                Ingredient.fromTag(Tags.Items.GUNPOWDER), 1,
                new FluidStack(Fluids.WATER, 1000),
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1000),
                1000).build(consumer, rl);

        rl = new ResourceLocation(Woot.MODID, "fluidconvertor/recipe2");
        FluidConvertorRecipe.convertorRecipe(
                rl,
                Ingredient.fromTag(Tags.Items.RODS_BLAZE), 1,
                new FluidStack(Fluids.LAVA, 1000),
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1000),
                1000).build(consumer, rl);

    }
}
