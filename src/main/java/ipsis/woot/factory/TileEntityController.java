package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.multiblock.*;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.mod.ModTileEntities;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.IRestorableTileEntity;
import ipsis.woot.util.helper.FakeMobKeyHelper;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileEntityController extends TileEntity implements IRestorableTileEntity, IWootDebug, IMultiBlockGlueProvider {

    private static final int MAX_MOB_COUNT = 4;
    public TileEntityController() {
        super(ModTileEntities.controllerTileEntity);
        iMultiBlockGlue = new Glue(this, this);
    }

    public List<FakeMobKey> getFakeMobKeyList() {
        return Collections.unmodifiableList(fakeMobKeyList);
    }

    @Override
    public void validate() {
        super.validate();
        if (WorldHelper.isServerWorld(getWorld())) {
            // This must NOT use the TE version
            IMultiBlockMaster iMultiBlockMaster = LocatorHelper.findMasterNoTE(getWorld(), getPos());
            if (iMultiBlockMaster != null)
                iMultiBlockMaster.interrupt();
        }
    }

    @Override
    public void remove() {
        super.remove();
        iMultiBlockGlue.onHello(getWorld(), getPos()); // Why hello??
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        iMultiBlockGlue.onGoodbye();
    }

    /**
     * Mobs
     */
    private List<FakeMobKey> fakeMobKeyList = new ArrayList<>();
    public void addMob(FakeMobKey fakeMobKey) {
        if (fakeMobKeyList.size() < MAX_MOB_COUNT)
            fakeMobKeyList.add(fakeMobKey);
    }

    /**
     * NBT
     */
    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        readRestorableFromNBT(compound);
    }

    /**
     * Returns an itemstack containing all the relevant NBT data
     */
    public ItemStack getItemStack() {

        ItemStack itemStack = new ItemStack(ModBlocks.controllerBlock);
        if (!fakeMobKeyList.isEmpty()) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            NBTTagList tagList = new NBTTagList();
            for (int i = 0; i < fakeMobKeyList.size(); i++) {
                NBTTagCompound itemTag = new NBTTagCompound();
                FakeMobKey.writeToNBT(fakeMobKeyList.get(i), itemTag);
                tagList.add(itemTag);
            }

            nbtTagCompound.setTag("keys", tagList);
            itemStack.setTag(nbtTagCompound);
        }

        return itemStack;
    }

    public static ItemStack getItemStack(List<EntityLiving> mobs) {

        ItemStack itemStack = new ItemStack(ModBlocks.controllerBlock);
        if (!mobs.isEmpty()) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            NBTTagList tagList = new NBTTagList();
            for (EntityLiving entityLiving : mobs) {
                NBTTagCompound itemTag = new NBTTagCompound();
                FakeMobKey fakeMobKey = FakeMobKeyHelper.createFromEntity(entityLiving);
                if (fakeMobKey.isValid()) {
                    FakeMobKey.writeToNBT(fakeMobKey, itemTag);
                    tagList.add(itemTag);
                }
            }
            nbtTagCompound.setTag("keys", tagList);
            itemStack.setTag(nbtTagCompound);
        }
        return itemStack;
    }

    /**
     * IRestorableTileEntity
     */
    @Override
    public void readRestorableFromNBT(NBTTagCompound nbtTagCompound) {

        NBTTagList tagList = nbtTagCompound.getList("keys", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            NBTTagCompound tags = tagList.getCompound(i);
            FakeMobKey fakeMobKey = new FakeMobKey(tags);
            fakeMobKeyList.add(fakeMobKey);
        }
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < fakeMobKeyList.size(); i++) {
            NBTTagCompound itemTag = new NBTTagCompound();
            FakeMobKey.writeToNBT(fakeMobKeyList.get(i), itemTag);
            tagList.add(itemTag);
        }

        nbtTagCompound.setTag("keys", tagList);
    }

    /**
     * IMultiBlockGlueProvider
     */
    private IMultiBlockGlue iMultiBlockGlue;
    @Nonnull
    @Override
    public IMultiBlockGlue getIMultiBlockGlue() {
        return iMultiBlockGlue;
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("=====> TileEntityController");
        debug.add("Has Master: " + iMultiBlockGlue.hasMaster());
        debug.add("mobs: " + fakeMobKeyList.size());
        for (FakeMobKey key : fakeMobKeyList)
            debug.add("mob: " + key);

        return debug;
    }
}
