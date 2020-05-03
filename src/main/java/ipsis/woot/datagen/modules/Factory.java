package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.mod.ModTags;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.generic.items.GenericItem;
import ipsis.woot.modules.infuser.InfuserSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Factory {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        /**
         * Basic factory blocks
         */
        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_A_BLOCK.get(), 4)
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.GRAY_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_B_BLOCK.get(), 4)
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.RED_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_C_BLOCK.get(), 4)
                .patternLine("zs ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.ORANGE_DYE_PLATE_ITEM.get())
                .key('z', GenericSetup.T1_SHARD_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_D_BLOCK.get(), 2)
                .patternLine("zsz")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.GREEN_DYE_PLATE_ITEM.get())
                .key('z', GenericSetup.T2_SHARD_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_E_BLOCK.get(), 2)
                .patternLine("zsz")
                .patternLine("sps")
                .patternLine("zsz")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.PURPLE_DYE_PLATE_ITEM.get())
                .key('z', GenericSetup.T3_SHARD_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_UPGRADE_BLOCK.get())
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.BLUE_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get())
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.LIGHT_BLUE_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get())
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.LIME_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.FACTORY_CONNECT_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.REDSTONE)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CAP_A_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.IRON_NUGGET)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CAP_B_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.GOLD_INGOT)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CAP_C_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.DIAMOND)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CAP_D_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.EMERALD)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        /**
         * Cells
         */
        ShapedRecipeBuilder.shapedRecipe(FactorySetup.CELL_1_BLOCK.get())
                .patternLine("igi")
                .patternLine("gcg")
                .patternLine("igi")
                .key('g', Tags.Items.GLASS)
                .key('i', Items.IRON_BARS)
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CELL_2_BLOCK.get())
                .addIngredient(FactorySetup.CELL_1_BLOCK.get())
                .addIngredient(GenericSetup.ENCH_PLATE_1.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CELL_3_BLOCK.get())
                .addIngredient(FactorySetup.CELL_2_BLOCK.get())
                .addIngredient(GenericSetup.ENCH_PLATE_2.get())
                .addIngredient(GenericSetup.ENCH_PLATE_2.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CELL_4_BLOCK.get())
                .addIngredient(FactorySetup.CELL_3_BLOCK.get())
                .addIngredient(GenericSetup.ENCH_PLATE_3.get())
                .addIngredient(GenericSetup.ENCH_PLATE_3.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        /**
         * Other
         */
        ShapedRecipeBuilder.shapedRecipe(FactorySetup.HEART_BLOCK.get())
                .patternLine(" s ")
                .patternLine("scs")
                .patternLine(" s ")
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .key('s', Tags.Items.HEADS)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.IMPORT_BLOCK.get())
                .patternLine(" h ")
                .patternLine(" c ")
                .patternLine("   ")
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .key('h', Blocks.HOPPER)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.EXPORT_BLOCK.get())
                .patternLine("   ")
                .patternLine(" c ")
                .patternLine(" h ")
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .key('h', Blocks.HOPPER)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.XP_SHARD_ITEM.get())
                .patternLine("sss")
                .patternLine("sss")
                .patternLine("sss")
                .key('s', FactorySetup.XP_SPLINTER_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
    }
}
