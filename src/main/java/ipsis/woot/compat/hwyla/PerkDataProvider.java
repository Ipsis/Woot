package ipsis.woot.compat.hwyla;

import ipsis.woot.modules.factory.Perk;
import ipsis.woot.modules.factory.blocks.UpgradeBlock;
import ipsis.woot.util.helper.StringHelper;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class PerkDataProvider implements IComponentProvider {

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {

        BlockState blockState = accessor.getBlockState();
        Perk perk = blockState.get(UpgradeBlock.UPGRADE);
        if (perk != null) {
            if (perk == Perk.EMPTY) {
                tooltip.add(new StringTextComponent(
                        StringHelper.translate("top.woot.perk.type.label") + ": " +
                                StringHelper.translate("top.woot.perk.type.empty")));
            } else {
                tooltip.add(new StringTextComponent(
                        StringHelper.translate("top.woot.perk.type.label") + ": " +
                                StringHelper.translate("item.woot." + perk.getName())));
            }
        }
    }
}
