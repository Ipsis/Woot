package ipsis.woot.power;

import ipsis.woot.ModFluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityConvertor extends TileEntity  {

    public int fillCellTank(int amount, boolean doFill) {

        TileEntity te = getWorld().getTileEntity(getPos().up());
        if (te instanceof TileEntityCell) {

            TileEntityCell cell = (TileEntityCell)te;
            IFluidHandler iFluidHandler = cell.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            if (iFluidHandler != null) {
                // NOTE receiveEnergy has a simulate arg but fillCellTank has a doFill
                // They are the opposite of each other
                return iFluidHandler.fill(new FluidStack(ModFluids.effort, amount), doFill);
            }

            return 0;
        }

        return 0;
    }

}
