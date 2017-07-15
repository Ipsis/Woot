package ipsis.woot.crafting;

import ipsis.Woot;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.ItemDie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AnvilManagerLoader {

    public static void load() {

        // Die creation
        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.MESH.getMeta()),
                new ItemStack(Blocks.IRON_BARS),
                false,
                new ItemStack(Items.GOLD_INGOT));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.PLATE.getMeta()),
                new ItemStack(Items.BRICK),
                false,
                new ItemStack(Items.GOLD_INGOT));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.SHARD.getMeta()),
                new ItemStack(Items.QUARTZ),
                false,
                new ItemStack(Items.GOLD_INGOT));

        // Dust
        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemNetherrackDust, 2),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.MESH.getMeta()),
                true,
                new ItemStack(Blocks.NETHERRACK));

        // Shards
        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemEnderShard, 3),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.SHARD.getMeta()),
                true,
                new ItemStack(Items.ENDER_EYE));
    }
}
