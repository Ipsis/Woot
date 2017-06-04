package ipsis.woot.tileentity;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.item.ItemPrism2;
import ipsis.woot.tileentity.ng.WootMob;
import ipsis.woot.tileentity.ng.WootMobBuilder;
import ipsis.woot.tileentity.ng.farmblocks.IFarmBlockController;
import ipsis.woot.tileentity.ng.farmblocks.IFarmBlockMaster;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityMobFactoryController extends TileEntity implements IFarmBlockController {

    WootMob wootMob = new WootMob();

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

    private void updateMobFarm() {

        TileEntity te = world.getTileEntity(getPos().offset(EnumFacing.DOWN));
        if (te instanceof IFarmBlockMaster)
            ((IFarmBlockMaster) te).interruptFarmStructure();
    }

    public void blockAdded() {

        updateMobFarm();
    }

    @Override
    public void invalidate() {

        updateMobFarm();
    }

    public ItemStack getDroppedItemStack() {

        ItemStack itemStack = new ItemStack(Item.getItemFromBlock(ModBlocks.blockController), 1);
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

        if (!ItemPrism2.isPrism(itemStack))
            return false;

        if (isProgrammed())
            return false;

        WootMob tmpWootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (!tmpWootMob.isValid())
            return false;

        // TODO check for capturing
        wootMob = tmpWootMob;

        markDirty();
        updateMobFarm();
        return true;
    }
}
