package ipsis.woot.modules.anvil;

import ipsis.woot.Woot;
import ipsis.woot.modules.anvil.blocks.AnvilBlock;
import ipsis.woot.modules.anvil.blocks.AnvilTileEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AnvilSetup {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.LOGGER.info("AnvilSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final String ANVIL_TAG = "anvil";
    public static final RegistryObject<AnvilBlock> ANVIL_BLOCK = BLOCKS.register(
            ANVIL_TAG, () -> new AnvilBlock());
    public static final RegistryObject<Item> ANVIL_BLOCK_ITEM = ITEMS.register(
            ANVIL_TAG, () ->
                    new BlockItem(ANVIL_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> ANVIL_BLOCK_TILE = TILES.register(
            ANVIL_TAG, () ->
                    TileEntityType.Builder.create(AnvilTileEntity::new, ANVIL_BLOCK.get()).build(null));
}
