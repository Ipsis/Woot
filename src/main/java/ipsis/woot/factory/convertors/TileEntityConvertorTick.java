package ipsis.woot.factory.convertors;

import ipsis.woot.factory.TileEntityCell;
import ipsis.woot.mod.ModTileEntities;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityConvertorTick extends TileEntity implements ITickable {

    public TileEntityConvertorTick() {
        super(ModTileEntities.convertorTickTileEntityType);
    }

    @Override
    public void tick() {
        if (WorldHelper.isClientWorld(world))
            return;

        if (world.isBlockPowered(pos)) {
            TileEntity te = world.getTileEntity(getPos().up());
            if (te instanceof TileEntityCell) {
                TileEntityCell cell = (TileEntityCell) te;
                cell.fill(Integer.MAX_VALUE);
            }
        }
    }
}
