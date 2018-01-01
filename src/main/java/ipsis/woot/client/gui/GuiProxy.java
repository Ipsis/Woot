package ipsis.woot.client.gui;

import ipsis.woot.client.gui.inventory.FactoryHeartContainer;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (te instanceof TileEntityMobFactoryHeart)
            return new FactoryHeartContainer(player.inventory, (TileEntityMobFactoryHeart)te);

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (te instanceof TileEntityMobFactoryHeart)
            return new FactoryHeartContainerGui(
                            (TileEntityMobFactoryHeart)te,
                            new FactoryHeartContainer(player.inventory, (TileEntityMobFactoryHeart) te));
        return null;
    }
}
