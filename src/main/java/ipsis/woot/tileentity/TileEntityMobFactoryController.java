package ipsis.woot.tileentity;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.item.ItemEnderShard;
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
        WootMobBuilder.writeToNBT(wootMob, compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        wootMob = WootMobBuilder.create(compound);
    }

    public void setMobName(String mobName, String displayName, int xp) {

        // ItemPrism1 only
    }

    public boolean isProgrammed() {

        return wootMob.isValid();
    }

    public WootMob getWootMob() {

        return wootMob;
    }

    public String getMobName() {

        if (wootMob != null)
            return wootMob.getDisplayName();
        else
            return "";
    }

    public String getModDisplayName() {

        if (wootMob != null)
            return wootMob.getDisplayName();
        else
            return "";
    }

    public int getXpValue() {

        return wootMob.getXpValue();
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
            WootMobBuilder.writeToNBT(wootMob, tag);
            itemStack.setTagCompound(tag);
        }

        return itemStack;
    }

    public boolean program(ItemStack itemStack) {

        if (itemStack.isEmpty())
            return false;

        if (!ItemEnderShard.isEnderShard(itemStack))
            return false;

        if (isProgrammed())
            return false;

        WootMob tmpWootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (!tmpWootMob.isValid())
            return false;

        // TODO check for capturing
        wootMob = tmpWootMob;

        markDirty();

        IFarmBlockMaster tmpMaster = new ControllerMasterLocator().findMaster(getWorld(), getPos(), this);
        if (tmpMaster != null)
            tmpMaster.interruptFarmStructure();

        return true;
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
}
