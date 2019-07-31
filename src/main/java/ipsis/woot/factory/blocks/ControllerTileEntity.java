package ipsis.woot.factory.blocks;

import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.WootDebug;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class ControllerTileEntity extends TileEntity implements WootDebug {

    public ControllerTileEntity() {
        super(ModBlocks.CONTROLLER_BLOCK_TILE);
    }

    private FakeMob fakeMob = new FakeMob();

    /**
     * NBT
     */
    @Override
    public void read(CompoundNBT compoundNBT) {
        super.read(compoundNBT);
        if (compoundNBT.contains("mob")) {
            CompoundNBT nbt = compoundNBT.getCompound("mob");
            fakeMob = new FakeMob(nbt);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        CompoundNBT nbt = new CompoundNBT();
        FakeMob.writeToNBT(fakeMob, nbt);
        compoundNBT.put("mob", nbt);
        return compoundNBT;
    }

    public FakeMob getFakeMob() {
        return fakeMob;
    }

    public static ItemStack getItemStack(FakeMob fakeMob) {
        ItemStack itemStack = new ItemStack(ModBlocks.CONTROLLER_BLOCK);

        /**
         * setTileEntityNBT
         */
        CompoundNBT compoundNBT = itemStack.getOrCreateChildTag("BlockEntityTag");
        CompoundNBT nbt = new CompoundNBT();
        FakeMob.writeToNBT(fakeMob, nbt);
        compoundNBT.put("mob", nbt);
        return itemStack;
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> ControllerTileEntity");
        debug.add("      mob: " + fakeMob);
        return debug;
    }
}
