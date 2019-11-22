package ipsis.woot.factory.blocks.power;

import ipsis.woot.mod.ModFluids;
import ipsis.woot.util.WootDebug;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.List;

public abstract class CellTileEntityBase extends TileFluidHandler implements WootDebug {

    public CellTileEntityBase(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
        tank.setCapacity(getCapacity());
        tank.setValidator(e -> e.getFluid() == ModFluids.CONATUS_FLUID.get().getFluid());
    }

    /**
     * For testing and the tick converter
     */
    public void fillToCapacity() {
        tank.fill(new FluidStack(ModFluids.CONATUS_FLUID.get(),
                getCapacity()), IFluidHandler.FluidAction.EXECUTE);
    }

    abstract int getCapacity();
    abstract int getMaxTransfer();

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> CellTileEntity");
        debug.add("      capacity: " + tank.getCapacity());
        debug.add("      transfer: " + getMaxTransfer());
        debug.add("      contains: " + tank.getFluid().getTranslationKey());
        debug.add("      contains: " + tank.getFluid().getAmount());
        return debug;
    }
}
