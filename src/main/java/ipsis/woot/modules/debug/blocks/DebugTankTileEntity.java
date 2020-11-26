package ipsis.woot.modules.debug.blocks;

import ipsis.woot.modules.debug.DebugSetup;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.WootFluidTank;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DebugTankTileEntity extends TileEntity implements WootDebug {

    public DebugTankTileEntity() {
        super(DebugSetup.DEBUG_TANK_BLOCK_TILE.get());
    }

    private LazyOptional<WootFluidTank> inputTank = LazyOptional.of(this::createTank);
    private WootFluidTank createTank() {
        return new WootFluidTank(Integer.MAX_VALUE);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return inputTank.cast();
        return super.getCapability(cap, side);
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> DebugTankTileEntity");
        inputTank.ifPresent(h-> {
            if (h.getFluid().isEmpty())
                debug.add("    Empty");
            else
                debug.add("    " + h.getFluid().getTranslationKey() + "/" + h.getFluidAmount());
        });
        return debug;
    }

}
