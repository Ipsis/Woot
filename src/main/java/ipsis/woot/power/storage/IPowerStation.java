package ipsis.woot.power.storage;

import ipsis.woot.block.BlockMobFactoryCell;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public interface IPowerStation {

    void setTier(BlockMobFactoryCell.EnumCellTier tier);
    BlockMobFactoryCell.EnumCellTier getTier();
    int consume(int power);
    @Nonnull IEnergyStorage getEnergyStorage();
    void readFromNBT(NBTTagCompound compound);
    void writeToNBT(NBTTagCompound compound);
}
