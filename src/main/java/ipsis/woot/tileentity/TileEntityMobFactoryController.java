package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.farmblocks.*;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.tileentity.ui.ControllerUIInfo;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

public class TileEntityMobFactoryController extends TileEntity implements IFarmBlockController, IFactoryGlueProvider {

    private IFactoryGlue iFactoryGlue;
    private WootMob wootMob = new WootMob();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);
        writeControllerToNBT(compound);
        return compound;
    }

    public TileEntityMobFactoryController() {

        iFactoryGlue = new FactoryGlue(IFactoryGlue.FactoryBlockType.CONTROLLER, new ControllerMasterLocator(), this, this);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        readControllerFromNBT(compound);
    }

    @Override
    public void readControllerFromNBT(NBTTagCompound compound) {

        wootMob = WootMobBuilder.create(compound);
    }

    public void writeControllerToNBT(NBTTagCompound compound) {

        WootMobBuilder.writeToNBT(wootMob, compound);
    }

    public boolean isProgrammed() {

        return wootMob.isValid();
    }

    @Nonnull
    public WootMob getWootMob() {

        return wootMob;
    }

    public void onBlockAdded() {

        iFactoryGlue.onHello(getWorld(), getPos());
    }

    @Override
    public void invalidate() {

        super.invalidate();
        iFactoryGlue.onGoodbye();
    }

    @Override
    public void onChunkUnload() {

        super.onChunkUnload();
        iFactoryGlue.onGoodbye();
    }

    public ItemStack getDroppedItemStack() {

        ItemStack itemStack = new ItemStack(Item.getItemFromBlock(ModBlocks.blockFactoryController), 1);
        if (wootMob.isValid()) {
            NBTTagCompound tag = new NBTTagCompound();
            writeControllerToNBT(tag);
            itemStack.setTagCompound(tag);
        }

        return itemStack;
    }

    public static ItemStack getProgrammedStack(WootMob wootMob) {

        ItemStack itemStack = new ItemStack(Item.getItemFromBlock(ModBlocks.blockFactoryController), 1);
        if (wootMob.isValid()) {
            NBTTagCompound tag = new NBTTagCompound();
            WootMobBuilder.writeToNBT(wootMob, tag);
            itemStack.setTagCompound(tag);
        }

        return itemStack;
    }

    public void getUIInfo(ControllerUIInfo info) {

        if (isProgrammed()) {
            info.isValid = true;
            info.wootMob = wootMob;
            info.requiredTier = Woot.wootConfiguration.getFactoryTier(world, wootMob.getWootMobName());
        }
    }

    @Nonnull
    @Override
    public IFactoryGlue getIFactoryGlue() {

        return iFactoryGlue;
    }
}
