package ipsis.woot.blocks;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.FakeMobKeyFactory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityController extends TileEntity {

    private FakeMobKey fakeMobKey = new FakeMobKey();

    public TileEntityController() {}

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        FakeMobKey.writeToNBT(fakeMobKey, compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fakeMobKey = FakeMobKeyFactory.createFromNBT(compound);
    }

    public void setFakeMobKey(FakeMobKey fakeMobKey) {
        this.fakeMobKey = fakeMobKey;
    }

    public FakeMobKey getFakeMobKey() { return this.fakeMobKey; }
}
