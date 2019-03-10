package ipsis.woot.creative;

import ipsis.woot.mod.ModTileEntities;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityCreativeRF extends TileEntity implements ITickable {

    private EnergyStorage energyStorage = new EnergyStorage(Integer.MAX_VALUE, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public TileEntityCreativeRF() {
        super(ModTileEntities.createRFTileEntity);
    }

    @Override
    public void tick() {
        if (WorldHelper.isServerWorld(getWorld()))
            sendEnergy();
    }

    private void sendEnergy() {
        for (EnumFacing facing : EnumFacing.values()) {
            TileEntity te = getWorld().getTileEntity(pos.offset(facing));
            if (te != null) {
               if (te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite())
                    .map(handler -> {
                        if (handler.canReceive())
                            handler.receiveEnergy(Integer.MAX_VALUE, false);
                        return false;
                    }).orElse(false)) {
                   break;
               }
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == CapabilityEnergy.ENERGY)
            return LazyOptional.of(() -> (T) energyStorage);

        return super.getCapability(cap, side);
    }
}
