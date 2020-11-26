package ipsis.woot.modules.debug.blocks;

import ipsis.woot.modules.debug.DebugSetup;
import ipsis.woot.modules.factory.blocks.CellTileEntityBase;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

/**
 * This is the cheaty converter and allows you to keep the cells
 * full, essentially turning a factory into a free mob farm,
 * with the spawn time being the only limiting factor.
 */
public class TickConverterTileEntity extends TileEntity implements ITickableTileEntity {

    public TickConverterTileEntity() {
        super(DebugSetup.CREATIVE_CONATUS_BLOCK_TILE.get());
    }

    @Override
    public void tick() {
        for (Direction facing : Direction.values()) {
            TileEntity te = world.getTileEntity(getPos().offset(facing));
            if (!(te instanceof CellTileEntityBase))
                continue;

            ((CellTileEntityBase) te).fillToCapacity();
        }
    }
}
