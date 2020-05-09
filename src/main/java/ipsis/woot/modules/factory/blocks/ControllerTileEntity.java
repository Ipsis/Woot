package ipsis.woot.modules.factory.blocks;

import ipsis.woot.mod.ModNBT;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.WootDebug;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;

public class ControllerTileEntity extends MultiBlockTileEntity implements WootDebug {

    public ControllerTileEntity() {
        super(FactorySetup.CONTROLLER_BLOCK_TILE.get());
    }

    private FakeMob fakeMob = new FakeMob();

    /**
     * NBT
     */
    @Override
    public void read(CompoundNBT compoundNBT) {
        super.read(compoundNBT);
        if (compoundNBT.contains(ModNBT.Controller.MOB_TAG)) {
            CompoundNBT nbt = compoundNBT.getCompound(ModNBT.Controller.MOB_TAG);
            fakeMob = new FakeMob(nbt);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        CompoundNBT nbt = new CompoundNBT();
        FakeMob.writeToNBT(fakeMob, nbt);
        compoundNBT.put(ModNBT.Controller.MOB_TAG, nbt);
        return compoundNBT;
    }

    public FakeMob getFakeMob() {
        return fakeMob;
    }

    public static ItemStack getItemStack(FakeMob fakeMob) {
        ItemStack itemStack = new ItemStack(FactorySetup.CONTROLLER_BLOCK.get());

        /**
         * setTileEntityNBT
         */
        CompoundNBT compoundNBT = itemStack.getOrCreateChildTag("BlockEntityTag");
        CompoundNBT nbt = new CompoundNBT();
        FakeMob.writeToNBT(fakeMob, nbt);
        compoundNBT.put(ModNBT.Controller.MOB_TAG, nbt);
        return itemStack;
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> ControllerTileEntity");
        debug.add("      hasMaster: " + glue.hasMaster());
        debug.add("      mob: " + fakeMob);
        return debug;
    }
}
