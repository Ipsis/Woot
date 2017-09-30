package ipsis.woot.farmblocks;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;


public interface IFarmBlockMaster {

    void interruptFarmStructure();
    void interruptFarmUpgrade();
    boolean hasCapability(Capability<?> capability, EnumFacing facing);
    <T> T getCapability(Capability<T> capability, EnumFacing facing);
}
