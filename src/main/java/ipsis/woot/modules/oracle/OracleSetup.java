package ipsis.woot.modules.oracle;

import ipsis.woot.Woot;
import ipsis.woot.modules.oracle.blocks.OracleBlock;
import ipsis.woot.modules.oracle.blocks.OracleContainer;
import ipsis.woot.modules.oracle.blocks.OracleTileEntity;
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

public class OracleSetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("OracleSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final String ORACLE_TAG = "oracle";
    public static final RegistryObject<OracleBlock> ORACLE_BLOCK = BLOCKS.register(
            ORACLE_TAG, () -> new OracleBlock());
    public static final RegistryObject<Item> ORACLE_BLOCK_ITEM = ITEMS.register(
            ORACLE_TAG, () ->
                    new BlockItem(ORACLE_BLOCK.get(), Woot.createStandardProperties()));
    public static final RegistryObject<TileEntityType<?>> ORACLE_BLOCK_TILE = TILES.register(
            ORACLE_TAG, () ->
                    TileEntityType.Builder.of(OracleTileEntity::new, ORACLE_BLOCK.get()).build(null));

    public static final RegistryObject<ContainerType<OracleContainer>> ORACLE_BLOCK_CONTAINER = CONTAINERS.register(
            ORACLE_TAG, () ->
                    IForgeContainerType.create((windowId, inv, data) -> {
                        return new OracleContainer(
                                windowId,
                                Woot.proxy.getClientWorld(),
                                data.readBlockPos(),
                                inv,
                                Woot.proxy.getClientPlayer());
                    }));
}
