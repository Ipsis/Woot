package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.tileentity.ui.ControllerUIInfo;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobBuilder;
import ipsis.woot.farmblocks.ControllerMasterLocator;
import ipsis.woot.farmblocks.IFarmBlockConnection;
import ipsis.woot.farmblocks.IFarmBlockController;
import ipsis.woot.farmblocks.IFarmBlockMaster;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityMobFactoryController extends TileEntity implements IFarmBlockController, IFarmBlockConnection {

    private WootMob wootMob = new WootMob();
    private IFarmBlockMaster farmBlockMaster = null;

    public boolean hasMaster() { return farmBlockMaster != null; }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);
        writeControllerToNBT(compound);
        return compound;
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

    public WootMob getWootMob() {

        return wootMob;
    }

    public void blockAdded() {

        IFarmBlockMaster tmpMaster = new ControllerMasterLocator().findMaster(getWorld(), getPos(), this);
        if (tmpMaster != null)
            tmpMaster.interruptFarmStructure();
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster())
            farmBlockMaster.interruptFarmStructure();
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

    /**
     * IFarmBlockConnection
     */

    @Override
    public void setMaster(IFarmBlockMaster master) {

        if (farmBlockMaster != master) {
            farmBlockMaster = master;

            //WorldHelper.updateClient(getWorld(), getPos());
        }
    }

    @Override
    public void clearMaster() {

        if (farmBlockMaster != null) {
            farmBlockMaster = null;
            //WorldHelper.updateClient(getWorld(), getPos());
        }
    }

    @Override
    public BlockPos getStructurePos() {

        return getPos();
    }

    public void getUIInfo(ControllerUIInfo info) {

        if (isProgrammed()) {
            info.isValid = true;
            info.wootMob = wootMob;
            info.requiredTier = Woot.wootConfiguration.getFactoryTier(world, wootMob.getWootMobName());
        }
    }
}
