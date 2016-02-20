package ipsis.woot.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityMobFactoryController extends TileEntity {

    String mobName;
    public TileEntityMobFactoryController() {

        mobName = "";
    }

    public void setMobName(String mobName) {

        this.mobName = mobName;
        updateMobFarm();
    }

    public String getMobName() {

        return mobName;
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
}
