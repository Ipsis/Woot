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
                ModBlocks.blockFactory.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactory.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactory.BASENAME),
                ModBlocks.blockController.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryController.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryController.BASENAME),
                ModBlocks.blockExtender.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryExtender.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryExtender.BASENAME),
                ModBlocks.blockProxy.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryProxy.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryProxy.BASENAME),
                ModBlocks.blockStructure.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryStructure.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryStructure.BASENAME),
                ModBlocks.blockUpgrade.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryUpgrade.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryUpgrade.BASENAME),
                ModBlocks.blockUpgradeB.setUnlocalizedName(Reference.MOD_ID + "." + BlockMobFactoryUpgradeB.BASENAME).setRegistryName(Reference.MOD_ID, BlockMobFactoryUpgradeB.BASENAME)
        );
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {

        ModItems.init();

        event.getRegistry().registerAll(
                ModItems.itemDye.setUnlocalizedName(Reference.MOD_ID + "." + ItemDye.BASENAME).setRegistryName(Reference.MOD_ID, ItemDye.BASENAME),
                ModItems.itemFactoryCap.setUnlocalizedName(Reference.MOD_ID + "." + ItemFactoryCap.BASENAME).setRegistryName(Reference.MOD_ID, ItemFactoryCap.BASENAME),
                ModItems.itemFactoryCasing.setUnlocalizedName(Reference.MOD_ID + "." + ItemFactoryCasing.BASENAME).setRegistryName(Reference.MOD_ID, ItemFactoryCasing.BASENAME),
                ModItems.itemFactoryConnector.setUnlocalizedName(Reference.MOD_ID + "." + ItemFactoryConnector.BASENAME).setRegistryName(Reference.MOD_ID, ItemFactoryConnector.BASENAME),
                ModItems.itemFactoryFrame.setUnlocalizedName(Reference.MOD_ID + "." + ItemFactoryFrame.BASENAME).setRegistryName(Reference.MOD_ID, ItemFactoryFrame.BASENAME),
                ModItems.itemFactoryUpgrade.setUnlocalizedName(Reference.MOD_ID + "." + ItemFactoryUpgrade.BASENAME).setRegistryName(Reference.MOD_ID, ItemFactoryUpgrade.BASENAME),
                ModItems.itemFerrocrete.setUnlocalizedName(Reference.MOD_ID + "." + ItemFerrocrete.BASENAME).setRegistryName(Reference.MOD_ID, ItemFerrocrete.BASENAME),
                ModItems.itemFerrocretePlate.setUnlocalizedName(Reference.MOD_ID + "." + ItemFerrocretePlate.BASENAME).setRegistryName(Reference.MOD_ID, ItemFerrocretePlate.BASENAME),
                ModItems.itemManual.setUnlocalizedName(Reference.MOD_ID + "." + ItemManual.BASENAME).setRegistryName(Reference.MOD_ID, ItemManual.BASENAME),
                ModItems.itemPrism2.setUnlocalizedName(Reference.MOD_ID + "." + ItemPrism2.BASENAME).setRegistryName(Reference.MOD_ID, ItemPrism2.BASENAME),
                ModItems.itemPrismFrame.setUnlocalizedName(Reference.MOD_ID + "." + ItemPrismFrame.BASENAME).setRegistryName(Reference.MOD_ID, ItemPrismFrame.BASENAME),
                ModItems.itemPulverisedFerrocrete.setUnlocalizedName(Reference.MOD_ID + "." + ItemPulverisedFerrocrete.BASENAME).setRegistryName(Reference.MOD_ID, ItemPulverisedFerrocrete.BASENAME),
                ModItems.itemShard.setUnlocalizedName(Reference.MOD_ID + "." + ItemShard.BASENAME).setRegistryName(Reference.MOD_ID, ItemShard.BASENAME),
                ModItems.itemSkull.setUnlocalizedName(Reference.MOD_ID + "." + ItemSkull.BASENAME).setRegistryName(Reference.MOD_ID, ItemSkull.BASENAME),
                ModItems.itemYahHammer.setUnlocalizedName(Reference.MOD_ID + "." + ItemYahHammer.BASENAME).setRegistryName(Reference.MOD_ID, ItemYahHammer.BASENAME),
                ModItems.itemXpShard.setUnlocalizedName(Reference.MOD_ID + "." + ItemXpShard.BASENAME).setRegistryName(Reference.MOD_ID, ItemXpShard.BASENAME)
        );

        event.getRegistry().registerAll(
                new ItemBlock(ModBlocks.blockAnvil).setRegistryName(ModBlocks.blockAnvil.getRegistryName()),
                new ItemBlock(ModBlocks.blockLayout).setRegistryName(ModBlocks.blockLayout.getRegistryName()),
                new ItemBlock(ModBlocks.blockFactory).setRegistryName(ModBlocks.blockFactory.getRegistryName()),
                new ItemBlockController(ModBlocks.blockController).setRegistryName(ModBlocks.blockController.getRegistryName()),
                new ItemBlock(ModBlocks.blockExtender).setRegistryName(ModBlocks.blockExtender.getRegistryName()),
                new ItemBlock(ModBlocks.blockProxy).setRegistryName(ModBlocks.blockProxy.getRegistryName()),
                new ItemBlockStructure(ModBlocks.blockStructure).setRegistryName(ModBlocks.blockStructure.getRegistryName()),
                new ItemBlockUpgrade(ModBlocks.blockUpgrade).setRegistryName(ModBlocks.blockUpgrade.getRegistryName()),
                new ItemBlockUpgradeB(ModBlocks.blockUpgradeB).setRegistryName(ModBlocks.blockUpgradeB.getRegistryName())
        );
    }

    @SubscribeEvent
    public void onRegisterEnchantments(RegistryEvent.Register<Enchantment> event) {

        event.getRegistry().register(new EnchantmentDecapitate());

    }
}
