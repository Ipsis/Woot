package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.layout.BlockLayout;
import ipsis.woot.layout.TileEntityLayout;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModTileEntities {

    @ObjectHolder(Woot.MODID + ":" + BlockLayout.BASENAME)
    public static TileEntityType<TileEntityLayout> layoutTileEntity;
}
