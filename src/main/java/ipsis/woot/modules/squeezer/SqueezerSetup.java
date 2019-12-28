package ipsis.woot.modules.squeezer;

import ipsis.woot.Woot;
import ipsis.woot.modules.squeezer.blocks.SqueezerContainer;
import ipsis.woot.modules.squeezer.blocks.SqueezerBlock;
import ipsis.woot.modules.squeezer.blocks.SqueezerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SqueezerSetup {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.LOGGER.info("SqueezerSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final String SQUEEZER_TAG = "squeezer";
    public static final RegistryObject<SqueezerBlock> SQUEEZER_BLOCK = BLOCKS.register(
            SQUEEZER_TAG, () -> new SqueezerBlock());
    public static final RegistryObject<Item> SQUEEZER_BLOCK_ITEM = ITEMS.register(
            SQUEEZER_TAG, () ->
                    new BlockItem(SQUEEZER_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> SQUEEZER_BLOCK_TILE = TILES.register(
            SQUEEZER_TAG, () ->
                    TileEntityType.Builder.create(SqueezerTileEntity::new, SQUEEZER_BLOCK.get()).build(null));

    public static final RegistryObject<ContainerType<SqueezerContainer>> SQUEEZER_BLOCK_CONTAINER = CONTAINERS.register(
            SQUEEZER_TAG, () ->
                    IForgeContainerType.create((windowId, inv, data) -> {
                        return new SqueezerContainer(
                                windowId,
                                Minecraft.getInstance().world,
                                data.readBlockPos(),
                                inv,
                                Minecraft.getInstance().player);
                    }));
}
