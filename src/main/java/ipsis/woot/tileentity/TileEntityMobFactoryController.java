package ipsis.woot.tileentity;

import ipsis.woot.item.ItemPrism;
import ipsis.woot.manager.MobManager;
import ipsis.woot.util.ItemStackHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityMobFactoryController extends TileEntity {

    String mobName;
    String displayName;
    public TileEntityMobFactoryController() {

        mobName = "";
        displayName = "";
    }

    public void setMobName(String mobName, String displayName) {

        this.mobName = mobName;
        this.displayName = displayName;
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

        if (MobManager.isValidMobName(mobName))
            ItemStackHelper.spawnInWorld(worldObj, pos, ItemPrism.getItemStack(mobName, displayName));
    }
}
