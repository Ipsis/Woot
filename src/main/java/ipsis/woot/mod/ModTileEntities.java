package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.factory.BlockController;
import ipsis.woot.factory.TileEntityController;
import ipsis.woot.layout.BlockLayout;
import ipsis.woot.layout.TileEntityLayout;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModTileEntities {

    @ObjectHolder(Woot.MODID + ":" + BlockLayout.BASENAME)
    public static TileEntityType<TileEntityLayout> layoutTileEntity;

    @ObjectHolder(Woot.MODID + ":" + BlockController.BASENAME)
    public static TileEntityType<TileEntityController> controllerTileEntity;
}
