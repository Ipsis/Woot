package ipsis.woot.compat.hwyla;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.blocks.ControllerBlock;
import ipsis.woot.modules.factory.blocks.HeartBlock;
import ipsis.woot.modules.factory.blocks.UpgradeBlock;
import ipsis.woot.modules.layout.blocks.LayoutBlock;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerBlock;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.util.ResourceLocation;

@WailaPlugin(Woot.MODID)
public class WootHwylaPlugin implements IWailaPlugin {

    static final ResourceLocation CONFIG_DISPLAY_LAYOUT = new ResourceLocation(Woot.MODID, "display_layout_items");

    @Override
    public void register(IRegistrar iRegistrar) {
        iRegistrar.addConfig(CONFIG_DISPLAY_LAYOUT, true);

        ControllerDataProvider controllerDataProvider = new ControllerDataProvider();
        iRegistrar.registerBlockDataProvider(controllerDataProvider, ControllerBlock.class);
        iRegistrar.registerComponentProvider(controllerDataProvider, TooltipPosition.BODY, ControllerBlock.class);

        PerkDataProvider perkDataProvider = new PerkDataProvider();
        iRegistrar.registerComponentProvider(perkDataProvider, TooltipPosition.BODY, UpgradeBlock.class);

        LayoutDataProvider layoutDataProvider = new LayoutDataProvider();
        iRegistrar.registerBlockDataProvider(layoutDataProvider, LayoutBlock.class);
        iRegistrar.registerComponentProvider(layoutDataProvider, TooltipPosition.BODY, LayoutBlock.class);

        SqueezerDataProvider squeezerDataProvider = new SqueezerDataProvider();
        iRegistrar.registerBlockDataProvider(squeezerDataProvider, DyeSqueezerBlock.class);
        iRegistrar.registerComponentProvider(squeezerDataProvider, TooltipPosition.BODY, DyeSqueezerBlock.class);

        HeartDataProvider heartDataProvider = new HeartDataProvider();
        iRegistrar.registerBlockDataProvider(heartDataProvider, HeartBlock.class);
        iRegistrar.registerComponentProvider(heartDataProvider, TooltipPosition.BODY, HeartBlock.class);
    }
}
