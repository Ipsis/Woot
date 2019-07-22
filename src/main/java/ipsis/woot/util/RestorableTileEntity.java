package ipsis.woot.util;

import net.minecraft.nbt.CompoundNBT;

public interface RestorableTileEntity {
    void writeRestorableToNBT(CompoundNBT compoundNBT);
    void readRestorableFromNBT(CompoundNBT compoundNBT);
}
