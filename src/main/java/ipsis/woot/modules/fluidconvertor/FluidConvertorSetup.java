package ipsis.woot.modules.fluidconvertor;

import ipsis.woot.Woot;
import ipsis.woot.modules.fluidconvertor.blocks.FluidConvertorBlock;
import ipsis.woot.modules.fluidconvertor.blocks.FluidConvertorContainer;
import ipsis.woot.modules.fluidconvertor.blocks.FluidConvertorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidConvertorSetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("FluidConvertor: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final String FLUID_CONVERTOR_TAG = "fluidconvertor";
    public static final RegistryObject<FluidConvertorBlock> FLUID_CONVERTOR_BLOCK = BLOCKS.register(
            FLUID_CONVERTOR_TAG, () -> new FluidConvertorBlock());
    public static final RegistryObject<Item> FLUID_CONVERTOR_ITEM = ITEMS.register(
            FLUID_CONVERTOR_TAG, () ->
                    new BlockItem(FLUID_CONVERTOR_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> FLUID_CONVERTOR_BLOCK_TILE = TILES.register(
            FLUID_CONVERTOR_TAG, () ->
                    TileEntityType.Builder.create(FluidConvertorTileEntity::new,
                            FLUID_CONVERTOR_BLOCK.get()).build((null)));
    public static final RegistryObject<ContainerType<FluidConvertorContainer>> FLUID_CONVERTOR_BLOCK_CONTATAINER = CONTAINERS.register(
            FLUID_CONVERTOR_TAG, () ->
                    IForgeContainerType.create((windowId, inv, data) -> {
                        return new FluidConvertorContainer(
                                windowId,
                                Woot.proxy.getClientWorld(),
                                data.readBlockPos(),
                                inv,
                                Woot.proxy.getClientPlayer());
                    }));
}
