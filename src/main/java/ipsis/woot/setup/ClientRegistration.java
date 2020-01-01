package ipsis.woot.setup;

import ipsis.woot.Woot;
import ipsis.woot.modules.anvil.client.AnvilTileEntitySpecialRenderer;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.infuser.client.InfuserScreen;
import ipsis.woot.modules.infuser.items.DyeCasingItem;
import ipsis.woot.modules.infuser.items.DyePlateItem;
import ipsis.woot.modules.layout.client.LayoutTileEntitySpecialRenderer;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.oracle.client.OracleScreen;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import ipsis.woot.modules.factory.client.HeartScreen;
import ipsis.woot.modules.anvil.blocks.AnvilTileEntity;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.modules.squeezer.client.DyeSqueezerScreen;
import ipsis.woot.modules.squeezer.client.EnchantSqueezerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Woot.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        Woot.LOGGER.debug("FMLClientSetupEvent");
        ClientRegistry.bindTileEntitySpecialRenderer(LayoutTileEntity.class, new LayoutTileEntitySpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(AnvilTileEntity.class, new AnvilTileEntitySpecialRenderer());
        ScreenManager.registerFactory(FactorySetup.HEART_BLOCK_CONTAINER.get(), HeartScreen::new);
        ScreenManager.registerFactory(OracleSetup.ORACLE_BLOCK_CONTAINER.get(), OracleScreen::new);
        ScreenManager.registerFactory(SqueezerSetup.SQUEEZER_BLOCK_CONTAINER.get(), DyeSqueezerScreen::new);
        ScreenManager.registerFactory(SqueezerSetup.ENCHANT_SQUEEZER_BLOCK_CONTAINER.get(), EnchantSqueezerScreen::new);
        ScreenManager.registerFactory(InfuserSetup.INFUSER_BLOCK_CONTAINER.get(), InfuserScreen::new);

        ItemColors items = Minecraft.getInstance().getItemColors();
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.WHITE_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.ORANGE_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.MAGENTA_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.LIGHT_BLUE_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.YELLOW_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.LIME_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.PINK_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.GRAY_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.LIGHT_GRAY_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.CYAN_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.PURPLE_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.BLUE_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.BROWN_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.GREEN_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.RED_DYE_PLATE_ITEM.get());
        items.register((s, t) -> ((DyePlateItem)s.getItem()).getColor().getColorValue(), InfuserSetup.BLACK_DYE_PLATE_ITEM.get());

        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.WHITE_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.ORANGE_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.MAGENTA_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.LIGHT_BLUE_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.YELLOW_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.LIME_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.PINK_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.GRAY_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.LIGHT_GRAY_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.CYAN_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.PURPLE_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.BLUE_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.BROWN_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.GREEN_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.RED_DYE_CASING_ITEM.get());
        items.register((s, t) -> ((DyeCasingItem)s.getItem()).getColor().getColorValue(), InfuserSetup.BLACK_DYE_CASING_ITEM.get());
    }
}
