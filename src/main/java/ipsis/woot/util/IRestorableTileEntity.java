package ipsis.woot.util;

import net.minecraft.nbt.NBTTagCompound;

public interface IRestorableTileEntity {

    void readRestorableFromNBT(NBTTagCompound nbtTagCompound);
    void writeRestorableToNBT(NBTTagCompound nbtTagCompound);
}
