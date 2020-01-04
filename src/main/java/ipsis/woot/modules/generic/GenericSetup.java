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
        Woot.LOGGER.info("GenericSetup: register");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<GenericItem> SI_INGOT_ITEM = ITEMS.register(
            "si_ingot", () -> new GenericItem(GenericItemType.SI_INGOT));
    public static final RegistryObject<GenericItem> SI_DUST_ITEM = ITEMS.register(
            "si_dust", () -> new GenericItem(GenericItemType.SI_DUST));
    public static final RegistryObject<GenericItem> SI_PLATE_ITEM = ITEMS.register(
            "si_plate", () -> new GenericItem(GenericItemType.SI_PLATE));
}
