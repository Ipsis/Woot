package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Infuser {
    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shapedRecipe(InfuserSetup.INFUSER_BLOCK.get())
                .patternLine("idi")
                .patternLine("gbg")
                .patternLine("ioi")
                .key('i', GenericSetup.SI_PLATE_ITEM.get())
                .key('d', Blocks.DROPPER)
                .key('o', Tags.Items.OBSIDIAN)
                .key('b', Items.BUCKET)
                .key('g', Tags.Items.GLASS)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
    }
}
