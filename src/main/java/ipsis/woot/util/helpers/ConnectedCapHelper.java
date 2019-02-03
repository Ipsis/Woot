package ipsis.woot.util.helpers;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectedCapHelper {

    public static class ConnectedItemHandler {
        public TileEntity te;
        public IItemHandler iItemHandler;
        public ConnectedItemHandler(TileEntity te, IItemHandler iItemHandler) {
            this.te = te;
            this.iItemHandler = iItemHandler;
        }
    }

    public static class ConnectedFluidHandler {
        public TileEntity te;
        public IFluidHandler iFluidHandler;
        public ConnectedFluidHandler(TileEntity te, IFluidHandler iFluidHandler) {
            this.te = te;
            this.iFluidHandler = iFluidHandler;
        }
    }

    /**
     * Returns a list of connected blocks with ITEM_HANDLER_CAPABILITY
     * Only checks in the horizontal plane
     */
    public static List<ConnectedItemHandler> getConnectedItemHandlers(@Nullable World world, @Nonnull BlockPos pos) {

        if (world == null)
            return Collections.EMPTY_LIST;

        List<ConnectedItemHandler> tiles = new ArrayList<>();
        for (EnumFacing f : EnumFacing.HORIZONTALS) {
            BlockPos pos2 = pos.offset(f);
            if (world.isBlockLoaded(pos2)) {
                TileEntity te = world.getTileEntity(pos2);
                if (te == null)
                    continue;

                if (!te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()))
                    continue;

                IItemHandler iItemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());
                if (iItemHandler == null)
                    continue;

                tiles.add(new ConnectedItemHandler(te, iItemHandler));
            }
        }
        return tiles;
    }

    /**
     * Returns a list of connected tiles TEs with FLUID_HANDLER_CAPABILITY and the specific drain/fillCellTank
     * Only checks in the horizontal plane
     */
    private static List<ConnectedFluidHandler> getConnectedFluidHandlers(@Nullable World world, @Nonnull BlockPos pos, boolean drain) {

        if (world == null)
            return Collections.EMPTY_LIST;

        List<ConnectedFluidHandler> tiles = new ArrayList<>();
        for (EnumFacing f : EnumFacing.HORIZONTALS) {

            BlockPos pos2 = pos.offset(f);
            if (world.isBlockLoaded(pos2)) {
                TileEntity te = world.getTileEntity(pos2);
                if (te == null)
                    continue;

                if (!te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite()))
                    continue;

                IFluidHandler iFluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite());
                if (iFluidHandler == null)
                    continue;

                IFluidTankProperties[] properties = iFluidHandler.getTankProperties();
                if (properties == null)
                    continue;

                for (IFluidTankProperties p : properties) {
                    if ((drain && p.canDrain()) || (!drain && p.canFill())) {
                        tiles.add(new ConnectedFluidHandler(te, iFluidHandler));
                        break;
                    }
                }
            }
        }

        return tiles;
    }

    public static List<ConnectedFluidHandler> getConnectedDrainableFluidHandlers(@Nullable World world, @Nonnull BlockPos pos) {
        return getConnectedFluidHandlers(world, pos, true);
    }

    public static List<ConnectedFluidHandler> getConnectedFillableFluidHandlers(@Nullable World world, @Nonnull BlockPos pos) {
        return getConnectedFluidHandlers(world, pos, false);
    }
}
