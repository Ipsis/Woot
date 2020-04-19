package ipsis.woot.modules.generator;

import ipsis.woot.Woot;
import ipsis.woot.modules.generator.blocks.ConatusGeneratorBlock;
import ipsis.woot.modules.generator.blocks.ConatusGeneratorContainer;
import ipsis.woot.modules.generator.blocks.ConatusGeneratorTileEntity;
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

public class GeneratorSetup {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("GeneratorSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final String CONATUS_GENERATOR_TAG = "conatusgenerator";
    public static final RegistryObject<ConatusGeneratorBlock> CONATUS_GENERATOR_BLOCK = BLOCKS.register(
            CONATUS_GENERATOR_TAG, () -> new ConatusGeneratorBlock());
    public static final RegistryObject<Item> CONATUS_GENERATOR_ITEM = ITEMS.register(
            CONATUS_GENERATOR_TAG, () ->
                    new BlockItem(CONATUS_GENERATOR_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> CONATUS_GENERATOR_BLOCK_TILE = TILES.register(
            CONATUS_GENERATOR_TAG, () ->
                    TileEntityType.Builder.create(ConatusGeneratorTileEntity::new,
                            CONATUS_GENERATOR_BLOCK.get()).build((null)));
    public static final RegistryObject<ContainerType<ConatusGeneratorContainer>> CONATUS_GENERATOR_BLOCK_CONTAINER = CONTAINERS.register(
            CONATUS_GENERATOR_TAG, () ->
                    IForgeContainerType.create((windowId, inv, data) -> new ConatusGeneratorContainer(
                            windowId,
                            Minecraft.getInstance().world,
                            data.readBlockPos(),
                            inv,
                            Minecraft.getInstance().player)));
}
