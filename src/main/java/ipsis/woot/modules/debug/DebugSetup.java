package ipsis.woot.modules.debug;

import ipsis.woot.Woot;
import ipsis.woot.modules.debug.blocks.CreativePowerBlock;
import ipsis.woot.modules.debug.blocks.CreativePowerTileEntity;
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

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Woot.MODID);

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
}
