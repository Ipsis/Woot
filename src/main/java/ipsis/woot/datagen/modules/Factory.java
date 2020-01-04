package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Factory {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

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
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.ORANGE_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_D_BLOCK.get(), 4)
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.GREEN_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_E_BLOCK.get(), 4)
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.PURPLE_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
    }
}
