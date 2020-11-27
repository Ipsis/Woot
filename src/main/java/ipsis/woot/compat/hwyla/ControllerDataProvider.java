package ipsis.woot.compat.hwyla;

import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StringHelper;
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

public class ControllerDataProvider implements IServerDataProvider<TileEntity>, IComponentProvider {

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        CompoundNBT nbt = accessor.getServerData();
        if (nbt.getBoolean("valid")) {
            FakeMob fakeMob = new FakeMob(nbt);
            Tier tier = Tier.byIndex(nbt.getInt("tier"));
            tooltip.add(new StringTextComponent(
                            StringHelper.translate("top.woot.controller.mob.label") + ": " +
                                    StringHelper.translate(fakeMob)));
            tooltip.add(new StringTextComponent(
                            StringHelper.translate("top.woot.controller.tier.label") + ": " +
                            StringHelper.translate(tier.getTranslationKey())));
        } else {
            tooltip.add(new StringTextComponent(
                    StringHelper.translate("top.woot.controller.mob.label") + ": "));
        }
    }

    @Override
    public void appendServerData(CompoundNBT compoundNBT, ServerPlayerEntity serverPlayerEntity, World world, TileEntity tileEntity) {

        if (tileEntity instanceof ControllerTileEntity) {
            ControllerTileEntity controllerTileEntity = (ControllerTileEntity)tileEntity;
            if (controllerTileEntity.getFakeMob().isValid()) {
                compoundNBT.putBoolean("valid", true);
                FakeMob.writeToNBT(controllerTileEntity.getFakeMob(), compoundNBT);
                compoundNBT.putInt("tier", controllerTileEntity.getTier().ordinal());
            } else {
                compoundNBT.putBoolean("valid", false);
            }
        }
    }
}
