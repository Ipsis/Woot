package ipsis.woot.compat.hwyla;

import ipsis.woot.modules.squeezer.blocks.DyeSqueezerTileEntity;
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

public class SqueezerDataProvider implements IServerDataProvider<TileEntity>, IComponentProvider {

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {

        CompoundNBT nbt = accessor.getServerData();
        if (nbt != null) {
            tooltip.add(new StringTextComponent(
                    StringHelper.translate("top.woot.squeezer.red.label") + ": " +
                    StringHelper.translateFormat("top.woot.squeezer.dye.0", nbt.getInt("red"))));
            tooltip.add(new StringTextComponent(
                    StringHelper.translate("top.woot.squeezer.yellow.label") + ": " +
                            StringHelper.translateFormat("top.woot.squeezer.dye.0", nbt.getInt("yellow"))));
            tooltip.add(new StringTextComponent(
                    StringHelper.translate("top.woot.squeezer.blue.label") + ": " +
                            StringHelper.translateFormat("top.woot.squeezer.dye.0", nbt.getInt("blue"))));
            tooltip.add(new StringTextComponent(
                    StringHelper.translate("top.woot.squeezer.white.label") + ": " +
                            StringHelper.translateFormat("top.woot.squeezer.dye.0", nbt.getInt("white"))));
            tooltip.add(new StringTextComponent(
                    StringHelper.translate("top.woot.squeezer.tanks.label") + ": " +
                            (nbt.getBoolean("dump") ?
                                    StringHelper.translate("top.woot.squeezer.tanks.0") :
                                    StringHelper.translate("top.woot.squeezer.tanks.1"))));
        }

    }

    @Override
    public void appendServerData(CompoundNBT compoundNBT, ServerPlayerEntity serverPlayerEntity, World world, TileEntity tileEntity) {

        if (tileEntity instanceof DyeSqueezerTileEntity) {
            DyeSqueezerTileEntity dyeSqueezerTileEntity = (DyeSqueezerTileEntity)tileEntity;
            compoundNBT.putInt("red", dyeSqueezerTileEntity.getRed());
            compoundNBT.putInt("yellow", dyeSqueezerTileEntity.getYellow());
            compoundNBT.putInt("blue", dyeSqueezerTileEntity.getBlue());
            compoundNBT.putInt("white", dyeSqueezerTileEntity.getWhite());
            compoundNBT.putBoolean("dump", dyeSqueezerTileEntity.getDumpExcess());
        }
    }
}
