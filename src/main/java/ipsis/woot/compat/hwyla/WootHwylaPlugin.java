package ipsis.woot.compat.hwyla;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.blocks.ControllerBlock;
import ipsis.woot.modules.factory.blocks.UpgradeBlock;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin(Woot.MODID)
public class WootHwylaPlugin implements IWailaPlugin {

    @Override
    public void register(IRegistrar iRegistrar) {
        ControllerDataProvider controllerDataProvider = new ControllerDataProvider();
        iRegistrar.registerBlockDataProvider(controllerDataProvider, ControllerBlock.class);
        iRegistrar.registerComponentProvider(controllerDataProvider, TooltipPosition.BODY, ControllerBlock.class);

        PerkDataProvider perkDataProvider = new PerkDataProvider();
        iRegistrar.registerComponentProvider(perkDataProvider, TooltipPosition.BODY, UpgradeBlock.class);
    }
}
