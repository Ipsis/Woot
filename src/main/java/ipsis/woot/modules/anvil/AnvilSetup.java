package ipsis.woot.modules.anvil;

import ipsis.woot.Woot;
import ipsis.woot.modules.anvil.blocks.AnvilBlock;
import ipsis.woot.modules.anvil.blocks.AnvilTileEntity;
import ipsis.woot.modules.anvil.items.DieItem;
import ipsis.woot.modules.anvil.items.HammerItem;
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

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Woot.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Woot.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("AnvilSetup: register");
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
    public static final RegistryObject<TileEntityType<AnvilTileEntity>> ANVIL_BLOCK_TILE = TILES.register(
            ANVIL_TAG, () ->
                    TileEntityType.Builder.create(AnvilTileEntity::new, ANVIL_BLOCK.get()).build(null));

    public static final RegistryObject<HammerItem> HAMMER_ITEM = ITEMS.register(
            "hammer", () -> new HammerItem());
    public static final RegistryObject<DieItem> PLATE_DIE_ITEM = ITEMS.register(
            "plate_die", () -> new DieItem(DieItem.DieType.PLATE));
    public static final RegistryObject<DieItem> SHARD_DIE_ITEM = ITEMS.register(
            "shard_die", () -> new DieItem(DieItem.DieType.SHARD));
    public static final RegistryObject<DieItem> DYE_DIE_ITEM = ITEMS.register(
            "dye_die", () -> new DieItem(DieItem.DieType.DYE));
}
