package ipsis.woot.compat.hwyla;

import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.factory.layout.PatternRepository;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import ipsis.woot.util.helper.StringHelper;
import mcjty.theoneprobe.api.CompoundText;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class LayoutDataProvider implements IServerDataProvider<TileEntity>, IComponentProvider {

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {

        CompoundNBT nbt = accessor.getServerData();
        if (nbt != null) {
            Tier tier = Tier.byIndex(nbt.getInt("tier"));
            if (tier != Tier.UNKNOWN) {
                tooltip.add(new StringTextComponent(
                        StringHelper.translate("top.woot.layout.tier.label") + ": " +
                                StringHelper.translate(tier.getTranslationKey())));


                if (config.get(WootHwylaPlugin.CONFIG_DISPLAY_LAYOUT)) {
                    PatternRepository.Pattern pattern = PatternRepository.get().getPattern(tier);
                    if (pattern != null) {
                        for (FactoryComponent component : FactoryComponent.VALUES) {
                            int count = pattern.getFactoryBlockCount((component));
                            if (count > 0) {
                                String text = String.format("%2d * %s", count, StringHelper.translate(component.getTranslationKey()));
                                if (component == FactoryComponent.CELL)
                                    text = String.format("%2d * %s", count, StringHelper.translate("info.woot.intern.cell"));
                                else if (component == FactoryComponent.CONTROLLER)
                                    text = String.format(" 1-%d * %s", count, StringHelper.translate(component.getTranslationKey()));

                                tooltip.add(new StringTextComponent(text));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void appendServerData(CompoundNBT compoundNBT, ServerPlayerEntity serverPlayerEntity, World world, TileEntity tileEntity) {

        if (tileEntity instanceof LayoutTileEntity) {
            LayoutTileEntity layoutTileEntity = (LayoutTileEntity)tileEntity;

            Tier tier = layoutTileEntity.getTier();
            compoundNBT.putInt("tier", tier.ordinal());
        }
    }
}
