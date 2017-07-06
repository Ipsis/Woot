package ipsis.woot.crafting;

import ipsis.Woot;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.ItemDye;
import ipsis.woot.item.ItemShard;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AnvilManagerLoader {

    public static void load() {

        AnvilRecipe recipe;

        recipe = new AnvilRecipe()
                .setBaseItem(new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()))
                .addItem(new ItemStack(Items.ENDER_EYE))
                .addOutput(new ItemStack(ModItems.itemShard, 3, ItemShard.EnumShardType.ENDER.getMeta()))
                .addOutput(new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()));

        Woot.anvilManager.addRecipe(recipe);

        recipe = new AnvilRecipe()
                .setBaseItem(new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()))
                .addItem(new ItemStack(Items.IRON_INGOT))
                .addOutput(new ItemStack(ModItems.itemShard, 3, ItemShard.EnumShardType.QUARTZ.getMeta()))
                .addOutput(new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()));

        Woot.anvilManager.addRecipe(recipe);
    }
}
