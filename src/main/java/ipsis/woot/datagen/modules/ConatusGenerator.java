package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.crafting.ConatusGeneratorRecipe;
import ipsis.woot.fluilds.FluidSetup;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class ConatusGenerator {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {


        ResourceLocation rl = new ResourceLocation(Woot.MODID, "conatusgen/recipe1");
        ConatusGeneratorRecipe.generatorRecipe(
                rl,
                Ingredient.fromTag(Tags.Items.GUNPOWDER), 1,
                new FluidStack(Fluids.WATER, 1000),
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1000),
                1000).build(consumer, rl);

        rl = new ResourceLocation(Woot.MODID, "conatusgen/recipe2");
        ConatusGeneratorRecipe.generatorRecipe(
                rl,
                Ingredient.fromTag(Tags.Items.RODS_BLAZE), 1,
                new FluidStack(Fluids.LAVA, 1000),
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1000),
                1000).build(consumer, rl);

    }
}
