package ipsis.woot.crafting;

import ipsis.Woot;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.ItemDie;
import ipsis.woot.item.ItemFactoryCore;
import ipsis.woot.item.ItemShard;
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
                new ItemStack(ModItems.itemStygianIronPlate, 4));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.PLATE.getMeta()),
                new ItemStack(Items.BRICK),
                false,
                new ItemStack(ModItems.itemStygianIronPlate, 4));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.SHARD.getMeta()),
                new ItemStack(Items.QUARTZ),
                false,
                new ItemStack(ModItems.itemStygianIronPlate, 4));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.CORE.getMeta()),
                new ItemStack(Items.REDSTONE),
                false,
                new ItemStack(ModItems.itemStygianIronPlate, 4));

        // Plate
        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemStygianIronPlate, 1),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.PLATE.getMeta()),
                true,
                new ItemStack(ModItems.itemStygianIronIngot));

        // Dust
        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemStygianIronDust, 3),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.MESH.getMeta()),
                true,
                new ItemStack(Blocks.NETHERRACK),
                new ItemStack(Blocks.SOUL_SAND),
                new ItemStack(Blocks.IRON_ORE)
        );

        // Shards
        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemEnderShard, 3),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.SHARD.getMeta()),
                true,
                new ItemStack(Items.ENDER_EYE));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemShard, 3, ItemShard.EnumShardType.DIAMOND.getMeta()),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.SHARD.getMeta()),
                true,
                new ItemStack(Items.DIAMOND));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemShard, 3, ItemShard.EnumShardType.EMERALD.getMeta()),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.SHARD.getMeta()),
                true,
                new ItemStack(Items.EMERALD));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemShard, 3, ItemShard.EnumShardType.QUARTZ.getMeta()),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.SHARD.getMeta()),
                true,
                new ItemStack(Items.QUARTZ));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemShard, 9, ItemShard.EnumShardType.NETHERSTAR.getMeta()),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.SHARD.getMeta()),
                true,
                new ItemStack(Items.NETHER_STAR));

        // Programmed Controller
        Woot.anvilManager.addRecipe(
                new ItemStack(ModBlocks.blockFactoryController),
                new ItemStack(ModItems.itemEnderShard),
                false,
                new ItemStack(ModItems.itemFactoryBase),
                new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.CONTROLLER.getMeta())
        );

        // Cores
        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.CONTROLLER.getMeta()),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.CORE.getMeta()),
                true,
                new ItemStack(Items.DYE, 1, 0),
                new ItemStack(Items.DYE, 1, 1),
                new ItemStack(Items.DYE, 1, 2),
                new ItemStack(Items.DYE, 1, 3),
                new ItemStack(Items.DYE, 1, 4),
                new ItemStack(Items.DYE, 1, 5),
                new ItemStack(Items.DYE, 1, 6));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.HEART.getMeta()),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.CORE.getMeta()),
                true,
                new ItemStack(Blocks.MAGMA),
                new ItemStack(ModBlocks.blockStygianIron));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.T1_UPGRADE.getMeta()),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.CORE.getMeta()),
                true,
                new ItemStack(Items.REDSTONE),
                new ItemStack(Items.IRON_INGOT));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.T2_UPGRADE.getMeta()),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.CORE.getMeta()),
                true,
                new ItemStack(Items.GLOWSTONE_DUST),
                new ItemStack(Items.GOLD_INGOT));

        Woot.anvilManager.addRecipe(
                new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.T3_UPGRADE.getMeta()),
                new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.CORE.getMeta()),
                true,
                new ItemStack(Items.PRISMARINE_SHARD),
                new ItemStack(Items.DIAMOND));
    }
}
