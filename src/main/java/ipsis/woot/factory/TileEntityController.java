package ipsis.woot.factory;

import ipsis.woot.mod.ModTileEntities;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.IRestorableTileEntity;
import ipsis.woot.util.helper.FakeMobKeyHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class TileEntityController extends TileEntity implements IRestorableTileEntity {

    public TileEntityController() {
        super(ModTileEntities.controllerTileEntity);
        FakeMobKey fakeMobKey = new FakeMobKey("minecraft:pig");
        fakeMobKeyList.add(fakeMobKey);
    }

    /**
     * Mobs
     */
    private List<FakeMobKey> fakeMobKeyList = new ArrayList<>();

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
     * IRestorableTileEntity
     */
    @Override
    public void readRestorableFromNBT(NBTTagCompound nbtTagCompound) {

        NBTTagList tagList = nbtTagCompound.getList("keys", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            NBTTagCompound tags = tagList.getCompound(i);
            FakeMobKey fakeMobKey = FakeMobKeyHelper.createFromNBT(tags);
            fakeMobKeyList.add(fakeMobKey);
        }
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < fakeMobKeyList.size(); i++) {
            NBTTagCompound itemTag = new NBTTagCompound();
            FakeMobKey.writeToNBT(fakeMobKeyList.get(0), itemTag);
            tagList.add(itemTag);
        }

        nbtTagCompound.setTag("keys", tagList);
    }
}
