package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.crafting.AnvilRecipe;
import ipsis.woot.mod.ModItems;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.generic.GenericSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
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

        ShapedRecipeBuilder.shapedRecipe(AnvilSetup.HAMMER_ITEM.get())
                .patternLine(" si")
                .patternLine(" ws")
                .patternLine("w  ")
                .key('i', GenericSetup.SI_INGOT_ITEM.get())
                .key('s', Tags.Items.STRING)
                .key('w', Items.STICK)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        AnvilRecipe.anvilRecipe2(new ResourceLocation(Woot.MODID, "anvil/plate_die"),
                Ingredient.fromItems(Items.STONE_SLAB),
                AnvilSetup.PLATE_DIE_ITEM.get(),
                NonNullList.from(Ingredient.EMPTY,
                        Ingredient.fromItems(Blocks.OBSIDIAN)))
                .build(consumer, new ResourceLocation(Woot.MODID, "anvil/plate_die"));

        AnvilRecipe.anvilRecipe2(new ResourceLocation(Woot.MODID, "anvil/shard_die"),
                Ingredient.fromItems(Items.QUARTZ),
                AnvilSetup.SHARD_DIE_ITEM.get(),
                NonNullList.from(Ingredient.EMPTY,
                        Ingredient.fromItems(Blocks.OBSIDIAN)))
                .build(consumer, new ResourceLocation(Woot.MODID, "anvil/shard_die"));

        AnvilRecipe.anvilRecipe2(new ResourceLocation(Woot.MODID, "anvil/controller"),
                Ingredient.fromItems(ModItems.MOB_SHARD_ITEM.getItem()),
                FactorySetup.CONTROLLER_BLOCK.get(),
                NonNullList.from(Ingredient.EMPTY,
                        Ingredient.fromItems(GenericSetup.PRISM_ITEM.get()),
                        Ingredient.fromItems(Blocks.COBBLESTONE)))
                .build(consumer, new ResourceLocation(Woot.MODID, "anvil/controller"));


    }
}
