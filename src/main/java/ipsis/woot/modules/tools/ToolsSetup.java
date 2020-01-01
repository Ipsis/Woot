package ipsis.woot.modules.tools;

import ipsis.woot.Woot;
import ipsis.woot.modules.tools.items.InternItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ToolsSetup {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Woot.MODID);

    public static void register() {
        Woot.LOGGER.info("ToolsSetup: register");
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<InternItem> INTERN_ITEM = ITEMS.register(
            "intern", () -> new InternItem());
}
