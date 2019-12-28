package ipsis.woot.modules.layout;

import ipsis.woot.Woot;
import ipsis.woot.modules.layout.blocks.LayoutBlock;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LayoutSetup {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.LOGGER.info("LayoutSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final String LAYOUT_TAG = "layout";
    public static final RegistryObject<LayoutBlock> LAYOUT_BLOCK = BLOCKS.register(
            LAYOUT_TAG, () -> new LayoutBlock());
    public static final RegistryObject<Item> LAYOUT_BLOCK_ITEM = ITEMS.register(
            LAYOUT_TAG, () ->
                    new BlockItem(LAYOUT_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> LAYOUT_BLOCK_TILE = TILES.register(
            LAYOUT_TAG, () ->
                    TileEntityType.Builder.create(LayoutTileEntity::new, LAYOUT_BLOCK.get()).build(null));
}
