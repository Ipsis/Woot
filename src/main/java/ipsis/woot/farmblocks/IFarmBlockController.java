package ipsis.woot.farmblocks;

import ipsis.woot.util.WootMob;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public interface IFarmBlockController {

    @Nonnull WootMob getWootMob();
    boolean isProgrammed();
    void readControllerFromNBT(NBTTagCompound compound);
}
