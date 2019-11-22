package ipsis.woot.factory.blocks.power.convertors;

import ipsis.woot.factory.blocks.power.CellTileEntityBase;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.mod.ModFluids;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * This is the cheaty converter and allows you to keep the cells
 * full, essentially turning a factory into a free mob farm,
 * with the spawn time being the only limiting factor.
 */
public class TickConverterTileEntity extends TileEntity implements ITickableTileEntity {

    public TickConverterTileEntity() {
        super(ModBlocks.TICK_CONVERTER_BLOCK_TILE);
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
