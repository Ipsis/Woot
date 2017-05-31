package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.item.ItemPrism2;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityMobFactoryController extends TileEntity {

    String mobName;
    String displayName;
    int xpValue;

    WootMob wootMob;

    static final String NBT_MOB_NAME = "mobName";
    public static final String NBT_DISPLAY_NAME = "displayName";
    static final String NBT_XP_VALUE = "mobXpCost";

    public TileEntityMobFactoryController() {

        mobName = "";
        displayName = "";
        xpValue = 1;
        wootMob = null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeControllerToNBT(compound);
        return compound;
    }

    public void writeControllerToNBT(NBTTagCompound compound) {

        if (wootMob != null)
            wootMob.writeToNBT(compound);
        compound.setString(NBT_MOB_NAME, mobName);
        compound.setString(NBT_DISPLAY_NAME, displayName);
        compound.setInteger(NBT_XP_VALUE, xpValue);
    }

    public void readControllerFromNBT(NBTTagCompound compound) {

        mobName = compound.getString(NBT_MOB_NAME);
        displayName = compound.getString(NBT_DISPLAY_NAME);
        xpValue = compound.getInteger(NBT_XP_VALUE);
        if (compound.hasKey(WootMob.NBT_NAME))
            wootMob = WootMob.createFromNBT(compound);
        else
            wootMob = null;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readControllerFromNBT(compound);
    }

    public void setMobName(String mobName, String displayName, int xp) {

        this.mobName = mobName;
        this.displayName = displayName;
        this.xpValue = xp;
        markDirty();
        updateMobFarm();
    }

    public boolean isProgrammed() {

        return wootMob != null;
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

        return xpValue;
    }

    private void updateMobFarm() {

        TileEntity te = world.getTileEntity(getPos().offset(EnumFacing.DOWN));
        if (te instanceof TileEntityMobFactory)
            ((TileEntityMobFactory) te).interruptStructure();
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
        if (Woot.mobRegistry.isValidMobName(mobName)) {
            NBTTagCompound tag = new NBTTagCompound();
            writeControllerToNBT(tag);
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

        wootMob = WootMob.createFromNBT(itemStack.getTagCompound());
        if (wootMob == null)
            return false;

        if (!WootMob.canCapture(wootMob.getWootMobName())) {
            LogHelper.info("Unable to program " + wootMob.getDisplayName());
            return false;
        }

        markDirty();
        updateMobFarm();
        return true;
    }
}
