package ipsis.woot.modules.squeezer;

import ipsis.woot.Woot;
import ipsis.woot.modules.squeezer.blocks.*;
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

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("SqueezerSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final String SQUEEZER_TAG = "squeezer";
    public static final RegistryObject<DyeSqueezerBlock> SQUEEZER_BLOCK = BLOCKS.register(
            SQUEEZER_TAG, () -> new DyeSqueezerBlock());
    public static final RegistryObject<Item> SQUEEZER_BLOCK_ITEM = ITEMS.register(
            SQUEEZER_TAG, () ->
                    new BlockItem(SQUEEZER_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> SQUEEZER_BLOCK_TILE = TILES.register(
            SQUEEZER_TAG, () ->
                    TileEntityType.Builder.create(DyeSqueezerTileEntity::new, SQUEEZER_BLOCK.get()).build(null));

    public static final RegistryObject<ContainerType<DyeSqueezerContainer>> SQUEEZER_BLOCK_CONTAINER = CONTAINERS.register(
            SQUEEZER_TAG, () ->
                    IForgeContainerType.create((windowId, inv, data) -> {
                        return new DyeSqueezerContainer(
                                windowId,
                                Woot.proxy.getClientWorld(),
                                data.readBlockPos(),
                                inv,
                                Woot.proxy.getClientPlayer());
                    }));

    public static final String ENCHANT_SQUEEZER_TAG = "enchsqueezer";
    public static final RegistryObject<EnchantSqueezerBlock> ENCHANT_SQUEEZER_BLOCK = BLOCKS.register(
            ENCHANT_SQUEEZER_TAG, () -> new EnchantSqueezerBlock());
    public static final RegistryObject<Item> ENCHANT_SQUEEZER_BLOCK_ITEM = ITEMS.register(
            ENCHANT_SQUEEZER_TAG, () ->
                    new BlockItem(ENCHANT_SQUEEZER_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> ENCHANT_SQUEEZER_BLOCK_TILE = TILES.register(
            ENCHANT_SQUEEZER_TAG, () ->
                    TileEntityType.Builder.create(EnchantSqueezerTileEntity::new, ENCHANT_SQUEEZER_BLOCK.get()).build(null));

    public static final RegistryObject<ContainerType<EnchantSqueezerContainer>> ENCHANT_SQUEEZER_BLOCK_CONTAINER = CONTAINERS.register(
            ENCHANT_SQUEEZER_TAG, () ->
                    IForgeContainerType.create((windowId, inv, data) -> {
                        return new EnchantSqueezerContainer(
                                windowId,
                                Woot.proxy.getClientWorld(),
                                data.readBlockPos(),
                                inv,
                                Woot.proxy.getClientPlayer());
                    }));
}
