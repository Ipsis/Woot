package ipsis.woot.modules.infuser;

import ipsis.woot.Woot;
import ipsis.woot.modules.infuser.blocks.InfuserBlock;
import ipsis.woot.modules.infuser.blocks.InfuserContainer;
import ipsis.woot.modules.infuser.blocks.InfuserTileEntity;
import ipsis.woot.modules.infuser.items.DyeShardItem;
import ipsis.woot.modules.squeezer.blocks.SqueezerContainer;
import ipsis.woot.modules.squeezer.blocks.SqueezerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InfuserSetup {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.LOGGER.info("InfuserSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final String INFUSER_TAG = "infuser";
    public static final RegistryObject<InfuserBlock> INFUSER_BLOCK = BLOCKS.register(
            INFUSER_TAG, () -> new InfuserBlock());
    public static final RegistryObject<Item> INFUSER_BLOCK_ITEM = ITEMS.register(
            INFUSER_TAG, () ->
                    new BlockItem(INFUSER_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> INFUSER_BLOCK_TILE = TILES.register(
            INFUSER_TAG, () ->
                    TileEntityType.Builder.create(InfuserTileEntity::new, INFUSER_BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<InfuserContainer>> INFUSER_BLOCK_CONTAINER = CONTAINERS.register(
            INFUSER_TAG, () ->
                    IForgeContainerType.create((windowId, inv, data) -> new InfuserContainer(
                            windowId,
                            Minecraft.getInstance().world,
                            data.readBlockPos(),
                            inv,
                            Minecraft.getInstance().player)));

    /**
     * Dye Shards
     */
    public static final RegistryObject<DyeShardItem> WHITE_DYE_SHARD_ITEM = ITEMS.register(
            DyeColor.WHITE.getTranslationKey() + "_dyeshard", () -> new DyeShardItem(DyeColor.WHITE));
    public static final RegistryObject<DyeShardItem> ORANGE_DYE_SHARD_ITEM = ITEMS.register(
            DyeColor.ORANGE.getTranslationKey() + "_dyeshard", () -> new DyeShardItem(DyeColor.ORANGE));
    public static final RegistryObject<DyeShardItem> MAGENTA_DYE_SHARD_ITEM = ITEMS.register(
            DyeColor.MAGENTA.getTranslationKey() + "_dyeshard", () -> new DyeShardItem(DyeColor.MAGENTA));
    public static final RegistryObject<DyeShardItem> LIGHT_BLUE_DYE_SHARD_ITEM = ITEMS.register(
            DyeColor.LIGHT_BLUE.getTranslationKey() + "_dyeshard", () -> new DyeShardItem(DyeColor.LIGHT_BLUE));
    public static final RegistryObject<DyeShardItem> YELLOW_DYE_SHARD_ITEM = ITEMS.register(
            DyeColor.YELLOW.getTranslationKey() + "_dyeshard", () -> new DyeShardItem(DyeColor.YELLOW));
    public static final RegistryObject<DyeShardItem> LIME_DYE_SHARD_ITEM = ITEMS.register(
            DyeColor.LIME.getTranslationKey() + "_dyeshard", () -> new DyeShardItem(DyeColor.LIME));

    /**
     * Dye Casings
     */
    public static final RegistryObject<DyeShardItem> WHITE_DYE_CASING_ITEM = ITEMS.register(
            DyeColor.WHITE.getTranslationKey() + "_dyecasing", () -> new DyeShardItem(DyeColor.WHITE));
    public static final RegistryObject<DyeShardItem> ORANGE_DYE_CASING_ITEM = ITEMS.register(
            DyeColor.ORANGE.getTranslationKey() + "_dyecasing", () -> new DyeShardItem(DyeColor.ORANGE));
    public static final RegistryObject<DyeShardItem> MAGENTA_DYE_CASING_ITEM = ITEMS.register(
            DyeColor.MAGENTA.getTranslationKey() + "_dyecasing", () -> new DyeShardItem(DyeColor.MAGENTA));
    public static final RegistryObject<DyeShardItem> LIGHT_BLUE_DYE_CASING_ITEM = ITEMS.register(
            DyeColor.LIGHT_BLUE.getTranslationKey() + "_dyecasing", () -> new DyeShardItem(DyeColor.LIGHT_BLUE));
    public static final RegistryObject<DyeShardItem> YELLOW_DYE_CASING_ITEM = ITEMS.register(
            DyeColor.YELLOW.getTranslationKey() + "_dyecasing", () -> new DyeShardItem(DyeColor.YELLOW));
    public static final RegistryObject<DyeShardItem> LIME_DYE_CASING_ITEM = ITEMS.register(
            DyeColor.LIME.getTranslationKey() + "_dyecasing", () -> new DyeShardItem(DyeColor.LIME));
}
