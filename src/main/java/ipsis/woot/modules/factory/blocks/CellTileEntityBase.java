package ipsis.woot.modules.factory.blocks;

import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.mod.ModNBT;
import ipsis.woot.modules.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class CellTileEntityBase extends MultiBlockTileEntity implements WootDebug {

    protected FluidTank tank = new FluidTank(FluidAttributes.BUCKET_VOLUME);
    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    public CellTileEntityBase(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
        tank.setCapacity(getCapacity());
        tank.setValidator(e -> e.getFluid() == FluidSetup.CONATUS_FLUID.get().getFluid());
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        super.deserializeNBT(compound);
        readFromNBT(compound);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        readFromNBT(compoundNBT);
    }

    private void readFromNBT(CompoundNBT compound) {
        if (compound.contains(ModNBT.TANK_TAG))
            tank.readFromNBT(compound.getCompound(ModNBT.TANK_TAG));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        CompoundNBT tankNBT = new CompoundNBT();
        tank.writeToNBT(tankNBT);
        compound.put(ModNBT.TANK_TAG, tankNBT);
        return compound;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(capability, facing);
    }

    /**
     * For testing and the tick converter
     */
    public void fillToCapacity() {
        tank.fill(new FluidStack(FluidSetup.CONATUS_FLUID.get(),
                getCapacity()), IFluidHandler.FluidAction.EXECUTE);
    }

    public abstract int getCapacity();
    public abstract int getMaxTransfer();

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> CellTileEntity");
        debug.add("      hasMaster: " + glue.hasMaster());
        debug.add("      capacity: " + tank.getCapacity());
        debug.add("      transfer: " + getMaxTransfer());
        debug.add("      contains: " + tank.getFluid().getTranslationKey());
        debug.add("      contains: " + tank.getFluid().getAmount());
        return debug;
    }
}
