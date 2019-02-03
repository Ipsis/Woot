package ipsis.woot.power;

import ipsis.woot.factory.structure.locator.*;
import ipsis.woot.util.IDebug;
import ipsis.woot.util.WootFluidTank;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileEntityCell extends TileEntity implements IMultiBlockGlueProvider, IDebug {

    private WootFluidTank fluidTank = new WootFluidTank(1000);
    private IMultiBlockGlue iMultiBlockGlue;

    public TileEntityCell() {
        super();
        iMultiBlockGlue = new Glue(this, this);
    }

    public TileEntityCell(int capacity) {
        this();
        fluidTank.setCapacity(capacity);
    }

    public int consume(int units) {

        FluidStack fluidStack = fluidTank.drainInternal(units, true);
        return fluidStack != null ? fluidStack.amount : 0;
    }

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
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidTank);

        return super.getCapability(capability, facing);
    }


    @Override
    public void validate() {
        super.validate();
        if (WorldHelper.isServerWorld(getWorld())) {
            // This must NOT use the TE version
            IMultiBlockMaster master = LocatorHelper.findMasterNoTE(getWorld(), getPos());
            if (master != null)
                master.interrupt();
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        iMultiBlockGlue.onHello(getWorld(), getPos());
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        iMultiBlockGlue.onGoodbye();
    }

    @Nonnull
    @Override
    public IMultiBlockGlue getIMultiBlockGlue() {
        return iMultiBlockGlue;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        fluidTank.writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        fluidTank.readFromNBT(compound);
        super.readFromNBT(compound);
    }

    /**
     * IDebug
     */
    @Override
    public void getDebugText(List<String> debug) {
        debug.add("Has Master:" + iMultiBlockGlue.hasMaster());
        FluidStack fluidStack = fluidTank.getFluid();
        if (fluidStack == null)
            debug.add("Fluid: empty");
        else
            debug.add("Fluid: " + fluidStack.getUnlocalizedName() + "/" + fluidStack.amount);
    }
}
