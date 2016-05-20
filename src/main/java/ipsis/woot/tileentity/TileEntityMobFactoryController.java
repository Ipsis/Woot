package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.init.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityMobFactoryController extends TileEntity {

    String mobName;
    String displayName;
    int xpValue;

    static final String NBT_MOB_NAME = "mobName";
    public static final String NBT_DISPLAY_NAME = "displayName";
    static final String NBT_XP_VALUE = "mobXpCost";

    public TileEntityMobFactoryController() {

        mobName = "";
        displayName = "";
        xpValue = 1;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeControllerToNBT(compound);
    }

    public void writeControllerToNBT(NBTTagCompound compound) {

        compound.setString(NBT_MOB_NAME, mobName);
        compound.setString(NBT_DISPLAY_NAME, displayName);
        compound.setInteger(NBT_XP_VALUE, xpValue);
    }

    public void readControllerFromNBT(NBTTagCompound compound) {

        mobName = compound.getString(NBT_MOB_NAME);
        displayName = compound.getString(NBT_DISPLAY_NAME);
        xpValue = compound.getInteger(NBT_XP_VALUE);
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

    public String getMobName() {

        return mobName;
    }

    public String getDisplayName() {

        return displayName;
    }

    public int getXpValue() {

        return xpValue;
    }

    void updateMobFarm() {

        TileEntity te = worldObj.getTileEntity(getPos().offset(EnumFacing.DOWN));
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
}
