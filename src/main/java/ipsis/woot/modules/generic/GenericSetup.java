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

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Woot.MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("GenericSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<GenericItem> SI_INGOT_ITEM = ITEMS.register(
            "si_ingot", () -> new GenericItem(GenericItemType.SI_INGOT));
    public static final RegistryObject<GenericItem> SI_DUST_ITEM = ITEMS.register(
            "si_dust", () -> new GenericItem(GenericItemType.SI_DUST));
    public static final RegistryObject<GenericItem> SI_PLATE_ITEM = ITEMS.register(
            "si_plate", () -> new GenericItem(GenericItemType.SI_PLATE));
    public static final RegistryObject<GenericItem> PRISM_ITEM = ITEMS.register(
            "prism", () -> new GenericItem(GenericItemType.PRISM));
    public static final RegistryObject<GenericItem> ENCH_PLATE_1 = ITEMS.register(
            "ench_plate_1", () -> new GenericItem(GenericItemType.ENCH_PLATE_1));
    public static final RegistryObject<GenericItem> ENCH_PLATE_2 = ITEMS.register(
            "ench_plate_2", () -> new GenericItem(GenericItemType.ENCH_PLATE_2));
    public static final RegistryObject<GenericItem> ENCH_PLATE_3 = ITEMS.register(
            "ench_plate_3", () -> new GenericItem(GenericItemType.ENCH_PLATE_3));
}
