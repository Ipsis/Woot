package ipsis.woot.modules.infuser;

import ipsis.woot.Woot;
import ipsis.woot.modules.infuser.blocks.InfuserBlock;
import ipsis.woot.modules.infuser.blocks.InfuserContainer;
import ipsis.woot.modules.infuser.blocks.InfuserTileEntity;
import ipsis.woot.modules.infuser.items.DyeCasingItem;
import ipsis.woot.modules.infuser.items.DyePlateItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InfuserSetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("InfuserSetup: register");
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
                    IForgeContainerType.create((windowId, inv, data) -> {
                        return new InfuserContainer(
                                windowId,
                                Woot.proxy.getClientWorld(),
                                data.readBlockPos(),
                                inv,
                                Woot.proxy.getClientPlayer());
                    }));

    /**
     * Dye Shards
     */
    public static final RegistryObject<DyePlateItem> WHITE_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.WHITE);
    public static final RegistryObject<DyePlateItem> ORANGE_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.ORANGE);
    public static final RegistryObject<DyePlateItem> MAGENTA_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.MAGENTA);
    public static final RegistryObject<DyePlateItem> LIGHT_BLUE_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.LIGHT_BLUE);
    public static final RegistryObject<DyePlateItem> YELLOW_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.YELLOW);
    public static final RegistryObject<DyePlateItem> LIME_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.LIME);
    public static final RegistryObject<DyePlateItem> PINK_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.PINK);
    public static final RegistryObject<DyePlateItem> GRAY_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.GRAY);
    public static final RegistryObject<DyePlateItem> LIGHT_GRAY_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.LIGHT_GRAY);
    public static final RegistryObject<DyePlateItem> CYAN_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.CYAN);
    public static final RegistryObject<DyePlateItem> PURPLE_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.PURPLE);
    public static final RegistryObject<DyePlateItem> BLUE_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.BLUE);
    public static final RegistryObject<DyePlateItem> BROWN_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.BROWN);
    public static final RegistryObject<DyePlateItem> GREEN_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.GREEN);
    public static final RegistryObject<DyePlateItem> RED_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.RED);
    public static final RegistryObject<DyePlateItem> BLACK_DYE_PLATE_ITEM = getDyePlateItem(ITEMS, DyeColor.BLACK);

    private static RegistryObject<DyePlateItem> getDyePlateItem(DeferredRegister<Item> reg, DyeColor color) {
        return reg.register(color.getTranslationKey() + "_dyeplate", () -> new DyePlateItem(color));
    }

    /**
     * Dye Casings
     */
    public static final RegistryObject<DyeCasingItem> WHITE_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.WHITE);
    public static final RegistryObject<DyeCasingItem> ORANGE_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.ORANGE);
    public static final RegistryObject<DyeCasingItem> MAGENTA_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.MAGENTA);
    public static final RegistryObject<DyeCasingItem> LIGHT_BLUE_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.LIGHT_BLUE);
    public static final RegistryObject<DyeCasingItem> YELLOW_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.YELLOW);
    public static final RegistryObject<DyeCasingItem> LIME_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.LIME);
    public static final RegistryObject<DyeCasingItem> PINK_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.PINK);
    public static final RegistryObject<DyeCasingItem> GRAY_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.GRAY);
    public static final RegistryObject<DyeCasingItem> LIGHT_GRAY_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.LIGHT_GRAY);
    public static final RegistryObject<DyeCasingItem> CYAN_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.CYAN);
    public static final RegistryObject<DyeCasingItem> PURPLE_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.PURPLE);
    public static final RegistryObject<DyeCasingItem> BLUE_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.BLUE);
    public static final RegistryObject<DyeCasingItem> BROWN_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.BROWN);
    public static final RegistryObject<DyeCasingItem> GREEN_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.GREEN);
    public static final RegistryObject<DyeCasingItem> RED_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.RED);
    public static final RegistryObject<DyeCasingItem> BLACK_DYE_CASING_ITEM = getDyeCasingItem(ITEMS, DyeColor.BLACK);

    private static RegistryObject<DyeCasingItem> getDyeCasingItem(DeferredRegister<Item> reg, DyeColor color) {
        return reg.register(color.getTranslationKey() + "_dyecasing", () -> new DyeCasingItem(color));

    }
}
