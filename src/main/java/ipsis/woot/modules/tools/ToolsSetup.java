package ipsis.woot.modules.tools;

import ipsis.woot.Woot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ToolsSetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Woot.MODID);

    public static void register() {
        Woot.setup.getLogger().info("ToolsSetup: register");
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
