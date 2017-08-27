package ipsis.woot.event;

import ipsis.woot.block.*;
import ipsis.woot.enchantment.EnchantmentDecapitate;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.*;
import ipsis.woot.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerRegistryEvent {

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {

        ModBlocks.init();

        event.getRegistry().registerAll(
                ModBlocks.blockAnvil.setUnlocalizedName(Reference.MOD_ID + "." + BlockWootAnvil.BASENAME).setRegistryName(Reference.MOD_ID, BlockWootAnvil.BASENAME),
                ModBlocks.blockLayout.setUnlocalizedName(Reference.MOD_ID + "." + BlockLayout.BASENAME).setRegistryName(Reference.MOD_ID, BlockLayout.BASENAME),
                ModBlocks.blockFactoryHeart.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryHeart.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryHeart.BASENAME),
                ModBlocks.blockStygianIron.setUnlocalizedName(Reference.MOD_ID + "." + BlockStygianIron.BASENAME).setRegistryName(Reference.MOD_ID, BlockStygianIron.BASENAME),
                ModBlocks.blockFactoryController.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryController.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryController.BASENAME),
                ModBlocks.blockImporter.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryImporter.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryImporter.BASENAME),
                ModBlocks.blockExporter.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryExporter.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryExporter.BASENAME),
                ModBlocks.blockCell.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryCell.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryCell.BASENAME),
                ModBlocks.blockStructure.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryStructure.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryStructure.BASENAME),
                ModBlocks.blockUpgrade.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryUpgrade.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryUpgrade.BASENAME),
                ModBlocks.blockUpgradeB.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryUpgradeB.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryUpgradeB.BASENAME)
        );
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {

        ModItems.init();

        event.getRegistry().registerAll(
                ModItems.itemDie.setUnlocalizedName(Reference.MOD_ID + "." + ItemDie.BASENAME).setRegistryName(Reference.MOD_ID, ItemDie.BASENAME),
                ModItems.itemFactoryCore.setUnlocalizedName(Reference.MOD_ID + "." + ItemFactoryCore.BASENAME).setRegistryName(Reference.MOD_ID, ItemFactoryCore.BASENAME),
                ModItems.itemFactoryBase.setUnlocalizedName(Reference.MOD_ID + "." + ItemFactoryBase.BASENAME).setRegistryName(Reference.MOD_ID, ItemFactoryBase.BASENAME),
                ModItems.itemStygianIronIngot.setUnlocalizedName(Reference.MOD_ID + "." + ItemStygianIronIngot.BASENAME).setRegistryName(Reference.MOD_ID, ItemStygianIronIngot.BASENAME),
                ModItems.itemStygianIronPlate.setUnlocalizedName(Reference.MOD_ID + "." + ItemStygianIronPlate.BASENAME).setRegistryName(Reference.MOD_ID, ItemStygianIronPlate.BASENAME),
                ModItems.itemManual.setUnlocalizedName(Reference.MOD_ID + "." + ItemManual.BASENAME).setRegistryName(Reference.MOD_ID, ItemManual.BASENAME),
                ModItems.itemNetherrackDust.setUnlocalizedName(Reference.MOD_ID + "." + ItemNetherrackDust.BASENAME).setRegistryName(Reference.MOD_ID, ItemNetherrackDust.BASENAME),
                ModItems.itemEnderShard.setUnlocalizedName(Reference.MOD_ID + "." + ItemEnderShard.BASENAME).setRegistryName(Reference.MOD_ID, ItemEnderShard.BASENAME),
                ModItems.itemStygianIronDust.setUnlocalizedName(Reference.MOD_ID + "." + ItemStygianIronDust.BASENAME).setRegistryName(Reference.MOD_ID, ItemStygianIronDust.BASENAME),
                ModItems.itemShard.setUnlocalizedName(Reference.MOD_ID + "." + ItemShard.BASENAME).setRegistryName(Reference.MOD_ID, ItemShard.BASENAME),
                ModItems.itemYahHammer.setUnlocalizedName(Reference.MOD_ID + "." + ItemYahHammer.BASENAME).setRegistryName(Reference.MOD_ID, ItemYahHammer.BASENAME),
                ModItems.itemXpShard.setUnlocalizedName(Reference.MOD_ID + "." + ItemXpShard.BASENAME).setRegistryName(Reference.MOD_ID, ItemXpShard.BASENAME)
        );

        event.getRegistry().registerAll(
                new ItemBlock(ModBlocks.blockAnvil).setRegistryName(ModBlocks.blockAnvil.getRegistryName()),
                new ItemBlock(ModBlocks.blockLayout).setRegistryName(ModBlocks.blockLayout.getRegistryName()),
                new ItemBlock(ModBlocks.blockFactoryHeart).setRegistryName(ModBlocks.blockFactoryHeart.getRegistryName()),
                new ItemBlock(ModBlocks.blockStygianIron).setRegistryName(ModBlocks.blockStygianIron.getRegistryName()),
                new ItemBlockController(ModBlocks.blockFactoryController).setRegistryName(ModBlocks.blockFactoryController.getRegistryName()),
                new ItemBlock(ModBlocks.blockImporter).setRegistryName(ModBlocks.blockImporter.getRegistryName()),
                new ItemBlock(ModBlocks.blockExporter).setRegistryName(ModBlocks.blockExporter.getRegistryName()),
                new ItemBlockStructure(ModBlocks.blockStructure).setRegistryName(ModBlocks.blockStructure.getRegistryName()),
                new ItemBlockCell(ModBlocks.blockCell).setRegistryName(ModBlocks.blockCell.getRegistryName()),
                new ItemBlockUpgrade(ModBlocks.blockUpgrade).setRegistryName(ModBlocks.blockUpgrade.getRegistryName()),
                new ItemBlockUpgradeB(ModBlocks.blockUpgradeB).setRegistryName(ModBlocks.blockUpgradeB.getRegistryName())
        );
    }

    @SubscribeEvent
    public void onRegisterEnchantments(RegistryEvent.Register<Enchantment> event) {

        event.getRegistry().register(new EnchantmentDecapitate());

    }
}
