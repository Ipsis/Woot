package ipsis.woot.blocks.generators;

import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileEntityCreativeRF extends TileEntity implements ITickable {

    private EnergyStorage energyStorage = new EnergyStorage(Integer.MAX_VALUE, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);

    @Override
    public void update() {
        if (WorldHelper.isServerWorld(getWorld()))
            sendEnergy();
    }

    private void sendEnergy() {
       if (energyStorage.getEnergyStored() <= 0)
           return;

        /**
         * Shove max power into all connected blocks>
         * All for free!
         */
       for (EnumFacing facing : EnumFacing.VALUES) {
           TileEntity te = world.getTileEntity(pos.offset(facing));
           if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
               IEnergyStorage hdlr = te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
               if (hdlr != null && hdlr.canReceive()) {
                   hdlr.receiveEnergy(energyStorage.getEnergyStored(), false);
               }
           }
       }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return  true;

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(energyStorage);

        return super.getCapability(capability, facing);
    }
}
