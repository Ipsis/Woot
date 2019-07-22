package ipsis.woot.factory.blocks;

import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.RestorableTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class ControllerTileEntity extends TileEntity implements RestorableTileEntity {

    public ControllerTileEntity() {
        super(ModBlocks.CONTROLLER_BLOCK_TILE);
    }

    /**
     * NBT
     */
    private FakeMob fakeMob = new FakeMob();
    @Override
    public void readRestorableFromNBT(CompoundNBT compoundNBT) {
        if (compoundNBT != null)
            fakeMob = new FakeMob(compoundNBT);
    }

    @Override
    public void writeRestorableToNBT(CompoundNBT compoundNBT) {
        if (compoundNBT == null)
            compoundNBT = new CompoundNBT();

        FakeMob.writeToNBT(fakeMob, compoundNBT);
    }

    public ItemStack getItemStack() {
        return getItemStack(fakeMob);
    }

    public static ItemStack getItemStack(FakeMob fakeMob) {
        ItemStack itemStack = new ItemStack(ModBlocks.CONTROLLER_BLOCK);
        CompoundNBT compoundNBT = new CompoundNBT();
        FakeMob.writeToNBT(fakeMob, compoundNBT);
        itemStack.setTag(compoundNBT);
        itemStack.setCount(1);
        return itemStack;
    }
}
