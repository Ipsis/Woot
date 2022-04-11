package ipsis.woot.modules.factory;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.blocks.*;
import ipsis.woot.modules.factory.blocks.HeartBlock;
import ipsis.woot.modules.factory.blocks.HeartContainer;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.factory.blocks.Cell1TileEntity;
import ipsis.woot.modules.factory.blocks.Cell2TileEntity;
import ipsis.woot.modules.factory.blocks.Cell3TileEntity;
import ipsis.woot.modules.factory.blocks.CellBlock;
import ipsis.woot.modules.factory.items.ControllerBlockItem;
import ipsis.woot.modules.factory.items.MobShardItem;
import ipsis.woot.modules.factory.items.PerkItem;
import ipsis.woot.modules.factory.items.XpShardBaseItem;
import ipsis.woot.modules.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.modules.factory.perks.Perk;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FactorySetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("FactorySetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final String HEART_TAG = "heart";
    public static final RegistryObject<HeartBlock> HEART_BLOCK = BLOCKS.register(
            HEART_TAG, () -> new HeartBlock());
    public static final RegistryObject<Item> HEART_BLOCK_ITEM = ITEMS.register(
            HEART_TAG, () ->
                    new BlockItem(HEART_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> HEART_BLOCK_TILE = TILES.register(
            HEART_TAG, () ->
                    TileEntityType.Builder.create(HeartTileEntity::new, HEART_BLOCK.get()).build(null));

    public static final RegistryObject<ContainerType<HeartContainer>> HEART_BLOCK_CONTAINER = CONTAINERS.register(
            HEART_TAG, () ->
                    IForgeContainerType.create((windowId, inv, data) -> {
                        return new HeartContainer(
                                windowId,
                                Woot.proxy.getClientWorld(),
                                data.readBlockPos(),
                                inv,
                                Woot.proxy.getClientPlayer());
                    }));

    public static final String CONTROLLER_TAG = "controller";
    public static final RegistryObject<ControllerBlock> CONTROLLER_BLOCK = BLOCKS.register(
            CONTROLLER_TAG, () -> new ControllerBlock());
    public static final RegistryObject<Item> CONTROLLER_BLOCK_ITEM = ITEMS.register(
            CONTROLLER_TAG, () ->
                    new ControllerBlockItem(CONTROLLER_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> CONTROLLER_BLOCK_TILE = TILES.register(
            CONTROLLER_TAG, () ->
                    TileEntityType.Builder.create(ControllerTileEntity::new, CONTROLLER_BLOCK.get()).build(null));

    public static final RegistryObject<FactoryBlock> FACTORY_A_BLOCK = BLOCKS.register(
            FactoryBlock.FACTORY_A_REGNAME, () -> new FactoryBlock(FactoryComponent.FACTORY_A));
    public static final RegistryObject<Item> FACTORY_A_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.FACTORY_A_REGNAME, () ->
                    new BlockItem(FACTORY_A_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> FACTORY_B_BLOCK = BLOCKS.register(
            FactoryBlock.FACTORY_B_REGNAME, () -> new FactoryBlock(FactoryComponent.FACTORY_B));
    public static final RegistryObject<Item> FACTORY_B_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.FACTORY_B_REGNAME, () ->
                    new BlockItem(FACTORY_B_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> FACTORY_C_BLOCK = BLOCKS.register(
            FactoryBlock.FACTORY_C_REGNAME, () -> new FactoryBlock(FactoryComponent.FACTORY_C));
    public static final RegistryObject<Item> FACTORY_C_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.FACTORY_C_REGNAME, () ->
                    new BlockItem(FACTORY_C_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> FACTORY_D_BLOCK = BLOCKS.register(
            FactoryBlock.FACTORY_D_REGNAME, () -> new FactoryBlock(FactoryComponent.FACTORY_D));
    public static final RegistryObject<Item> FACTORY_D_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.FACTORY_D_REGNAME, () ->
                    new BlockItem(FACTORY_D_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> FACTORY_E_BLOCK = BLOCKS.register(
            FactoryBlock.FACTORY_E_REGNAME, () -> new FactoryBlock(FactoryComponent.FACTORY_E));
    public static final RegistryObject<Item> FACTORY_E_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.FACTORY_E_REGNAME, () ->
                    new BlockItem(FACTORY_E_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> CAP_A_BLOCK = BLOCKS.register(
            FactoryBlock.CAP_A_REGNAME, () -> new FactoryBlock(FactoryComponent.CAP_A));
    public static final RegistryObject<Item> CAP_A_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.CAP_A_REGNAME, () ->
                    new BlockItem(CAP_A_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> CAP_B_BLOCK = BLOCKS.register(
            FactoryBlock.CAP_B_REGNAME, () -> new FactoryBlock(FactoryComponent.CAP_B));
    public static final RegistryObject<Item> CAP_B_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.CAP_B_REGNAME, () ->
                    new BlockItem(CAP_B_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> CAP_C_BLOCK = BLOCKS.register(
            FactoryBlock.CAP_C_REGNAME, () -> new FactoryBlock(FactoryComponent.CAP_C));
    public static final RegistryObject<Item> CAP_C_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.CAP_C_REGNAME, () ->
                    new BlockItem(CAP_C_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> CAP_D_BLOCK = BLOCKS.register(
            FactoryBlock.CAP_D_REGNAME, () -> new FactoryBlock(FactoryComponent.CAP_D));
    public static final RegistryObject<Item> CAP_D_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.CAP_D_REGNAME, () ->
                    new BlockItem(CAP_D_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> FACTORY_CONNECT_BLOCK = BLOCKS.register(
            FactoryBlock.FACTORY_CONNECT_REGNAME, () -> new FactoryBlock(FactoryComponent.FACTORY_CONNECT));
    public static final RegistryObject<Item> FACTORY_CONNECT_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.FACTORY_CONNECT_REGNAME, () ->
                    new BlockItem(FACTORY_CONNECT_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> FACTORY_CTR_BASE_PRI_BLOCK = BLOCKS.register(
            FactoryBlock.FACTORY_CTR_BASE_PRI_REGNAME, () -> new FactoryBlock(FactoryComponent.FACTORY_CTR_BASE_PRI));
    public static final RegistryObject<Item> FACTORY_CTR_BASE_PRI_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.FACTORY_CTR_BASE_PRI_REGNAME, () ->
                    new BlockItem(FACTORY_CTR_BASE_PRI_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> FACTORY_CTR_BASE_SEC_BLOCK = BLOCKS.register(
            FactoryBlock.FACTORY_CTR_BASE_SEC_REGNAME, () -> new FactoryBlock(FactoryComponent.FACTORY_CTR_BASE_SEC));
    public static final RegistryObject<Item> FACTORY_CTR_BASE_SEC_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.FACTORY_CTR_BASE_SEC_REGNAME, () ->
                    new BlockItem(FACTORY_CTR_BASE_SEC_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> IMPORT_BLOCK = BLOCKS.register(
            FactoryBlock.IMPORT_REGNAME, () -> new FactoryBlock(FactoryComponent.IMPORT));
    public static final RegistryObject<Item> IMPORT_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.IMPORT_REGNAME, () ->
                    new BlockItem(IMPORT_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<FactoryBlock> EXPORT_BLOCK = BLOCKS.register(
            FactoryBlock.EXPORT_REGNAME, () -> new FactoryBlock(FactoryComponent.EXPORT));
    public static final RegistryObject<Item> EXPORT_BLOCK_ITEM = ITEMS.register(
            FactoryBlock.EXPORT_REGNAME, () ->
                    new BlockItem(EXPORT_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<TileEntityType<?>> MULTIBLOCK_BLOCK_TILE = TILES.register(
            "multiblock", () ->
                    TileEntityType.Builder.create(MultiBlockTileEntity::new,
                            IMPORT_BLOCK.get(),
                            EXPORT_BLOCK.get(),
                            FACTORY_A_BLOCK.get(),
                            FACTORY_B_BLOCK.get(),
                            FACTORY_C_BLOCK.get(),
                            FACTORY_D_BLOCK.get(),
                            FACTORY_E_BLOCK.get(),
                            CAP_A_BLOCK.get(),
                            CAP_B_BLOCK.get(),
                            CAP_C_BLOCK.get(),
                            CAP_D_BLOCK.get(),
                            FACTORY_CONNECT_BLOCK.get(),
                            FACTORY_CTR_BASE_PRI_BLOCK.get(),
                            FACTORY_CTR_BASE_SEC_BLOCK.get()
                            ).build(null));

    public static final String FACTORY_UPGRADE_TAG = "factory_upgrade";
    public static final RegistryObject<UpgradeBlock> FACTORY_UPGRADE_BLOCK = BLOCKS.register(
            FACTORY_UPGRADE_TAG, () -> new UpgradeBlock(FactoryComponent.FACTORY_UPGRADE));
    public static final RegistryObject<Item> FACTORY_UPGRADE_BLOCK_ITEM = ITEMS.register(
            FACTORY_UPGRADE_TAG, () ->
                    new BlockItem(FACTORY_UPGRADE_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> FACTORY_UPGRADE_BLOCK_TILE = TILES.register(
            FACTORY_UPGRADE_TAG, () ->
                    TileEntityType.Builder.create(UpgradeTileEntity::new, FACTORY_UPGRADE_BLOCK.get()).build(null));

    public static final String CELL_1_TAG = "cell_1";
    public static final RegistryObject<CellBlock> CELL_1_BLOCK = BLOCKS.register(
            CELL_1_TAG, () -> new CellBlock(Cell1TileEntity.class));
    public static final RegistryObject<Item> CELL_1_BLOCK_ITEM = ITEMS.register(
            CELL_1_TAG, () ->
                    new BlockItem(CELL_1_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> CELL_1_BLOCK_TILE = TILES.register(
            CELL_1_TAG, () ->
                    TileEntityType.Builder.create(Cell1TileEntity::new, CELL_1_BLOCK.get()).build(null));

    public static final String CELL_2_TAG = "cell_2";
    public static final RegistryObject<CellBlock> CELL_2_BLOCK = BLOCKS.register(
            CELL_2_TAG, () -> new CellBlock(Cell2TileEntity.class));
    public static final RegistryObject<Item> CELL_2_BLOCK_ITEM = ITEMS.register(
            CELL_2_TAG, () ->
                    new BlockItem(CELL_2_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> CELL_2_BLOCK_TILE = TILES.register(
            CELL_2_TAG, () ->
                    TileEntityType.Builder.create(Cell2TileEntity::new, CELL_2_BLOCK.get()).build(null));

    public static final String CELL_3_TAG = "cell_3";
    public static final RegistryObject<CellBlock> CELL_3_BLOCK = BLOCKS.register(
            CELL_3_TAG, () -> new CellBlock(Cell3TileEntity.class));
    public static final RegistryObject<Item> CELL_3_BLOCK_ITEM = ITEMS.register(
            CELL_3_TAG, () ->
                    new BlockItem(CELL_3_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> CELL_3_BLOCK_TILE = TILES.register(
            CELL_3_TAG, () ->
                    TileEntityType.Builder.create(Cell3TileEntity::new, CELL_3_BLOCK.get()).build(null));

    public static final String CELL_4_TAG = "cell_4";
    public static final RegistryObject<CellBlock> CELL_4_BLOCK = BLOCKS.register(
            CELL_4_TAG, () -> new CellBlock(Cell4TileEntity.class));
    public static final RegistryObject<Item> CELL_4_BLOCK_ITEM = ITEMS.register(
            CELL_4_TAG, () ->
                    new BlockItem(CELL_4_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> CELL_4_BLOCK_TILE = TILES.register(
            CELL_4_TAG, () ->
                    TileEntityType.Builder.create(Cell4TileEntity::new, CELL_4_BLOCK.get()).build(null));

    public static final RegistryObject<ExoticBlock> EXOTIC_A_BLOCK = BLOCKS.register(
            Exotic.EXOTIC_A.getName(), () -> new ExoticBlock(Exotic.EXOTIC_A));
    public static final RegistryObject<Item> EXOTIC_A_BLOCK_ITEM = ITEMS.register(
            Exotic.EXOTIC_A.getName(), () -> new BlockItem(EXOTIC_A_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<ExoticBlock> EXOTIC_B_BLOCK = BLOCKS.register(
            Exotic.EXOTIC_B.getName(), () -> new ExoticBlock(Exotic.EXOTIC_B));
    public static final RegistryObject<Item> EXOTIC_B_BLOCK_ITEM = ITEMS.register(
            Exotic.EXOTIC_B.getName(), () -> new BlockItem(EXOTIC_B_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<ExoticBlock> EXOTIC_C_BLOCK = BLOCKS.register(
            Exotic.EXOTIC_C.getName(), () -> new ExoticBlock(Exotic.EXOTIC_C));
    public static final RegistryObject<Item> EXOTIC_C_BLOCK_ITEM = ITEMS.register(
            Exotic.EXOTIC_C.getName(), () -> new BlockItem(EXOTIC_C_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<ExoticBlock> EXOTIC_D_BLOCK = BLOCKS.register(
            Exotic.EXOTIC_D.getName(), () -> new ExoticBlock(Exotic.EXOTIC_D));
    public static final RegistryObject<Item> EXOTIC_D_BLOCK_ITEM = ITEMS.register(
            Exotic.EXOTIC_D.getName(), () -> new BlockItem(EXOTIC_D_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<ExoticBlock> EXOTIC_E_BLOCK = BLOCKS.register(
            Exotic.EXOTIC_E.getName(), () -> new ExoticBlock(Exotic.EXOTIC_E));
    public static final RegistryObject<Item> EXOTIC_E_BLOCK_ITEM = ITEMS.register(
            Exotic.EXOTIC_E.getName(), () -> new BlockItem(EXOTIC_E_BLOCK.get(), Woot.createStandardProperties()));

    public static final RegistryObject<PerkItem> EFFICIENCY_1_ITEM = ITEMS.register(
            PerkItem.EFFICIENCY_1_REGNAME, () -> new PerkItem(Perk.EFFICIENCY_1));
    public static final RegistryObject<PerkItem> EFFICIENCY_2_ITEM = ITEMS.register(
            PerkItem.EFFICIENCY_2_REGNAME, () -> new PerkItem(Perk.EFFICIENCY_2));
    public static final RegistryObject<PerkItem> EFFICIENCY_3_ITEM = ITEMS.register(
            PerkItem.EFFICIENCY_3_REGNAME, () -> new PerkItem(Perk.EFFICIENCY_3));

    public static final RegistryObject<PerkItem> LOOTING_1_ITEM = ITEMS.register(
            PerkItem.LOOTING_1_REGNAME, () -> new PerkItem(Perk.LOOTING_1));
    public static final RegistryObject<PerkItem> LOOTING_2_ITEM = ITEMS.register(
            PerkItem.LOOTING_2_REGNAME, () -> new PerkItem(Perk.LOOTING_2));
    public static final RegistryObject<PerkItem> LOOTING_3_ITEM = ITEMS.register(
            PerkItem.LOOTING_3_REGNAME, () -> new PerkItem(Perk.LOOTING_3));

    public static final RegistryObject<PerkItem> MASS_1_ITEM = ITEMS.register(
            PerkItem.MASS_1_REGNAME, () -> new PerkItem(Perk.MASS_1));
    public static final RegistryObject<PerkItem> MASS_2_ITEM = ITEMS.register(
            PerkItem.MASS_2_REGNAME, () -> new PerkItem(Perk.MASS_2));
    public static final RegistryObject<PerkItem> MASS_3_ITEM = ITEMS.register(
            PerkItem.MASS_3_REGNAME, () -> new PerkItem(Perk.MASS_3));

    public static final RegistryObject<PerkItem> RATE_1_ITEM = ITEMS.register(
            PerkItem.RATE_1_REGNAME, () -> new PerkItem(Perk.RATE_1));
    public static final RegistryObject<PerkItem> RATE_2_ITEM = ITEMS.register(
            PerkItem.RATE_2_REGNAME, () -> new PerkItem(Perk.RATE_2));
    public static final RegistryObject<PerkItem> RATE_3_ITEM = ITEMS.register(
            PerkItem.RATE_3_REGNAME, () -> new PerkItem(Perk.RATE_3));

    public static final RegistryObject<PerkItem> TIER_SHARD_1_ITEM = ITEMS.register(
            PerkItem.TIER_SHARD_1_REGNAME, () -> new PerkItem(Perk.TIER_SHARD_1));
    public static final RegistryObject<PerkItem> TIER_SHARD_2_ITEM = ITEMS.register(
            PerkItem.TIER_SHARD_2_REGNAME, () -> new PerkItem(Perk.TIER_SHARD_2));
    public static final RegistryObject<PerkItem> TIER_SHARD_3_ITEM = ITEMS.register(
            PerkItem.TIER_SHARD_3_REGNAME, () -> new PerkItem(Perk.TIER_SHARD_3));

    public static final RegistryObject<PerkItem> XP_1_ITEM = ITEMS.register(
            PerkItem.XP_1_REGNAME, () -> new PerkItem(Perk.XP_1));
    public static final RegistryObject<PerkItem> XP_2_ITEM = ITEMS.register(
            PerkItem.XP_2_REGNAME, () -> new PerkItem(Perk.XP_2));
    public static final RegistryObject<PerkItem> XP_3_ITEM = ITEMS.register(
            PerkItem.XP_3_REGNAME, () -> new PerkItem(Perk.XP_3));

    public static final RegistryObject<PerkItem> HEADLESS_1_ITEM = ITEMS.register(
            PerkItem.HEADLESS_1_REGNAME, () -> new PerkItem(Perk.HEADLESS_1));
    public static final RegistryObject<PerkItem> HEADLESS_2_ITEM = ITEMS.register(
            PerkItem.HEADLESS_2_REGNAME, () -> new PerkItem(Perk.HEADLESS_2));
    public static final RegistryObject<PerkItem> HEADLESS_3_ITEM = ITEMS.register(
            PerkItem.HEADLESS_3_REGNAME, () -> new PerkItem(Perk.HEADLESS_3));

    public static final RegistryObject<PerkItem> SLAUGHTER_1_ITEM = ITEMS.register(
            PerkItem.SLAUGHTER_1_REGNAME, () -> new PerkItem(Perk.SLAUGHTER_1));
    public static final RegistryObject<PerkItem> SLAUGHTER_2_ITEM = ITEMS.register(
            PerkItem.SLAUGHTER_2_REGNAME, () -> new PerkItem(Perk.SLAUGHTER_2));
    public static final RegistryObject<PerkItem> SLAUGHTER_3_ITEM = ITEMS.register(
            PerkItem.SLAUGHTER_3_REGNAME, () -> new PerkItem(Perk.SLAUGHTER_3));

    public static final RegistryObject<PerkItem> CRUSHER_1_ITEM = ITEMS.register(
            PerkItem.CRUSHER_1_REGNAME, () -> new PerkItem(Perk.CRUSHER_1));
    public static final RegistryObject<PerkItem> CRUSHER_2_ITEM = ITEMS.register(
            PerkItem.CRUSHER_2_REGNAME, () -> new PerkItem(Perk.CRUSHER_2));
    public static final RegistryObject<PerkItem> CRUSHER_3_ITEM = ITEMS.register(
            PerkItem.CRUSHER_3_REGNAME, () -> new PerkItem(Perk.CRUSHER_3));

    public static final RegistryObject<PerkItem> LASER_1_ITEM = ITEMS.register(
            PerkItem.LASER_1_REGNAME, () -> new PerkItem(Perk.LASER_1));
    public static final RegistryObject<PerkItem> LASER_2_ITEM = ITEMS.register(
            PerkItem.LASER_2_REGNAME, () -> new PerkItem(Perk.LASER_2));
    public static final RegistryObject<PerkItem> LASER_3_ITEM = ITEMS.register(
            PerkItem.LASER_3_REGNAME, () -> new PerkItem(Perk.LASER_3));

    public static final RegistryObject<PerkItem> FLAYED_1_ITEM = ITEMS.register(
            PerkItem.FLAYED_1_REGNAME, () -> new PerkItem(Perk.FLAYED_1));
    public static final RegistryObject<PerkItem> FLAYED_2_ITEM = ITEMS.register(
            PerkItem.FLAYED_2_REGNAME, () -> new PerkItem(Perk.FLAYED_2));
    public static final RegistryObject<PerkItem> FLAYED_3_ITEM = ITEMS.register(
            PerkItem.FLAYED_3_REGNAME, () -> new PerkItem(Perk.FLAYED_3));

    public static final RegistryObject<MobShardItem> MOB_SHARD_ITEM = ITEMS.register(
            "mobshard", () -> new MobShardItem());

    public static final RegistryObject<XpShardBaseItem> XP_SHARD_ITEM = ITEMS.register(
            XpShardBaseItem.SHARD_REGNAME, () -> new XpShardBaseItem(XpShardBaseItem.Variant.SHARD));
    public static final RegistryObject<XpShardBaseItem> XP_SPLINTER_ITEM = ITEMS.register(
            XpShardBaseItem.SPLINTER_REGNAME, () -> new XpShardBaseItem(XpShardBaseItem.Variant.SPLINTER));
}
