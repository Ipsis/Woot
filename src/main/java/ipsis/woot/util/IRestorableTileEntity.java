package ipsis.woot.util;

import net.minecraft.nbt.NBTTagCompound;

public interface IRestorableTileEntity {

    void writeRestorableToNBT(NBTTagCompound nbtTagCompound);
    void readRestorableFromNBT(NBTTagCompound nbtTagCompound);
}
