package ipsis.woot.modules.generic;

import ipsis.woot.Woot;
import ipsis.woot.modules.generic.items.GenericItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GenericSetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("GenericSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<GenericItem> SI_INGOT_ITEM = ITEMS.register(
            "si_ingot", () -> new GenericItem(GenericItem.GenericItemType.SI_INGOT));
    public static final RegistryObject<GenericItem> SI_DUST_ITEM = ITEMS.register(
            "si_dust", () -> new GenericItem(GenericItem.GenericItemType.SI_DUST));
    public static final RegistryObject<GenericItem> SI_PLATE_ITEM = ITEMS.register(
            "si_plate", () -> new GenericItem(GenericItem.GenericItemType.SI_PLATE));
    public static final RegistryObject<GenericItem> PRISM_ITEM = ITEMS.register(
            "prism", () -> new GenericItem(GenericItem.GenericItemType.PRISM));
    public static final RegistryObject<GenericItem> ENCH_PLATE_1 = ITEMS.register(
            "ench_plate_1", () -> new GenericItem(GenericItem.GenericItemType.ENCH_PLATE_1));
    public static final RegistryObject<GenericItem> ENCH_PLATE_2 = ITEMS.register(
            "ench_plate_2", () -> new GenericItem(GenericItem.GenericItemType.ENCH_PLATE_2));
    public static final RegistryObject<GenericItem> ENCH_PLATE_3 = ITEMS.register(
            "ench_plate_3", () -> new GenericItem(GenericItem.GenericItemType.ENCH_PLATE_3));
    public static final RegistryObject<GenericItem> T1_SHARD_ITEM = ITEMS.register(
            "t1shard", () -> new GenericItem(GenericItem.GenericItemType.BASIC_UP_SHARD));
    public static final RegistryObject<GenericItem> T2_SHARD_ITEM = ITEMS.register(
            "t2shard", () -> new GenericItem(GenericItem.GenericItemType.ADVANCED_UP_SHARD));
    public static final RegistryObject<GenericItem> T3_SHARD_ITEM = ITEMS.register(
            "t3shard", () -> new GenericItem(GenericItem.GenericItemType.ELITE_UP_SHARD));
    public static final RegistryObject<GenericItem> MACHINE_CASING_ITEM = ITEMS.register(
            "machine_casing", () -> new GenericItem(GenericItem.GenericItemType.MACHINE_CASING));
}
