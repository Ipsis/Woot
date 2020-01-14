package ipsis.woot.modules.debug.blocks;

import ipsis.woot.modules.debug.DebugSetup;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CreativePowerTileEntity extends TileEntity implements ITickableTileEntity {

    public CreativePowerTileEntity() {
        super(DebugSetup.CREATIVE_POWER_BLOCK_TILE.get());
    }

    @Override
    public void tick() {
        if (world.isRemote)
            return;

        // Fill adjacent every second
        for (Direction facing : Direction.values()) {
            TileEntity te = world.getTileEntity(pos.offset(facing));
            if (te != null) {
                te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite())
                        .ifPresent(h -> { if (h.canReceive()) h.receiveEnergy(1000, false); });

            }
        }
    }

    private LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(this::createEnergy);
    private EnergyStorage createEnergy() {
        return new EnergyStorage(Integer.MAX_VALUE);
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY)
            return energyStorage.cast();
        return super.getCapability(cap, side);
    }
}
