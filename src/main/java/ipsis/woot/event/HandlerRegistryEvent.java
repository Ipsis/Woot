package ipsis.woot.event;

import ipsis.woot.block.*;
import ipsis.woot.enchantment.EnchantmentHeadhunter;
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

        class RegisterBlock {
            Block block;
            String basename;
            public RegisterBlock(Block block, String basename) {
               this.basename = basename;
               this.block = block;
            }
        }

        RegisterBlock[] blocks = {
                new RegisterBlock(ModBlocks.blockAnvil, BlockWootAnvil.BASENAME),
                new RegisterBlock(ModBlocks.blockLayout, BlockLayout.BASENAME),
                new RegisterBlock(ModBlocks.blockFactoryHeart, BlockMobFactoryHeart.BASENAME),
                new RegisterBlock(ModBlocks.blockSoulStone, BlockSoulStone.BASENAME),
                new RegisterBlock(ModBlocks.blockStygianIron, BlockStygianIron.BASENAME),
                new RegisterBlock(ModBlocks.blockStygianIronOre, BlockStygianIronOre.BASENAME),
                new RegisterBlock(ModBlocks.blockFactoryController, BlockMobFactoryController.BASENAME),
                new RegisterBlock(ModBlocks.blockImporter, BlockMobFactoryImporter.BASENAME),
                new RegisterBlock(ModBlocks.blockExporter, BlockMobFactoryExporter.BASENAME),
                new RegisterBlock(ModBlocks.blockCell, BlockMobFactoryCell.BASENAME),
                new RegisterBlock(ModBlocks.blockStructure, BlockMobFactoryStructure.BASENAME),
                new RegisterBlock(ModBlocks.blockUpgrade, BlockMobFactoryUpgrade.BASENAME),
                new RegisterBlock(ModBlocks.blockUpgradeB, BlockMobFactoryUpgradeB.BASENAME),
        };

        for (RegisterBlock b : blocks)
            event.getRegistry().register(b.block.setUnlocalizedName(Reference.MOD_ID + "." + b.basename).setRegistryName(Reference.MOD_ID, b.basename));
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {

        ModItems.init();

        class RegisterItem {
            Item item;
            String basename;
            public RegisterItem(Item item, String basename) {
                this.basename = basename;
                this.item = item;
            }
        }

        RegisterItem[] items = {
                new RegisterItem(ModItems.itemDie, ItemDie.BASENAME),
                new RegisterItem(ModItems.itemFactoryCore, ItemFactoryCore.BASENAME),
                new RegisterItem(ModItems.itemFactoryBase, ItemFactoryBase.BASENAME),
                new RegisterItem(ModItems.itemStygianIronIngot, ItemStygianIronIngot.BASENAME),
                new RegisterItem(ModItems.itemStygianIronPlate, ItemStygianIronPlate.BASENAME),
                new RegisterItem(ModItems.itemEnderShard, ItemEnderShard.BASENAME),
                new RegisterItem(ModItems.itemPrism, ItemPrism.BASENAME),
                new RegisterItem(ModItems.itemSoulSandDust, ItemSoulSandDust.BASENAME),
                new RegisterItem(ModItems.itemStygianIronDust, ItemStygianIronDust.BASENAME),
                new RegisterItem(ModItems.itemShard, ItemShard.BASENAME),
                new RegisterItem(ModItems.itemYahHammer, ItemYahHammer.BASENAME),
                new RegisterItem(ModItems.itemXpShard, ItemXpShard.BASENAME),
                new RegisterItem(ModItems.itemFakeManual, ItemFakeManual.BASENAME),
                new RegisterItem(ModItems.itemBuilder, ItemBuilder.BASENAME)
        };

        for (RegisterItem item : items)
            event.getRegistry().register(item.item.setUnlocalizedName(Reference.MOD_ID + "." + item.basename).setRegistryName(Reference.MOD_ID, item.basename));

        Block[] blocks = {
                ModBlocks.blockAnvil,
                ModBlocks.blockLayout,
                ModBlocks.blockFactoryHeart,
                ModBlocks.blockSoulStone,
                ModBlocks.blockStygianIron,
                ModBlocks.blockStygianIronOre,
                ModBlocks.blockImporter,
                ModBlocks.blockExporter
        };

        for (Block b : blocks)
            event.getRegistry().register(new ItemBlock(b).setRegistryName(b.getRegistryName()));

        event.getRegistry().registerAll(
                new ItemBlockController(ModBlocks.blockFactoryController).setRegistryName(ModBlocks.blockFactoryController.getRegistryName()),
                new ItemBlockStructure(ModBlocks.blockStructure).setRegistryName(ModBlocks.blockStructure.getRegistryName()),
                new ItemBlockCell(ModBlocks.blockCell).setRegistryName(ModBlocks.blockCell.getRegistryName()),
                new ItemBlockUpgrade(ModBlocks.blockUpgrade).setRegistryName(ModBlocks.blockUpgrade.getRegistryName()),
                new ItemBlockUpgradeB(ModBlocks.blockUpgradeB).setRegistryName(ModBlocks.blockUpgradeB.getRegistryName())
        );
    }

    @SubscribeEvent
    public void onRegisterEnchantments(RegistryEvent.Register<Enchantment> event) {

        event.getRegistry().register(new EnchantmentHeadhunter());

    }
}
