package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.crafting.FluidConvertorRecipe;
import ipsis.woot.crafting.FluidConvertorRecipeBuilder;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class FluidConvertor {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get())
                .pattern(" s ")
                .pattern(" c ")
                .pattern("bfb")
                .define('s', Blocks.BREWING_STAND)
                .define('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .define('b', Items.BUCKET)
                .define('f', Blocks.FURNACE)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get())
                .requires(FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer, "fluid_conv_1");

        /**
         * Contaus Fluid
         */
        Ingredient[] conatus_ingredients = {
                Ingredient.of(FactorySetup.XP_SHARD_ITEM.get()),
                Ingredient.of(FactorySetup.XP_SPLINTER_ITEM.get()),
                Ingredient.of(Items.REDSTONE),
                Ingredient.of(GenericSetup.T1_SHARD_ITEM.get()),
                Ingredient.of(GenericSetup.T2_SHARD_ITEM.get()),
                Ingredient.of(GenericSetup.T3_SHARD_ITEM.get())
        };
        ResourceLocation rl;
        int[] conatus_outputAmount = { 1000, 100, 1000, 1250, 2500, 5000 };
        if (conatus_ingredients.length == conatus_outputAmount.length) {
            for (int i = 0; i < conatus_ingredients.length; i++) {
                FluidConvertorRecipeBuilder.fluidConvertorRecipe(
                        new FluidStack(FluidSetup.CONATUS_FLUID.get(), conatus_outputAmount[i]),
                        1000,
                        conatus_ingredients[i], 1,
                        new FluidStack(FluidSetup.MOB_ESSENCE_FLUID.get(), 1000))
                .build(consumer, "conatus" + i);
            }
        } else {
            Woot.setup.getLogger().error("FluidConvertor recipes ingredients != outputAmount");
        }

        rl = new ResourceLocation(Woot.MODID, "fluidconvertor/conatus_ench1");
        FluidConvertorRecipeBuilder.fluidConvertorRecipe(
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1250),
                1000,
                Ingredient.of(Items.MAGMA_BLOCK), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), 1000))
                .build(consumer, "conatus_ench1");

        FluidConvertorRecipeBuilder.fluidConvertorRecipe(
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1450),
                1000,
                Ingredient.of(Items.END_STONE), 1,
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), 1000))
                .build(consumer, "conatus_ench2");

        /**
         * Purge Fluid
         */
        Ingredient[] ingredients = {
                Ingredient.of(Items.ROTTEN_FLESH),
                Ingredient.of(Items.BONE),
                Ingredient.of(Items.BLAZE_ROD),
                Ingredient.of(Items.ENDER_PEARL),
        };
        int[] outputAmount = { 1000, 1000, 2000, 4000 };

        if (ingredients.length == outputAmount.length) {
            for (int i = 0; i < ingredients.length; i++) {
                FluidConvertorRecipeBuilder.fluidConvertorRecipe(
                        new FluidStack(FluidSetup.MOB_ESSENCE_FLUID.get(), outputAmount[i]),
                        1000,
                        ingredients[i], 1,
                        new FluidStack(Fluids.WATER, 1000))
                        .build(consumer, "purge" + i);
            }
        } else {
            Woot.setup.getLogger().error("FluidConvertor recipes ingredients != outputAmount");
        }
    }
}
