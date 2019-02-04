package ipsis.woot.power;

import ipsis.Woot;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class TileEntityConvertorFluid extends TileEntityConvertor implements IFluidHandler {

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);

        return super.getCapability(capability, facing);
    }

    /**
     * IFluidHandler
     */
    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[0];
    }

    @Override
    public int fill(FluidStack fluidStack, boolean doFill) {

        if (fluidStack != null) {
            int mb = Woot.CONVERSION_REGISTRY.convertFromFluid(fluidStack);
            return fillCellTank(mb, doFill);
        }
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack fluidStack, boolean doFill) {
        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int amount, boolean doFill) {
        return null;
    }
}
