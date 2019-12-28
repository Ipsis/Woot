package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.client.ui.OracleContainer;
import ipsis.woot.debug.DebugItem;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryUpgrade;
import ipsis.woot.factory.blocks.*;
import ipsis.woot.factory.blocks.heart.HeartBlock;
import ipsis.woot.factory.blocks.heart.HeartContainer;
import ipsis.woot.factory.blocks.heart.HeartTileEntity;
import ipsis.woot.factory.blocks.power.*;
import ipsis.woot.factory.blocks.power.convertors.TickConverterBlock;
import ipsis.woot.factory.blocks.power.convertors.TickConverterTileEntity;
import ipsis.woot.factory.items.UpgradeItem;
import ipsis.woot.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.misc.anvil.AnvilBlock;
import ipsis.woot.misc.anvil.AnvilTileEntity;
import ipsis.woot.misc.OracleBlock;
import ipsis.woot.misc.OracleTileEntity;
import ipsis.woot.shards.MobShardItem;
import ipsis.woot.simulation.dimension.TartarusModDimension;
import ipsis.woot.tools.InternItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ipsis.woot.mod.ModDimensions.TARTARUS_DIMENSION_ID;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        Woot.LOGGER.info("registerBlocks");
        event.getRegistry().register(new HeartBlock());
        event.getRegistry().register(new ControllerBlock());
        event.getRegistry().register(new LayoutBlock());
        event.getRegistry().register(new OracleBlock());
        event.getRegistry().register(new AnvilBlock());
        event.getRegistry().register(new CellBlock(CellBlock.CELL_1_REGNAME, Cell1TileEntity.class));
        event.getRegistry().register(new CellBlock(CellBlock.CELL_2_REGNAME, Cell2TileEntity.class));
        event.getRegistry().register(new CellBlock(CellBlock.CELL_3_REGNAME, Cell3TileEntity.class));
        event.getRegistry().register(new TickConverterBlock());

        event.getRegistry().register(new FactoryBlock(FactoryComponent.FACTORY_A, FactoryBlock.FACTORY_A_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.FACTORY_B, FactoryBlock.FACTORY_B_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.FACTORY_C, FactoryBlock.FACTORY_C_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.FACTORY_D, FactoryBlock.FACTORY_D_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.FACTORY_E, FactoryBlock.FACTORY_E_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.CAP_A, FactoryBlock.CAP_A_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.CAP_B, FactoryBlock.CAP_B_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.CAP_C, FactoryBlock.CAP_C_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.CAP_D, FactoryBlock.CAP_D_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.FACTORY_CONNECT, FactoryBlock.FACTORY_CONNECT_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.FACTORY_CTR_BASE, FactoryBlock.FACTORY_CTR_BASE_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.IMPORT, FactoryBlock.IMPORT_REGNAME));
        event.getRegistry().register(new FactoryBlock(FactoryComponent.EXPORT, FactoryBlock.EXPORT_REGNAME));
        event.getRegistry().register(new UpgradeBlock(FactoryComponent.FACTORY_UPGRADE));
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Woot.LOGGER.info("registerItems");

        Item.Properties properties = new Item.Properties().group(Woot.itemGroup);

        event.getRegistry().register(new DebugItem());
        event.getRegistry().register(new InternItem());
        event.getRegistry().register(new MobShardItem());

        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.CAPACITY_1, UpgradeItem.CAPACITY_1_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.CAPACITY_2, UpgradeItem.CAPACITY_2_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.CAPACITY_3, UpgradeItem.CAPACITY_3_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.EFFICIENCY_1, UpgradeItem.EFFICIENCY_1_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.EFFICIENCY_2, UpgradeItem.EFFICIENCY_2_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.EFFICIENCY_3, UpgradeItem.EFFICIENCY_3_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.LOOTING_1, UpgradeItem.LOOTING_1_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.LOOTING_2, UpgradeItem.LOOTING_2_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.LOOTING_3, UpgradeItem.LOOTING_3_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.MASS_1, UpgradeItem.MASS_1_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.MASS_2, UpgradeItem.MASS_2_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.MASS_3, UpgradeItem.MASS_3_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.RATE_1, UpgradeItem.RATE_1_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.RATE_2, UpgradeItem.RATE_2_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.RATE_3, UpgradeItem.RATE_3_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.XP_1, UpgradeItem.XP_1_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.XP_2, UpgradeItem.XP_2_REGNAME));
        event.getRegistry().register(new UpgradeItem(FactoryUpgrade.XP_3, UpgradeItem.XP_3_REGNAME));

        event.getRegistry().register(new BlockItem(ModBlocks.ORACLE_BLOCK, properties).setRegistryName(Woot.MODID, OracleBlock.REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.ANVIL_BLOCK, properties).setRegistryName(Woot.MODID, AnvilBlock.REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.CONTROLLER_BLOCK, properties).setRegistryName(Woot.MODID, ControllerBlock.REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.HEART_BLOCK, properties).setRegistryName(Woot.MODID, HeartBlock.REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.LAYOUT_BLOCK, properties).setRegistryName(Woot.MODID, LayoutBlock.REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.TICK_CONVERTER_BLOCK, properties).setRegistryName(Woot.MODID, TickConverterBlock.REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.CELL_1_BLOCK, properties).setRegistryName(Woot.MODID, CellBlock.CELL_1_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.CELL_2_BLOCK, properties).setRegistryName(Woot.MODID, CellBlock.CELL_2_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.CELL_3_BLOCK, properties).setRegistryName(Woot.MODID, CellBlock.CELL_3_REGNAME));

        event.getRegistry().register(new BlockItem(ModBlocks.FACTORY_A_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.FACTORY_A_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.FACTORY_B_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.FACTORY_B_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.FACTORY_C_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.FACTORY_C_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.FACTORY_D_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.FACTORY_D_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.FACTORY_E_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.FACTORY_E_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.CAP_A_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.CAP_A_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.CAP_B_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.CAP_B_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.CAP_C_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.CAP_C_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.CAP_D_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.CAP_D_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.FACTORY_CONNECT_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.FACTORY_CONNECT_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.FACTORY_CTR_BASE_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.FACTORY_CTR_BASE_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.IMPORT_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.IMPORT_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.EXPORT_BLOCK, properties).setRegistryName(Woot.MODID, FactoryBlock.EXPORT_REGNAME));
        event.getRegistry().register(new BlockItem(ModBlocks.FACTORY_UPGRADE_BLOCK, properties).setRegistryName(Woot.MODID, UpgradeBlock.REGNAME));
    }

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        Woot.LOGGER.info("registerTileEntities");
        event.getRegistry().register(TileEntityType.Builder.create(OracleTileEntity::new, ModBlocks.ORACLE_BLOCK).build(null).setRegistryName(Woot.MODID, OracleBlock.REGNAME));
        event.getRegistry().register(TileEntityType.Builder.create(AnvilTileEntity::new, ModBlocks.ANVIL_BLOCK).build(null).setRegistryName(Woot.MODID, AnvilBlock.REGNAME));
        event.getRegistry().register(TileEntityType.Builder.create(HeartTileEntity::new, ModBlocks.HEART_BLOCK).build(null).setRegistryName(Woot.MODID, HeartBlock.REGNAME));
        event.getRegistry().register(TileEntityType.Builder.create(ControllerTileEntity::new, ModBlocks.CONTROLLER_BLOCK).build(null).setRegistryName(Woot.MODID, ControllerBlock.REGNAME));
        event.getRegistry().register(TileEntityType.Builder.create(LayoutTileEntity::new, ModBlocks.LAYOUT_BLOCK).build(null).setRegistryName(Woot.MODID, LayoutBlock.REGNAME));
        event.getRegistry().register(TileEntityType.Builder.create(UpgradeTileEntity::new, ModBlocks.FACTORY_UPGRADE_BLOCK).build(null).setRegistryName(Woot.MODID, UpgradeBlock.REGNAME));
        event.getRegistry().register(TileEntityType.Builder.create(TickConverterTileEntity::new, ModBlocks.TICK_CONVERTER_BLOCK).build(null).setRegistryName(Woot.MODID, TickConverterBlock.REGNAME));
        event.getRegistry().register(TileEntityType.Builder.create(Cell1TileEntity::new, ModBlocks.CELL_1_BLOCK).build(null).setRegistryName(Woot.MODID, CellBlock.CELL_1_REGNAME));
        event.getRegistry().register(TileEntityType.Builder.create(Cell2TileEntity::new, ModBlocks.CELL_2_BLOCK).build(null).setRegistryName(Woot.MODID, CellBlock.CELL_2_REGNAME));
        event.getRegistry().register(TileEntityType.Builder.create(Cell3TileEntity::new, ModBlocks.CELL_3_BLOCK).build(null).setRegistryName(Woot.MODID, CellBlock.CELL_3_REGNAME));


        event.getRegistry().register(TileEntityType.Builder.create(
                MultiBlockTileEntity::new,
                ModBlocks.IMPORT_BLOCK, ModBlocks.EXPORT_BLOCK,
                ModBlocks.FACTORY_A_BLOCK, ModBlocks.FACTORY_B_BLOCK, ModBlocks.FACTORY_C_BLOCK, ModBlocks.FACTORY_D_BLOCK, ModBlocks.FACTORY_E_BLOCK,
                ModBlocks.CAP_A_BLOCK, ModBlocks.CAP_B_BLOCK, ModBlocks.CAP_C_BLOCK, ModBlocks.CAP_D_BLOCK,
                ModBlocks.FACTORY_CONNECT_BLOCK,
                ModBlocks.FACTORY_CTR_BASE_BLOCK)
                .build(null).setRegistryName(Woot.MODID, MultiBlockTileEntity.REGNAME));
    }

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        Woot.LOGGER.info("registerContainers");
        event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new HeartContainer(windowId,
                    Minecraft.getInstance().world,
                    pos,
                    inv,
                    Minecraft.getInstance().player);
        }).setRegistryName(HeartBlock.REGNAME));

        event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new OracleContainer(windowId,
                    Minecraft.getInstance().world,
                    pos,
                    inv,
                    Minecraft.getInstance().player);
        }).setRegistryName(OracleBlock.REGNAME));
    }

    @SubscribeEvent
    public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
        Woot.LOGGER.info("registerEnchantments");
    }

    @SubscribeEvent
    public static void registerDimensions(final RegistryEvent.Register<ModDimension> event) {
        Woot.LOGGER.info("registerDimensions");
        event.getRegistry().register(new TartarusModDimension().setRegistryName(TARTARUS_DIMENSION_ID));
    }
}
