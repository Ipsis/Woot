package ipsis.woot.modules.debug;

import ipsis.woot.Woot;
import ipsis.woot.modules.debug.blocks.*;
import ipsis.woot.modules.debug.items.DebugItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DebugSetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("DebugSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<DebugItem> DEBUG_ITEM = ITEMS.register(
            "debug", () -> new DebugItem());

    public static final String CREATIVE_POWER_TAG = "creative_power";
    public static final RegistryObject<CreativePowerBlock> CREATIVE_POWER_BLOCK = BLOCKS.register(
            CREATIVE_POWER_TAG, () -> new CreativePowerBlock());
    public static final RegistryObject<Item> CREATIVE_POWER_BLOCK_ITEM = ITEMS.register(
            CREATIVE_POWER_TAG, () ->
                    new BlockItem(CREATIVE_POWER_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> CREATIVE_POWER_BLOCK_TILE = TILES.register(
            CREATIVE_POWER_TAG, () ->
                    TileEntityType.Builder.create(CreativePowerTileEntity::new, CREATIVE_POWER_BLOCK.get()).build(null));

    public static final String CREATIVE_CONATUS_TAG = "creative_conatus";
    public static final RegistryObject<TickConverterBlock> CREATIVE_CONATUS_BLOCK = BLOCKS.register(
            CREATIVE_CONATUS_TAG, () -> new TickConverterBlock());
    public static final RegistryObject<Item> CREATIVE_CONATUS_BLOCK_ITEM = ITEMS.register(
            CREATIVE_CONATUS_TAG, () ->
                    new BlockItem(CREATIVE_CONATUS_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> CREATIVE_CONATUS_BLOCK_TILE = TILES.register(
            CREATIVE_CONATUS_TAG, () ->
                    TileEntityType.Builder.create(TickConverterTileEntity::new, CREATIVE_CONATUS_BLOCK.get()).build(null));

    public static final String DEBUG_TANK_TAG = "debug_tank";
    public static final RegistryObject<DebugTankBlock> DEBUG_TANK_BLOCK = BLOCKS.register(
            DEBUG_TANK_TAG, () -> new DebugTankBlock());
    public static final RegistryObject<Item> DEBUG_TANK_BLOCK_ITEM = ITEMS.register(
            DEBUG_TANK_TAG, () ->
                    new BlockItem(DEBUG_TANK_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> DEBUG_TANK_BLOCK_TILE = TILES.register(
            DEBUG_TANK_TAG, () ->
                    TileEntityType.Builder.create(DebugTankTileEntity::new, DEBUG_TANK_BLOCK.get()).build(null));
}
