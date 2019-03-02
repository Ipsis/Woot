package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.debug.ItemDebug;
import ipsis.woot.enchantment.EnchantmentHeadhunter;
import ipsis.woot.factory.*;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.layout.BlockLayout;
import ipsis.woot.layout.TileEntityLayout;
import ipsis.woot.tools.ItemHammer;
import ipsis.woot.tools.ItemIntern;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Woot.LOGGER.info("Register blocks");

        event.getRegistry().register(new BlockLayout());
        event.getRegistry().register(new BlockFactory(FactoryBlock.BLAZE));
        event.getRegistry().register(new BlockFactory(FactoryBlock.BONE));
        event.getRegistry().register(new BlockFactory(FactoryBlock.ENDER));
        event.getRegistry().register(new BlockFactory(FactoryBlock.FLESH));
        event.getRegistry().register(new BlockFactory(FactoryBlock.NETHER));
        event.getRegistry().register(new BlockFactory(FactoryBlock.REDSTONE));
        event.getRegistry().register(new BlockFactory(FactoryBlock.UPGRADE));
        event.getRegistry().register(new BlockFactory(FactoryBlock.CAP_1));
        event.getRegistry().register(new BlockFactory(FactoryBlock.CAP_2));
        event.getRegistry().register(new BlockFactory(FactoryBlock.CAP_3));
        event.getRegistry().register(new BlockFactory(FactoryBlock.CAP_4));
        event.getRegistry().register(new BlockHeart());
        event.getRegistry().register(new BlockController());
        event.getRegistry().register(new BlockImport());
        event.getRegistry().register(new BlockExport());
        event.getRegistry().register(new BlockCell(FactoryBlock.CELL_1));
        event.getRegistry().register(new BlockCell(FactoryBlock.CELL_2));
        event.getRegistry().register(new BlockCell(FactoryBlock.CELL_3));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Woot.LOGGER.info("Register items");

        event.getRegistry().register(new ItemDebug());
        event.getRegistry().register(new ItemHammer());
        event.getRegistry().register(new ItemIntern());
        event.getRegistry().register(new ItemBlock(ModBlocks.layoutBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.layoutBlock.getRegistryName()));

        event.getRegistry().register(new ItemBlock(ModBlocks.blazeFactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.blazeFactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.boneFactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.boneFactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.enderFactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.enderFactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.fleshFactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.fleshFactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.netherFactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.netherFactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.redstoneFactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.redstoneFactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.upgradeFactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.upgradeFactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cap1FactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.cap1FactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cap2FactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.cap2FactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cap3FactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.cap3FactoryBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cap4FactoryBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.cap4FactoryBlock.getRegistryName()));

        event.getRegistry().register(new ItemBlock(ModBlocks.heartBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.heartBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.controllerBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.controllerBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.importBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.importBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.exportBlock, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.exportBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cell1Block, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.cell1Block.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cell2Block, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.cell2Block.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cell3Block, new Item.Properties().group(Woot.TAB_WOOT)).setRegistryName(ModBlocks.cell3Block.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        Woot.LOGGER.info("Register tile entities");

        event.getRegistry().register(TileEntityType.Builder.create(TileEntityLayout::new).build(null).setRegistryName(Woot.MODID, BlockLayout.BASENAME));
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        Woot.LOGGER.info("Register enchantments");

        event.getRegistry().register(new EnchantmentHeadhunter());
    }

}
